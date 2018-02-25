package com.xclib.mvvm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, ViewModel> creators;

    public ViewModelFactory(@Nullable Class<? extends ViewModel> classViewModel, @Nullable ViewModel viewModel) {
        Map<Class<? extends ViewModel>,ViewModel> modelMap = new HashMap<>();
        modelMap.put(classViewModel,viewModel);
        this.creators = modelMap;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        ViewModel creator = creators.get(modelClass);
        if (creator == null) {
            for (Class<? extends ViewModel> entry : creators.keySet()) {
                if (modelClass.isAssignableFrom(entry)) {
                    creator = creators.get(entry);
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            return (T) creator;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
