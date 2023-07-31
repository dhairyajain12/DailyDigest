package com.dailydigest

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dailydigest.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(forgotPasswordBinding.root)

        val fpEmail = forgotPasswordBinding.emailForgotPassword
        val fpSendBtn = forgotPasswordBinding.btnResetPassword
        val fpBack = forgotPasswordBinding.txtFpBackToLogin

        fpSendBtn.setOnClickListener {
            if (fpEmail.text?.isEmpty() == true) {
                fpEmail.error = getString(R.string.app_email_error)
                fpEmail.requestFocus()
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(fpEmail.text.toString()).matches()) {
                    fpEmail.error = null
                    fpEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    sendEmail()
                    fpBack.visibility = View.VISIBLE
                } else {
                    fpEmail.error = getString(R.string.app_email_error)
                    fpEmail.requestFocus()
                    fpBack.visibility = View.GONE
                }
            }
        }

        fpBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun sendEmail() {
        // Send Email to Reset Password
        Toast.makeText(this, "Email Sent Successfully", Toast.LENGTH_SHORT).show()
    }
}