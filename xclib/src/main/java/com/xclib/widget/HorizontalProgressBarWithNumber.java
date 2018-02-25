package com.xclib.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.xclib.R;


public class HorizontalProgressBarWithNumber extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACHED_COLOR = 0xFFd3d6da;
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_SIZE_TEXT_OFFSET = 10;

    /**
     * painter of all drawing things
     */
    protected Paint mPaint = new Paint();
    /**
     * color of progress number
     */
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    /**
     * size of text (sp)
     */
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);

    /**
     * offset of draw progress
     */
    protected int mTextOffset = dp2px(DEFAULT_SIZE_TEXT_OFFSET);

    /**
     * height of reached progress bar
     */
    protected int mReachedProgressBarHeight = dp2px(DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);

    /**
     * color of reached bar
     */
    protected int mReachedBarColor = DEFAULT_TEXT_COLOR;
    /**
     * color of unreached bar
     */
    protected int mUnReachedBarColor = DEFAULT_COLOR_UNREACHED_COLOR;
    /**
     * height of unreached progress bar
     */
    protected int mUnReachedProgressBarHeight = dp2px(DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
    /**
     * view width except padding
     */
    protected int mRealWidth;

    protected boolean mIfDrawText = true;

    protected static final int VISIBLE = 0;


    private String centerText;

    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs,
                                           int defStyle) {
        super(context, attrs, defStyle);
        obtainStyledAttributes(attrs);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mRealWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            float textHeight = (mPaint.descent() - mPaint.ascent());
            result = (int) (getPaddingTop() + getPaddingBottom() + Math.max(
                    Math.max(mReachedProgressBarHeight,
                            mUnReachedProgressBarHeight), Math.abs(textHeight)));
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * get the styled attributes
     *
     * @param attrs
     */
    private void obtainStyledAttributes(AttributeSet attrs) {
        // init values from custom attributes
        final TypedArray attributes = getContext().obtainStyledAttributes(
                attrs, R.styleable.HorizontalProgressBarWithNumber);

        mTextColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBarWithNumber_progress_text_color,
                        DEFAULT_TEXT_COLOR);
        mTextSize = (int) attributes.getDimension(
                R.styleable.HorizontalProgressBarWithNumber_progress_text_size,
                mTextSize);

        mReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBarWithNumber_progress_reached_color,
                        mTextColor);
        mUnReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBarWithNumber_progress_unreached_color,
                        DEFAULT_COLOR_UNREACHED_COLOR);
        mReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBarWithNumber_progress_reached_bar_height,
                        mReachedProgressBarHeight);
        mUnReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBarWithNumber_progress_unreached_bar_height,
                        mUnReachedProgressBarHeight);
        mTextOffset = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBarWithNumber_progress_text_offset,
                        mTextOffset);

        int textVisible = attributes
                .getInt(R.styleable.HorizontalProgressBarWithNumber_progress_text_visibility,
                        VISIBLE);
        if (textVisible != VISIBLE) {
            mIfDrawText = false;
        }
        attributes.recycle();
    }

    private float progress=-1f;

    public synchronized void setFloatProgress(float progress) {
        this.progress = progress;
    }

    public float getFloatProgress() {
        return progress;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(getPaddingLeft(), 0);

        float progress=getFloatProgress();
        if(progress<5&&progress>0){
            progress=5;
        }
        float radio = progress * 1.0f / getMax();
        if(radio>1){
            radio=1;
        }
        float progressPosX = (int) (mRealWidth * radio);

        // draw unreached bar
        float start = 0; //progressPosX-60;
        if (start < 0) {
            start = 0;
        }
        mPaint.setColor(mUnReachedBarColor);

        RectF rectF = new RectF(start, 0, mRealWidth, mUnReachedProgressBarHeight);
        canvas.drawRoundRect(rectF, 50, 50, mPaint);


        // draw reached bar
        float endX = progressPosX;
        if (endX > 0) {
            mPaint.setColor(mReachedBarColor);
            setLayerType(LAYER_TYPE_SOFTWARE, null);
            mPaint.setShadowLayer(5, 0, 0, mReachedBarColor);
            // mPaint.setStrokeWidth(mReachedProgressBarHeight);
            rectF = new RectF(0, 0, endX, mReachedProgressBarHeight);
            canvas.drawRoundRect(rectF, 50, 50, mPaint);
            // canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        mPaint.setShadowLayer(0, 0, 0, 0);

        // draw progress bar
        // measure text bound
        String text=getCenterText();

        if(getFloatProgress()<0){
            mIfDrawText=false;
        }
        // mPaint.getTextBounds(text, 0, text.length(), mTextBound);
        if(text!=null){
            float textWidth = mPaint.measureText(text);
            float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;
            float textPosX;
            if (progressPosX - mTextOffset > textWidth) {
                textPosX = progressPosX - textWidth - mTextOffset;
            } else {
                textPosX = progressPosX + mTextOffset;
                mTextColor = mReachedBarColor;
            }
            if (mIfDrawText) {
                mPaint.setColor(mTextColor);
                canvas.translate(0, getHeight() / 2);
                canvas.drawText(text, textPosX, -textHeight, mPaint);
            }
        }


        canvas.restore();

    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getmTextOffset() {
        return mTextOffset;
    }

    public void setmTextOffset(int mTextOffset) {
        this.mTextOffset = mTextOffset;
    }

    public int getmReachedProgressBarHeight() {
        return mReachedProgressBarHeight;
    }

    public void setmReachedProgressBarHeight(int mReachedProgressBarHeight) {
        this.mReachedProgressBarHeight = mReachedProgressBarHeight;
    }

    public int getmReachedBarColor() {
        return mReachedBarColor;
    }

    public void setmReachedBarColor(int mReachedBarColor) {
        this.mReachedBarColor = mReachedBarColor;
    }

    public int getmUnReachedBarColor() {
        return mUnReachedBarColor;
    }

    public void setmUnReachedBarColor(int mUnReachedBarColor) {
        this.mUnReachedBarColor = mUnReachedBarColor;
    }

    public int getmUnReachedProgressBarHeight() {
        return mUnReachedProgressBarHeight;
    }

    public void setmUnReachedProgressBarHeight(int mUnReachedProgressBarHeight) {
        this.mUnReachedProgressBarHeight = mUnReachedProgressBarHeight;
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

}
