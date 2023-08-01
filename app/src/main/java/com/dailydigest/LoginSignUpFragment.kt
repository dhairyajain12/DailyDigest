package com.dailydigest

import android.content.res.Resources
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentLoginSignUpBinding

class LoginSignUpFragment : Fragment() {

    // var declaration
    private val TAG = "LoginSignUpFragment"
    private var loginSignUpFragmentBinding: FragmentLoginSignUpBinding? = null
    private val binding get() = loginSignUpFragmentBinding!!
    
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
            signUpView.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_switch_tracks, null)
            signUpView.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            loginSignUpBtn.setText(R.string.app_sign_up)
            loginView.background = null
            loginView.setTextColor(ResourcesCompat.getColor(resources, R.color.custom_app_theme, null))
            loginLayout.visibility = View.GONE
            signUpLayout.visibility = View.VISIBLE
        }
        
        // Login Tab Click Listener
        loginView.setOnClickListener {
            loginView.background = ResourcesCompat.getDrawable(resources, R.drawable.custom_switch_tracks, null)
            loginView.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            loginSignUpBtn.setText(R.string.app_login)
            signUpView.background = null
            signUpView.setTextColor(ResourcesCompat.getColor(resources, R.color.custom_app_theme, null))
            signUpLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }
        
        // Login Sign Up Button Click Listener
        loginSignUpBtn.setOnClickListener { 
            if (loginSignUpBtn.text == resources.getString(R.string.app_login)) {
                // Login Button Click Event
                if (loginEmail.text?.isEmpty() == false) {
                    if (Patterns.EMAIL_ADDRESS.matcher(loginEmail.text.toString()).matches()) {
                        loginEmail.error = null
                    }else {
                        loginEmail.error = resources.getString(R.string.app_email_error) // Email Invalid Error
                        loginEmail.requestFocus()
                    }
                } else if (loginPassword.text?.isEmpty() == true) {
                    loginPassword.error = resources.getString(R.string.app_password_error) // Password Empty Error
                    loginPassword.requestFocus()
                } else {
                    val action = LoginSignUpFragmentDirections.actionLoginSignUpFragmentToHomeFragment()
                    view.findNavController().navigate(action)
                }
            } else {
                if (loginSignUpBtn.text == resources.getString(R.string.app_sign_up)) {
                    // Sign Up Button Click Event
                    if (signUpEmail.text?.isEmpty() == false) {
                        if (Patterns.EMAIL_ADDRESS.matcher(signUpEmail.text.toString()).matches()) {
                            signUpEmail.error = null
                        }else {
                            signUpEmail.error = resources.getString(R.string.app_email_error) // Email Invalid Error
                            signUpEmail.requestFocus()
                        }
                    } else if (signUpPassword.text?.isEmpty() == true) {
                        signUpPassword.error = resources.getString(R.string.app_password_error) // Password Empty Error
                        signUpPassword.requestFocus()
                    } else if (signUpConfirmPassword.text?.isEmpty() == true) {
                        signUpConfirmPassword.error = resources.getString(R.string.app_password_error) // Confirm Password Empty Error
                        signUpConfirmPassword.requestFocus()
                    } else if (signUpPassword.text.toString() != signUpConfirmPassword.text.toString()) {
                        signUpConfirmPassword.error = resources.getString(R.string.app_password_match_error) // Password Match Error
                        signUpConfirmPassword.requestFocus()
                    } else {
                        val action = LoginSignUpFragmentDirections.actionLoginSignUpFragmentToHomeFragment()
                        view.findNavController().navigate(action)
                    }
                }
            }
        }
        
        // Forget Password Click Event Listener
        forgetPass.setOnClickListener { 
            val action = LoginSignUpFragmentDirections.actionLoginSignUpFragmentToForgetPasswordFragment()
            view.findNavController().navigate(action)
        }
    }
}