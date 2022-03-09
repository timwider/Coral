package com.example.coral.data

import androidx.room.*

@Dao
interface MembersDao {

    @Insert
    fun insert(member: Member)

    @Update
    suspend fun update(member: Member)

    @Delete
    suspend fun delete(member: Member)

    @Query("SELECT * FROM MEMBERS")
    fun getAllMembers(): List<Member>



}