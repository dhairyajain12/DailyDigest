package com.dailydigest

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.findNavController
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
            if (fpEmail.text?.isEmpty() == true) { // Check if Email is Empty or Not
                fpEmail.error = getString(R.string.app_email_error)
                fpEmail.requestFocus()
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(fpEmail.text.toString()).matches()) { // Check if Email is in valid pattern or Not
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
            //TODO: Check if firebase send mail task is successful or not before navigating to login fragment
            val action = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToLoginSignUpFragment()
            view.findNavController().navigate(action)
            // remove fragment from back stack to prevent returning to this fragment
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    // Send Email Private Function
    private fun sendEmail() {
        // Send Email to Reset Password
        //TODO: Firebase Send Email Code
        Toast.makeText(requireContext(), "Email Sent Successfully", Toast.LENGTH_SHORT).show()
    }
}