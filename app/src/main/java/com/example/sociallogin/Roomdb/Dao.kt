package com.example.sociallogin.Roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
       fun insertInformation(userModel: userModel)

    @Query("SELECT EXISTS (SELECT * FROM userInfo WHERE  username = :username)")
    fun isTeken(username:String):Boolean

    @Query("SELECT EXISTS (SELECT * FROM userInfo WHERE  userEmail = :userEmail AND userpass = :password)")

    fun login(userEmail:String,password:String):Boolean
}