package com.dailydigest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dailydigest.databinding.FragmentLoginSignUpBinding

class LoginSignUpFragment : Fragment() {

    private var loginsignupFragmentBinding: FragmentLoginSignUpBinding? = null
    private val binding get() = loginsignupFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginsignupFragmentBinding = FragmentLoginSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: Implement Login SignUp Validation Logic from Fragment
    }
}