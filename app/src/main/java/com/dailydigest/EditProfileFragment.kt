package com.dailydigest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EditProfileFragment : Fragment() {

    private var textWatcherValue = false
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textWatcherValue = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textWatcherValue = !textWatcherValue
            }

            override fun afterTextChanged(s: Editable?) {
                textWatcherValue = true
            }
        }
        binding.edtName.editText?.addTextChangedListener(textWatcher)
        binding.edtEmail.editText?.addTextChangedListener(textWatcher)
        binding.edtDob.editText?.addTextChangedListener(textWatcher)
        binding.edtMno.editText?.addTextChangedListener(textWatcher)
        binding.edtDob.editText?.addTextChangedListener(textWatcher)

        binding.icBack.setOnClickListener {
            if (textWatcherValue) {
                showDiscardAlert()
            } else {
                val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
                view.findNavController().navigate(action)
            }
        }

        binding.btnSave.setOnClickListener {
            val imm = getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
            //TODO("Save Data to Firebase and update profile UI")
            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
            view.findNavController().navigate(action)
        }

        binding.edtDob.setEndIconOnClickListener {
            showCalendar()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
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
        builder.setNegativeButton("No") { _, _ -> builder.create().dismiss() }
        builder.create()
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun showCalendar() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Date of Birth")
        val alertView = layoutInflater.inflate(R.layout.calendar_dialog, null)
        builder.setView(alertView)
        val txtDob = binding.edtDob.editText
        val calendarView = alertView.findViewById<CalendarView>(R.id.calendarDialogView)
        var birthDate = ""
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            birthDate = date
            Log.d("TAG", "Birth Date : $birthDate")
        }
        builder.setPositiveButton("Ok") { _, _ -> txtDob?.setText(birthDate) }
        builder.setNegativeButton("Cancel") { _, _ -> builder.create().dismiss() }
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }
}