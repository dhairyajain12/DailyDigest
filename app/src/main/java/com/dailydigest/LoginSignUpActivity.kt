package com.dailydigest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dailydigest.databinding.ActivityLoginSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginSignUpActivity : AppCompatActivity() {

    // var declaration
    private val tag = "LoginSignUpActivity"
    private lateinit var loginSignUpBinding: ActivityLoginSignUpBinding
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // SplashScreen
        Thread.sleep(2000)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        Firebase.auth.uid?.let {
            Log.i(tag,it) }
        if (Firebase.auth.currentUser != null) {
            Toast.makeText(this@LoginSignUpActivity, "Welcome ${Firebase.auth.currentUser!!.displayName}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@LoginSignUpActivity, HomeActivity::class.java)
                .putExtra("email", auth.currentUser!!.email))
            finish()
        }
        loginSignUpBinding = ActivityLoginSignUpBinding.inflate(layoutInflater)
        setContentView(loginSignUpBinding.root)
    }

    override fun onStart() {
        super.onStart()

    // Login and SignUp Layouts
        val loginLayout = loginSignUpBinding.clLogin
        val signUpLayout = loginSignUpBinding.clSignup 
    // Login and SignUp Views
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
            signUpView.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_switch_tracks, null) // Set Background of SignUp View
            signUpView.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null)) // Set Text Color of SignUp View
            loginSignUpBtn.setText(R.string.app_sign_up) // Set Text of Login Sign Up Button
            loginView.background = null // Set Background of Login View to null
            loginView.setTextColor(ResourcesCompat.getColor(resources, R.color.color_app_theme, null)) // Set Text Color of Login View
            loginLayout.visibility = View.GONE
            signUpLayout.visibility = View.VISIBLE
        }

        // Login Tab Click Listener
        loginView.setOnClickListener {
            loginView.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_switch_tracks, null) // Set Background of Login View
            loginView.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null)) // Set Text Color of Login View
            loginSignUpBtn.setText(R.string.app_login) // Set Text of Login Sign Up Button
            signUpView.background = null // Set Background of SignUp View to null
            signUpView.setTextColor(ResourcesCompat.getColor(resources, R.color.color_app_theme, null)) // Set Text Color of SignUp View
            signUpLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }
        
        // Login Button Click Listener
        loginSignUpBtn.setOnClickListener {
            if (loginSignUpBtn.text == resources.getString(R.string.app_login)) {
                val email = loginEmail.text.toString().trim()
                val password = loginPassword.text.toString().trim()
                
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(loginSignUpBtn.windowToken, 0)
                
                if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    loginEmail.error = null
                    if (password.isNotEmpty()) {
                        loginPassword.error = null
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener { authResult ->
                                Log.i(tag, "Login successful: ${authResult.user!!.uid}")
                                Toast.makeText(this@LoginSignUpActivity, "Login successful: ${authResult.user!!.displayName}", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@LoginSignUpActivity, HomeActivity::class.java))
                            }
                            .addOnFailureListener { exception ->
                                Log.e(tag, "Login failed: ${exception.message}")
                                Toast.makeText(this@LoginSignUpActivity, "Login failed: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        loginPassword.error = resources.getString(R.string.app_password_error)
                        loginPassword.requestFocus()
                    }
                } else if (email.isEmpty()) {
                    loginEmail.error = resources.getString(R.string.app_email_error)
                    loginEmail.requestFocus()
                }
            }
            
            // Sign Up Button Click Listener
            if (loginSignUpBtn.text == resources.getString(R.string.app_sign_up)) {
                val email = signUpEmail.text.toString().trim()
                val password = signUpPassword.text.toString().trim()
                val confirmPassword = signUpConfirmPassword.text.toString().trim()
                
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(loginSignUpBtn.windowToken, 0)

                when {
                    email.isEmpty() -> {
                        signUpEmail.error = resources.getString(R.string.app_email_error)
                        signUpEmail.requestFocus()
                    }
                    password.isEmpty() -> {
                        signUpPassword.error = resources.getString(R.string.app_password_error)
                        signUpPassword.requestFocus()
                    }
                    confirmPassword.isEmpty() -> {
                        signUpConfirmPassword.error = resources.getString(R.string.app_password_error)
                        signUpConfirmPassword.requestFocus()
                    }
                    password != confirmPassword -> {
                        signUpConfirmPassword.error = resources.getString(R.string.app_password_match_error)
                        signUpConfirmPassword.requestFocus()
                    }
                    else -> {
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener { authResult ->
                                    val changeRequest = UserProfileChangeRequest.Builder()
                                        .setDisplayName(email.substringBefore("@"))
                                        .build()

                                    authResult.user!!.updateProfile(changeRequest)
                                    startActivity(Intent(this@LoginSignUpActivity, LoginSignUpActivity::class.java))
                                }
                                .addOnFailureListener { exception ->
                                    Log.e(tag, "Sign up failed: ${exception.localizedMessage}")
                                    Toast.makeText(this@LoginSignUpActivity, "Sign up failed: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            signUpEmail.error = resources.getString(R.string.app_email_error)
                            signUpEmail.requestFocus()
                        }
                    }
                }
            }
        }

        // Forget Password Click Listener
        forgetPass.setOnClickListener {  // Forget Password Click Event
            startActivity(Intent(this@LoginSignUpActivity, ForgetPasswordActivity::class.java))
        }
    }
}