package com.example.kolkoikrzyzyk

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.example.kolkoikrzyzyk.GameActivity
import com.example.kolkoikrzyzyk.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.sign

class
MainActivity : Activity(),View.OnClickListener {


    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    lateinit var editTextEmail:EditText
    lateinit var editTextPassword:EditText
    lateinit var btnSignin:Button

    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private var database= FirebaseDatabase.getInstance()
    private var myRef=database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        init()
        mFirebaseAnalytics= FirebaseAnalytics.getInstance(this)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    private fun init(){
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword=findViewById(R.id.editTextPassword)
        btnSignin=findViewById(R.id.btnSignin)


        btnSignin.setOnClickListener(this)

        progressDialog= ProgressDialog(this)

    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.btnSignin -> createAccount(editTextEmail.text.toString(),editTextPassword.text.toString())//create account


        }

    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        callGameActivity(currentUser)
    }

    private fun createAccount(email: String, password: String) {
        //Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        if(user!=null) {
                            myRef.child("Users").child(SplitString(user.email.toString())).child("Request").setValue(user.uid)
                        }
                        callGameActivity(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Login",task.exception.toString())
                        if (task.exception.toString().contains("The email address is already in use by another account.")){
                            signIn(email,password)//if already a user,call sign in
                        }else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        callGameActivity(user)// call GameActivity
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
        // [END sign_in_with_email]
    }


    private fun validateForm(): Boolean {
        var valid = true

        val email = editTextEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            editTextEmail.error = "Required."
            valid = false
        } else {
            editTextEmail.error = null
        }

        val password = editTextPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            editTextPassword.error = "Required."
            valid = false
        } else {
            editTextPassword.error = null
        }

        return valid
    }

    private fun showProgressDialog(){
        if (!progressDialog.isShowing) {
            progressDialog.setTitle("Please wait")
            progressDialog.setMessage("Loading...")
            progressDialog.show()
        }
    }

    private fun hideProgressDialog(){
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }



    private fun callGameActivity(user : FirebaseUser?){
        if (user!=null){
            var intent = Intent(this, GameActivity::class.java)
            //pass email and uid for reference
            intent.putExtra("email", user.email)
            intent.putExtra("uid", user.uid)
            startActivity(intent)
            finish()
        }
    }
    fun  SplitString(str:String):String{
        var split=str.split("@")
        return split[0]
    }
}