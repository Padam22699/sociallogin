package com.example.sociallogin.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sociallogin.MainActivity
import com.example.sociallogin.Roomdb.Dao
import com.example.sociallogin.Roomdb.database
import com.example.sociallogin.databinding.ActivityLoginBinding
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.util.*


class Login : AppCompatActivity() {


    lateinit var userdao: Dao
    var isAllowed: Boolean = false
    lateinit var binding: ActivityLoginBinding
    private lateinit var analytics: FirebaseAnalytics
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var callbackManager: CallbackManager
    private var Email = "email"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        analytics = Firebase.analytics
        supportActionBar?.hide()



        binding.signuptoLogin.setOnClickListener {
            startActivity(Intent(this , Registration::class.java))
            finish()
        }
        facebookLogin()
        googleLogin()
        roomlogin()


    }

    private fun roomlogin() {

        val ref = getSharedPreferences("login" , Context.MODE_PRIVATE)
        val check: Boolean = ref.getBoolean("login" , false)
        if (check) {
            startActivity(Intent(this , MainActivity::class.java))
            finish()
        }

        binding.signin.setOnClickListener {
            userdao = database.getinstence(this).userDao()

            if (userdao.login(binding.userEmail.text.toString() ,
                    binding.userpass.text.toString())
            ) {
                ref.edit().putBoolean("login" , true).apply()
                startActivity(Intent(this , MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this , "invalid user" , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun googleLogin() {

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this , gso);

        val act: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (act != null) {
            finish()
            startActivity(Intent(this , MainActivity::class.java))
        }

        binding.googlebtn.setOnClickListener {
            var signInIent: Intent = gsc.signInIntent
            startActivityForResult(signInIent , 1000)
        }
    }

    private fun facebookLogin() {

        binding.facebookbtn.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this , Arrays.asList("public_profile"));
        }
        callbackManager = CallbackManager.Factory.create()
        val accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        if (accessToken != null && accessToken.isExpired == false) {
            finish()
            startActivity(Intent(this , MainActivity::class.java))
        }


        LoginManager.getInstance()
            .registerCallback(callbackManager , object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Toast.makeText(this@Login , "Somthing went wrong" , Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Login , Login::class.java))
                    finish()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@Login , "Somthing went wrong" , Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Login , Login::class.java))
                    finish()
                }

                override fun onSuccess(result: LoginResult) {

                    startActivity(Intent(this@Login , MainActivity::class.java))
                    finish()
                    val graphRequest =
                        GraphRequest.newMeRequest(result?.accessToken) { obj , response ->
                            try {

                                if (obj!!.has("id")) {
                                    Log.d("FACEBOOKDATA" , obj.getString("name"))
                                    Log.d("FACEBOOKDATA" , obj.getString("email"))
                                    Log.d("FACEBOOKDATA" , obj.getString("picture"))
                                }

                            } catch (e: Exception) {

                            }
                        }
                    val param = Bundle()
                    param.putString("field" , "name,email,id,picture.type(large)")
                    graphRequest.parameters = param
                    graphRequest.executeAsync()
                }

            })
    }


    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        callbackManager.onActivityResult(requestCode , resultCode , data)


        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                finish()
                startActivity(Intent(this , MainActivity::class.java))
            } catch (e: ApiException) {
                Toast.makeText(this , "Somthing went wrong" , Toast.LENGTH_SHORT).show()
            }
        }

    }


}