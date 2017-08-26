package com.ready.sport.inmatch.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.ready.sport.inmatch.Tools.FontCache;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class TextViewPlus extends AppCompatTextView{
    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewPlus(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = FontCache.get("fonts/AvenirLTStd-Light.otf", getContext());
            setTypeface(tf);
        }
    }
}
