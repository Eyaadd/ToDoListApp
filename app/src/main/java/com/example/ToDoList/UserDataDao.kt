package com.example.ToDoList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userData: UserData)

    @Query("SELECT * FROM user_data")
    suspend fun getAllUserData(): List<UserData>

    @Delete
    suspend fun deleteUserData(userData: UserData)
}

