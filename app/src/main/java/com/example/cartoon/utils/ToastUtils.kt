package com.example.cartoon.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

object ToastUtils {

    @IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
    @Target(
        AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    @MustBeDocumented
    annotation class ToastLengthDef


    fun Activity.showToast(@StringRes resource:Int, @ToastLengthDef toastLength:Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, getStringFromResource(resource), toastLength).show()
    }

    fun Context.showToast(message:String, @ToastLengthDef toastLength: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, message, toastLength).show()
    }

    fun Activity.getStringFromResource(@StringRes resource:Int): String {
        return ContextCompat.getString(this, resource)
    }

}