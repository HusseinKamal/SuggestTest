package com.hussein.suggesttest.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.EditText

class MyEditText : EditText {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    fun init() {
        val tf = Typeface.createFromAsset(context.assets, "fonts/isfkut.otf")
        setTypeface(tf, 1)

    }
}
