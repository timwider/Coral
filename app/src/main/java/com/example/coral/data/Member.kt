package com.example.coral.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.coral.utils.Converters
import com.example.coral.utils.HistoryConverter
import com.example.coral.utils.MemberCode
import com.example.coral.utils.WorkoutsConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "members")
@Parcelize
data class Member(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    @ColumnInfo var name: String,
    @ColumnInfo var phone: String?,
    @ColumnInfo var daysLeft: Int,
    @ColumnInfo @field:TypeConverters(Converters::class) var currentPayment: String?,
    @ColumnInfo @field:TypeConverters(WorkoutsConverter::class) var workouts:  MutableList<String>?,
    @ColumnInfo @field:TypeConverters(HistoryConverter::class)  var memberHistory: MutableList<String>?,
    @ColumnInfo var note: String?

) : Parcelable


