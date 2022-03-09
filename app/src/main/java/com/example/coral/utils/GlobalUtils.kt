package com.example.coral.utils

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

// The purpose of this class is to provide methods that are used more than once,
// so that code is less repetitive
class GlobalUtils {

    fun setupDatePicker(context: Context, listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, listener, y, m, d).apply {

            this.setButton(DatePickerDialog.BUTTON_POSITIVE, "Выбрать", this)
            this.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Отмена", this)
        }.show()
    }
}