package com.dailydigest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EditProfileFragment : Fragment() {
    
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.icBack.setOnClickListener {
            showDiscardAlert()
        }
        
        binding.btnSave.setOnClickListener {
            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
            view.findNavController().navigate(action)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun showDiscardAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Discard Changes")
        builder.setMessage("Are you sure you want to discard the changes?")
        builder.setPositiveButton("Yes") { _, _ ->
            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
            requireView().findNavController().navigate(action)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.show()
    }
}