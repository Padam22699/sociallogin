package com.example.sociallogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sociallogin.Activity.Login
import com.example.sociallogin.databinding.ActivityMainBinding
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var logoutbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ini()
        fragmentnavigation()

        logoutbtn.setOnClickListener {
            googlesignout()
            facebookklogout()

        }
    }

    private fun fragmentnavigation() {
        binding.bottomNavBar.setItemSelected(R.id.home)
        binding.bottomNavBar.setOnItemSelectedListener {
           when(it){
             R.id.home -> Toast.makeText(this , "home Selected" , Toast.LENGTH_SHORT).show()
             R.id.Orders -> Toast.makeText(this , "order Selected" , Toast.LENGTH_SHORT).show()
             R.id.Profiles -> Toast.makeText(this , "profile Selected" , Toast.LENGTH_SHORT).show()
             R.id.Support -> Toast.makeText(this , "suppord Selected" , Toast.LENGTH_SHORT).show()

           }
        }
    }

    private fun ini() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this , gso);
        logoutbtn = findViewById(R.id.logout)
    }

    private fun facebookklogout() {
        LoginManager.getInstance().logOut()
        startActivity(Intent(this , MainActivity::class.java))
        finish()
    }

    private fun googlesignout() {
        gsc.signOut().addOnCompleteListener() {
            finish()
            startActivity(Intent(this , Login::class.java))
        }
    }


}