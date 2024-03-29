package com.example.coral.utils

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent

class NoteEditText: androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            this.clearFocus()
        }
        return super.onKeyPreIme(keyCode, event)
    }
}