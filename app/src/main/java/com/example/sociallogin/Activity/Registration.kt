package com.example.sociallogin.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sociallogin.MainActivity
import com.example.sociallogin.R
import com.example.sociallogin.Roomdb.Dao
import com.example.sociallogin.Roomdb.database
import com.example.sociallogin.Roomdb.userModel
import com.example.sociallogin.databinding.ActivityRegistrationBinding
import kotlinx.coroutines.Dispatchers

class Registration : AppCompatActivity() {
    lateinit var ref: SharedPreferences
      var isAllowed:Boolean=false
lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        roomregiter()

    }
    private fun roomregiter() {
       val  userdao =  database.getinstence(this).userDao()

        if(userdao.isTeken(binding.username.toString())){
            isAllowed=false
            Toast.makeText(this , "Name Alredy taken" , Toast.LENGTH_SHORT).show()
        }else isAllowed=true

        binding.SignUp.setOnClickListener {
            var name=binding.username.text.toString()
            var email=binding.Email.text.toString()
            var pass=binding.password.text.toString()


            if(isAllowed){
                var userinfo=userModel( 0,name,email,pass)

                userdao.insertInformation(userinfo)
                ref.edit().putBoolean("login" , true).apply()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            Toast.makeText(this , "Name Alredy taken" , Toast.LENGTH_SHORT).show()
        }

    }
}