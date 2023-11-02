package com.dailydigest

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dailydigest.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPasswordActivity : AppCompatActivity() {
    
    private lateinit var forgetPasswordBinding: ActivityForgetPasswordBinding
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgetPasswordBinding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(forgetPasswordBinding.root)
        auth = Firebase.auth
    }
    
    override fun onStart() {
        super.onStart()

        // Forget Password Form Components
        val fpEmail = forgetPasswordBinding.emailForgotPassword
        val fpSendBtn = forgetPasswordBinding.btnResetPassword
        
        // Forget Password Send Email Button Click Listener
        fpSendBtn.setOnClickListener { 
            if (fpEmail.text?.isEmpty() == true) {
                fpEmail.error = getString(R.string.app_email_error)
                fpEmail.requestFocus()
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(fpEmail.text.toString().trim()).matches()) {
                    fpEmail.error = null
                    fpEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    sendEmail(fpEmail.text.toString().trim())
                } else {
                    fpEmail.error = getString(R.string.app_email_error)
                    fpEmail.requestFocus()
                }
            }
        }
    }

    private fun sendEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener { _ ->
                Toast.makeText(this, "Email Sent Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginSignUpActivity::class.java))
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}