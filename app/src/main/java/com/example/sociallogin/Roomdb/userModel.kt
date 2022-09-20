package com.example.sociallogin.Roomdb

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userInfo")
data class userModel (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val userId:Int,

    @ColumnInfo(name = "userName")
    val userName:String?=" ",

    @ColumnInfo(name = "userEmail")
    val userEmail:String?=" ",

    @ColumnInfo(name = "userpass")
    val userpass:String?=" "

    )