package com.hussein.suggesttest.util.apphelper

import android.text.TextUtils
class ValidateData {
    companion object{
        fun isValid(text: String?): Boolean {
            return text != "" && !TextUtils.isEmpty(text) && text != null && text.length > 0
        }
    }
}