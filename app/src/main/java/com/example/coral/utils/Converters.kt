package com.example.coral.utils

import androidx.room.TypeConverter
import com.example.coral.data.Member

class Converters {


    @TypeConverter
    fun toList(dateAndSum: String): MutableList<String> {
        val date = dateAndSum.split(" ")[0]
        val sum = dateAndSum.split(" ")[1]

        return  mutableListOf("$date $sum")
    }

    @TypeConverter
    fun fromListToString(list: MutableList<String>): String {
        var str = ""
        list.forEach { str += it}
        return str
    }

    fun datePickerMonthToStringNum(datePickerMonth: Int): String {
        return when(datePickerMonth) {
            in 0..8 -> "0${datePickerMonth + 1}"
            else -> "${datePickerMonth + 1}"
        }
    }

    fun datePickerDateToString(m: Int, d: Int): String {

        val monthStr = when (m + 1) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> "января"
        }
        return "$d $monthStr"
    }

    fun datePickerDateToString2(day: Int, month: Int, year: Int, isFullDate: Boolean): String {

        val fullMonth = when (month + 1) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> "января"
        }

        val numMonth = when(month) {
            in 0..8 -> "0${month + 1}"
            else -> "${month + 1}"
        }

        return if (isFullDate) {
            "$day.$numMonth.$year"
        } else "$day $fullMonth"
    }
}