package com.example.coral.utils

import androidx.room.TypeConverter

class WorkoutsConverter {

    @TypeConverter
    fun toWorkouts(cleanString: String?): MutableList<String>? {
        if (cleanString != null) {
            val newList = mutableListOf<String>()
            newList += cleanString.split(" ")
            return newList
        } else return null
    }

    @TypeConverter
    fun toStr(list: MutableList<String>?): String? {
        if (list != null) {
            val cleanString = list.map { i -> i.toString() }.joinToString().replace(",", "")
            return cleanString
        } else return null
    }


}