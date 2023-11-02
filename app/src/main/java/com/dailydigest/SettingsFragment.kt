package com.dailydigest

import android.app.AlertDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icBack.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }

        binding.constraintLytNotification.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToNotificationFragment()
            view.findNavController().navigate(action)
        }

        binding.constraintLytReminder.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToReminderFragment()
            view.findNavController().navigate(action)
        }

        binding.constraintLytDateFormat.setOnClickListener {
            val listDate =
                arrayOf("20 July 2023", "20 July 2023 9:28 am", "20 July 2023 9:28 am Sun")
            showFormat(R.drawable.ic_calendar, "Select Date Format", listDate)
        }

        binding.constraintLytTimeFormat.setOnClickListener {
            val listTime =
                arrayOf("Default", "24 Hour", "12 Hour")
            showFormat(R.drawable.ic_time, "Select Time Format", listTime)
        }

        //        feed back click event
        binding.constraintLytFeedback.setOnClickListener {
            val dateFormat = SimpleDateFormat("dd MMMM yy", Locale.US)
            val currentDate = dateFormat.format(Calendar.getInstance().time)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("dhairyajainrocks@gmail.com"))
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Feedback : Daily Digest application [$currentDate]"
            )
            intent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(Intent.createChooser(intent, "Choose an email client : "))
        }
    }

    private fun showFormat(icon: Int, title: String, listArray: Array<String>) {
        val checkedItem = intArrayOf(0)

        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setIcon(icon)
        alertDialog.setTitle(title)

        alertDialog.setSingleChoiceItems(listArray, checkedItem[0]) { _, which ->
            checkedItem[0] = which
        }

        alertDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        alertDialog.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

        val customAlertDialog = alertDialog.create()
        customAlertDialog.show()
    }
}
