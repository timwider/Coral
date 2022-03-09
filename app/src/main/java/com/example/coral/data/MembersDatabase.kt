package com.example.coral.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coral.utils.Converters

@Database(entities = [Member::class], version = 1)
abstract class MembersDatabase: RoomDatabase() {
    abstract fun membersDao(): MembersDao

    companion object {
        var INSTANCE: MembersDatabase? = null

        fun getDatabase(context: Context): MembersDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MembersDatabase::class.java,
                    "members_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}