package ru.techmas.androidtemplate.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {

    //@formatter:off
    private ImageLoader(){}
    //@formatter:on


    public static void load(@NonNull Context context, @NonNull ImageView imageView, @NonNull String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
        // TODO: 04.06.2017 PlaceHolder here and any logic

    }

}
