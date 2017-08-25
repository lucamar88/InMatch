package com.ready.sport.inmatch.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.ready.sport.inmatch.Tools.FontCache;

/**
 * Created by l.martelloni on 25/08/2017.
 */

public class TextInputLayoutPlus extends TextInputLayout{
    public TextInputLayoutPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextInputLayoutPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextInputLayoutPlus(Context context) {
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
