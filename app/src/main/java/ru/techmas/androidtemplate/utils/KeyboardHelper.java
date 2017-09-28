package ru.techmas.androidtemplate.utils;

import android.app.Activity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyboardHelper {

    private static final String TAG = KeyboardHelper.class.getSimpleName();

    //@formatter:off
    private KeyboardHelper(){}
    //@formatter:on

    public static void hide(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d(TAG, "hide: Keyboard is not found");
        }
    }
}
