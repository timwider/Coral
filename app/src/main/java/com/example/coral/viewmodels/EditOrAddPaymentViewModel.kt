package com.example.coral.viewmodels

import android.app.DatePickerDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coral.utils.Converters

class EditOrAddPaymentViewModel: ViewModel() {

    val selectedDate = MutableLiveData<String>()
    val isPaymentAdded = MutableLiveData(false)

    private fun formatDateFromDatePicker(day: Int, month: Int, year: Int): String {
        val formattedMonth = Converters().datePickerMonthToStringNum(month)
        return "$day.$formattedMonth.$year"
    }

    fun setDaysFromPayment(daysLeft: Int): String = (daysLeft + 10).toString()

    fun onDateSelectedListener() =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            selectedDate.value = formatDateFromDatePicker(day, month, year)
            isPaymentAdded.value = true
        }

    private fun createStringFromDateAndSum(newPaymentSum: String, currentPayment: String): String {

        return if (newPaymentSum.isNotEmpty()) {
            "${selectedDate.value} $newPaymentSum"
        } else currentPayment
    }

    fun provideNewPayment(newPaymentSum: String, defaultPaymentValue: String): String {
        return if (isPaymentAdded.value!!) {
            createStringFromDateAndSum(newPaymentSum, selectedDate.value!!)
        } else defaultPaymentValue
    }

    fun provideWorkouts(
        newPayment: String,
        currentPayment: String,
        workouts:  MutableList<String>?): MutableList<String>? {
        return if (newPayment == currentPayment) {
            workouts
        } else null
    }
}