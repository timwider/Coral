package com.example.coral.utils

import androidx.room.TypeConverter

class HistoryConverter {

    fun allVarsToDataRow(paymentDate: String, paymentSum: String, workouts: MutableList<String>): String {

        var ultimateData = ""

        var workoutsFormatted = ""
        workouts.forEach {workoutsFormatted += "$it-"}
        workoutsFormatted = workoutsFormatted.dropLast(1)

        return "$paymentDate:$paymentSum:$workoutsFormatted"
    }


    fun getDataFromDataRow(dataRow: String): Triple<String, String, String> {
        // data row with payment and 4 workouts
        // 19.02.2022:10000:19.02.2022-20.02.2022-21.02.2022-23.02.2022
        val paymentDate = dataRow.split(":")[0]
        val paymentSum = dataRow.split(":")[1]
        val workoutsRaw = dataRow.split(":")[2]
        val workoutsList = workoutsRaw.replace("-", "\n")

        return Triple(paymentDate, paymentSum, workoutsList)
    }


    @TypeConverter
    fun historyListToDbString(list: MutableList<String>?): String? {
        return if (list != null) {
            val cleanString = list.map { i -> i.toString() }.joinToString().replace(",", "")
            cleanString
        } else null
    }

    @TypeConverter
    fun dbStringToHistoryList(cleanString: String?): MutableList<String>? {
        if (cleanString != null) {
            val newList = mutableListOf<String>()
            newList += cleanString.split(" ")
            return newList
        } else return null
    }

}