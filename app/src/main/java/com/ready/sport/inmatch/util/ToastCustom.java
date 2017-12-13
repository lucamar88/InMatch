package com.ready.sport.inmatch.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ready.sport.inmatch.R;

/**
 * Created by l.martelloni on 13/12/2017.
 */

public class ToastCustom extends Toast {

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastCustom(Context context, Drawable imageSrc, String textSrc) {
        super(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom_add,(ViewGroup)((Activity)context).getWindow().getDecorView().findViewById(R.id.toast_layout_root));


        AppCompatImageView image = (AppCompatImageView) layout.findViewById(R.id.image);
        image.setImageDrawable(imageSrc);
        TextViewPlus text = (TextViewPlus) layout.findViewById(R.id.text);
        text.setText(textSrc);


        this.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        this.setDuration(Toast.LENGTH_LONG);
        this.setView(layout);
    }

    @Override
    public void show(){
        super.show();
    }
}
