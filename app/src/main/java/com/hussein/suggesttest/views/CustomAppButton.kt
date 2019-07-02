package com.hussein.suggesttest.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Button


class CustomAppButton : Button {
    constructor(context: Context) : super(context) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()

    }

    private fun init() {
        val tf = Typeface.createFromAsset(context.assets,
                "fonts/isfkut.otf")
        typeface = tf
    }
}
