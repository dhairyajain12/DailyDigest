package com.dailydigest.Fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.findNavController
import com.dailydigest.R
import com.dailydigest.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {
    
    // var declaration
    private var forgetPasswordFragmentBinding: FragmentForgetPasswordBinding? = null
    private val binding get() = forgetPasswordFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        forgetPasswordFragmentBinding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Forget Password Fragment View Created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Forget Password Form Components
        val fpEmail = forgetPasswordFragmentBinding!!.emailForgotPassword
        val fpSendBtn = forgetPasswordFragmentBinding!!.btnResetPassword
        val fpBack = forgetPasswordFragmentBinding!!.txtFpBackToLogin

        // Forget Password Send Email Button Click Listener
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

        // Forget Password Back to Login Click Listener
        fpBack.setOnClickListener {
            val action = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToLoginSignUpFragment()
            view.findNavController().navigate(action)
        }
    }

    // Send Email Private Function
    private fun sendEmail() {
        // Send Email to Reset Password
        Toast.makeText(requireContext(), "Email Sent Successfully", Toast.LENGTH_SHORT).show()
    }
}