package com.xclib.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.lzy.imagepicker.ui.ImageGridActivity;

/**
 * Created by xiongchang on 17/7/7.
 */

public class ImagePickerUtil {

    public static final int REQUEST_OPEN_GALLERY = 10001;

    /**
     * 重写onActivityResult
     *
     * if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
     if (data != null && requestCode == IMAGE_PICKER) {
     ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
     } else {
     Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
     }
     }


     ActionSheet  在style中添加   <item name="actionSheetStyle">@style/ActionSheetStyleiOS7</item>

//     *
//     * @param context
//     * @param fragmentManager
//     */
//    public static void chooseImage(final Context context, FragmentManager fragmentManager){
//        ActionSheet.createBuilder(context,fragmentManager)
//                .setCancelButtonTitle("取消")
//                .setOtherButtonTitles("相机", "相册")
//                .setCancelableOnTouchOutside(true)
//                .setListener(new ActionSheet.ActionSheetListener() {
//                    @Override
//                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
//
//                    }
//
//                    @Override
//                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
//                        switch (index){
//                            case 0:
//                                Intent intent1 = new Intent(context, ImageGridActivity.class);
//                                intent1.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
//                                ((Activity)context).startActivityForResult(intent1, ProjectConfig.REQUES_OPEN_CAMERA);
//                                break;
//
//                            case 1:
//                                Intent intent2 = new Intent(context, ImageGridActivity.class);
//                                ((Activity)context).startActivityForResult(intent2, ProjectConfig.REQUES_OPEN_GALLERY);
//                                break;
//                        }
//                    }
//                }).show();
//    }

    public static void chooseImage(final Activity activity){
        Intent intent2 = new Intent(activity, ImageGridActivity.class);
        activity.startActivityForResult(intent2, REQUEST_OPEN_GALLERY);
    }


}
