package com.ready.sport.inmatch.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

import com.ready.sport.inmatch.Tools.FontCache;

/**
 * Created by l.martelloni on 25/08/2017.
 */

public class AutoCompleteTextViewPlus extends AppCompatAutoCompleteTextView{
    public AutoCompleteTextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AutoCompleteTextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoCompleteTextViewPlus(Context context) {
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
