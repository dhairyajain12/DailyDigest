package com.dailydigest

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.dailydigest.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {

    private var forgetPasswordFragmentBinding: FragmentForgetPasswordBinding? = null
    private val binding get() = forgetPasswordFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        forgetPasswordFragmentBinding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fpEmail = forgetPasswordFragmentBinding!!.emailForgotPassword
        val fpSendBtn = forgetPasswordFragmentBinding!!.btnResetPassword
        val fpBack = forgetPasswordFragmentBinding!!.txtFpBackToLogin

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
//            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun sendEmail() {
        // Send Email to Reset Password
        Toast.makeText(requireContext(), "Email Sent Successfully", Toast.LENGTH_SHORT).show()
    }
}