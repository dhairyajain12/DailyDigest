package com.dailydigest

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dailydigest.databinding.FragmentLoginSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginSignUpActivity : AppCompatActivity() {

    private val TAG = "LoginSignUpActivity"

    private lateinit var loginSignUpBinding: FragmentLoginSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        auth = Firebase.auth
        loginSignUpBinding = FragmentLoginSignUpBinding.inflate(layoutInflater)
        setContentView(loginSignUpBinding.root)

    }

    override fun onStart() {
        super.onStart()

        // Login Sign Up Layout Views
        val loginLayout = loginSignUpBinding.llLogin
        val signUpLayout = loginSignUpBinding.llSignup

        // Login Sign Up Activity Views
        val loginView = loginSignUpBinding.tvLogin
        val signUpView = loginSignUpBinding.tvSignup

        // Login Form Components
        val loginEmail = loginSignUpBinding.edtEmailLogin
        val loginPassword = loginSignUpBinding.edtPasswordLogin
        val forgetPass = loginSignUpBinding.tvForgetPassword

        // Sign Up Form Components
        val signUpEmail = loginSignUpBinding.edtEmailSignup
        val signUpPassword = loginSignUpBinding.edtPasswordSignup
        val signUpConfirmPassword = loginSignUpBinding.edtConfirmPasswordSignup

        // Login Sign Up Button
        val loginSignUpBtn = loginSignUpBinding.btnLogin

// Sign Up Tab Click Listener
        signUpView.setOnClickListener {
            signUpView.background =
                ResourcesCompat.getDrawable(resources, R.drawable.custom_switch_tracks, null)
            signUpView.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            loginSignUpBtn.setText(R.string.app_sign_up)
            loginView.background = null
            loginView.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.custom_app_theme,
                    null
                )
            )
            signUpLayout.visibility = View.VISIBLE
            loginLayout.visibility = View.GONE
        }

// Login Tab Click Listener
        loginView.setOnClickListener {
            loginView.background =
                ResourcesCompat.getDrawable(resources, R.drawable.custom_switch_tracks, null)
            loginView.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            loginSignUpBtn.setText(R.string.app_login)
            signUpView.background = null
            signUpView.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.custom_app_theme,
                    null
                )
            )
            signUpLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }

// Login Button Click Event Listener
        loginSignUpBtn.setOnClickListener {
            if (loginSignUpBtn.text == getString(R.string.app_login)) {
                // Login Button Click Event
                if (loginEmail.text?.isEmpty() == false) {
                    if (Patterns.EMAIL_ADDRESS.matcher(loginEmail.text.toString()).matches()) {
                        loginEmail.error = null
                    } else {
                        loginEmail.error =
                            getString(R.string.app_email_error) // Email Invalid Error
                        loginEmail.requestFocus()
                    }
                } else if (loginPassword.text?.isEmpty() == true) {
                    loginPassword.error =
                        getString(R.string.app_password_error) // Password Empty Error
                    loginPassword.requestFocus()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            } else {
                if (loginSignUpBtn.text == getString(R.string.app_sign_up)) {
                    // Sign Up Button Click Event
                    if (signUpEmail.text?.isEmpty() == false) {
                        if (Patterns.EMAIL_ADDRESS.matcher(signUpEmail.text.toString()).matches()) {
                            signUpEmail.error = null
                        } else {
                            signUpEmail.error =
                                getString(R.string.app_email_error) // Email Invalid Error
                            signUpEmail.requestFocus()
                        }
                    } else if (signUpPassword.text?.isEmpty() == true) {
                        signUpPassword.error =
                            getString(R.string.app_password_error) // Password Empty Error
                        signUpPassword.requestFocus()
                    } else if (signUpConfirmPassword.text?.isEmpty() == true) {
                        signUpConfirmPassword.error =
                            getString(R.string.app_confirm_password_error) // Confirm Password Empty Error
                        signUpConfirmPassword.requestFocus()
                    } else {
                        if (signUpPassword.text.toString() != signUpConfirmPassword.text.toString()) {
                            signUpConfirmPassword.error =
                                getString(R.string.app_password_match_error) // Password Match Error
                            signUpConfirmPassword.requestFocus()
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    }
                }
            }
        }

// Forget Password Click Event Listener
        forgetPass.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}