package com.example.coral.viewmodels

import android.app.DatePickerDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coral.data.Member
import com.example.coral.utils.Converters

class AddNewMemberViewModel: ViewModel() {

    val selectedDate = MutableLiveData<String>()

    fun onDateSetListener() =
        DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            selectedDate.value = Converters().datePickerDateToString2(day, month, year, true)
        }

    fun createHelperText(): String {
        return "Дата: ${selectedDate.value!!}"
    }
}