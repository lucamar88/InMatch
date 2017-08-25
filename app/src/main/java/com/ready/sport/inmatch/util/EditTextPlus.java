package com.ready.sport.inmatch.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;

import com.ready.sport.inmatch.Tools.FontCache;

/**
 * Created by l.martelloni on 25/08/2017.
 */

public class EditTextPlus extends AppCompatEditText{
    public EditTextPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextPlus(Context context) {
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
