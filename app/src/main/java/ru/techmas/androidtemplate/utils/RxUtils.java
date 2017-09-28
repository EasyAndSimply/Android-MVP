package ru.techmas.androidtemplate.utils;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RxUtils {

    public static <T> ObservableTransformer<T, T> httpSchedulers() {
        return observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }
}
