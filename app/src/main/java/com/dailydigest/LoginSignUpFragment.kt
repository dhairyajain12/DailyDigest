package com.dailydigest

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentLoginSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class LoginSignUpFragment : Fragment() {

    // var declaration
    private val TAG = "LoginSignUpFragment"
    private var loginSignUpFragmentBinding: FragmentLoginSignUpBinding? = null
    private val binding get() = loginSignUpFragmentBinding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        loginSignUpFragmentBinding = FragmentLoginSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Login Sign Up Fragment View Created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Login and SignUp Layouts
        val loginLayout = loginSignUpFragmentBinding!!.llLogin
        val signUpLayout = loginSignUpFragmentBinding!!.llSignup
        
        // Login and SignUp Views
        val loginView = loginSignUpFragmentBinding!!.tvLogin
        val signUpView = loginSignUpFragmentBinding!!.tvSignup
        
        // Login Form Components
        val loginEmail = loginSignUpFragmentBinding!!.edtEmailLogin
        val loginPassword = loginSignUpFragmentBinding!!.edtPasswordLogin
        val forgetPass = loginSignUpFragmentBinding!!.tvForgetPassword
        
        // Sign Up Form Components
        val signUpEmail = loginSignUpFragmentBinding!!.edtEmailSignup
        val signUpPassword = loginSignUpFragmentBinding!!.edtPasswordSignup
        val signUpConfirmPassword = loginSignUpFragmentBinding!!.edtConfirmPasswordSignup
        
        // Login Sign Up Button
        val loginSignUpBtn = loginSignUpFragmentBinding!!.btnLogin
        
        // Sign Up Tab Click Listener
        signUpView.setOnClickListener {
            signUpView.background = 
                ResourcesCompat.getDrawable(
                    resources, 
                    R.drawable.custom_switch_tracks, 
                    null) // Set Background of SignUp View
            signUpView.setTextColor(
                ResourcesCompat.getColor(
                    resources, 
                    R.color.textColor, 
                    null) // Set Text Color of SignUp View
            )
            loginSignUpBtn.setText(R.string.app_sign_up) // Set Text of Login Sign Up Button
            loginView.background = null // Set Background of Login View to null
            loginView.setTextColor(
                ResourcesCompat.getColor(
                    resources, 
                    R.color.custom_app_theme, 
                    null) // Set Text Color of Login View
            )
            loginLayout.visibility = View.GONE
            signUpLayout.visibility = View.VISIBLE
        }
        
        // Login Tab Click Listener
        loginView.setOnClickListener {
            loginView.background = 
                ResourcesCompat.getDrawable(
                    resources, 
                    R.drawable.custom_switch_tracks, 
                    null) // Set Background of Login View
            loginView.setTextColor(
                ResourcesCompat.getColor(
                    resources, 
                    R.color.textColor, 
                    null) // Set Text Color of Login View
            )
            loginSignUpBtn.setText(R.string.app_login) // Set Text of Login Sign Up Button
            signUpView.background = null // Set Background of SignUp View to null
            signUpView.setTextColor(
                ResourcesCompat.getColor(
                    resources, 
                    R.color.custom_app_theme, 
                    null) // Set Text Color of SignUp View
            )
            signUpLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }
        
        // Login Sign Up Button Click Listener
        loginSignUpBtn.setOnClickListener { 
            if (loginSignUpBtn.text == resources.getString(R.string.app_login)) { // Check text on Login Sign Up Button
                // Login Button Click Event
                if (loginEmail.text?.isEmpty() == false) { // Check Email is Empty or Not
                    if (Patterns.EMAIL_ADDRESS.matcher(loginEmail.text.toString()).matches()) { // Check Email is in valid pattern or Not
                        loginEmail.error = null
                    } else {
                        loginEmail.error = resources.getString(R.string.app_email_error) // Email Invalid Error
                        loginEmail.requestFocus()
                    }
                } else if (loginPassword.text?.isEmpty() == true) { // Check Password is Empty or Not
                    loginPassword.error = resources.getString(R.string.app_password_error) // Password Empty Error
                    loginPassword.requestFocus()
                } else {
                    //TODO: Firebase Login Authentication Code
                    val action = LoginSignUpFragmentDirections.actionLoginSignUpFragmentToHomeFragment()
                    view.findNavController().navigate(action)
                }
            } else {
                if (loginSignUpBtn.text == resources.getString(R.string.app_sign_up)) { // Check text on Login Sign Up Button
                    // Sign Up Button Click Event
                    if (signUpEmail.text?.isEmpty() == false) { // Check Email is Empty or Not
                        if (Patterns.EMAIL_ADDRESS.matcher(signUpEmail.text.toString()).matches()) { // Check Email is in valid pattern or Not
                            signUpEmail.error = null
                        }else {
                            signUpEmail.error = resources.getString(R.string.app_email_error) // Email Invalid Error
                            signUpEmail.requestFocus()
                        }
                    } else if (signUpPassword.text?.isEmpty() == true) { // Check Password is Empty or Not
                        signUpPassword.error = resources.getString(R.string.app_password_error) // Password Empty Error
                        signUpPassword.requestFocus()
                    } else if (signUpConfirmPassword.text?.isEmpty() == true) { // Check Confirm Password is Empty or Not
                        signUpConfirmPassword.error = resources.getString(R.string.app_password_error) // Confirm Password Empty Error
                        signUpConfirmPassword.requestFocus()
                    } else if (signUpPassword.text.toString() != signUpConfirmPassword.text.toString()) { // Check Password and Confirm Password Match or Not
                        signUpConfirmPassword.error = resources.getString(R.string.app_password_match_error) // Password Match Error
                        signUpConfirmPassword.requestFocus()
                    } else {
                        //TODO: Firebase Sign Up Authentication Code
                        signUpNewUser(signUpEmail.text.toString(), signUpPassword.text.toString())
                        val action = LoginSignUpFragmentDirections.actionLoginSignUpFragmentToHomeFragment()
                        view.findNavController().navigate(action)
                    }
                }
            }
        }
        
        // Forget Password Click Event Listener
        forgetPass.setOnClickListener {  // Forget Password Click Event
            val action = LoginSignUpFragmentDirections.actionLoginSignUpFragmentToForgetPasswordFragment()
            view.findNavController().navigate(action)
            // remove fragment from back stack to prevent it returning on back press
            activity?.supportFragmentManager?.popBackStack()
        }
    }
    
    // Private sign up function via for sign up via firebase
    private fun signUpNewUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val action =
                        LoginSignUpFragmentDirections.actionLoginSignUpFragmentToHomeFragment()
                    view?.findNavController()?.navigate(action)
                } else {
                    // If sign up fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "SignUp failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}