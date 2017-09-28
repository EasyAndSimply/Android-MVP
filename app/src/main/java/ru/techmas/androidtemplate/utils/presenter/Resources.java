package ru.techmas.androidtemplate.utils.presenter;

import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import ru.techmas.androidtemplate.App;

public class Resources {

    private App app;

    public Resources(App app) {
        this.app = app;
    }

    @NonNull
    public final String getString(@StringRes int resId) {
        return app.getString(resId);
    }

    @NonNull
    public final String[] getStringArray(@ArrayRes int resId) {
        return app.getResources().getStringArray(resId);
    }

}
