package com.dailydigest.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.R
import com.dailydigest.databinding.FragmentSettingsBinding
import com.dailydigest.dialog.RateUsDialog
import java.util.Calendar
import java.util.Locale

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            showFormat(R.drawable.img_ic_calendar, "Select Date Format", listDate)
        }

        binding.constraintLytTimeFormat.setOnClickListener {
            val listTime =
                arrayOf("Default", "24 Hour", "12 Hour")
            showFormat(R.drawable.ic_time, "Select Time Format", listTime)
        }

        binding.constraintLytRateUs.setOnClickListener {
            val rateUsDialog = RateUsDialog(requireContext())
            rateUsDialog.window?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transperent)))
            rateUsDialog.setCancelable(false)
            rateUsDialog.show()
        }

        //        feed back click event
        binding.constraintLytFeedback.setOnClickListener {
            val dateFormat = SimpleDateFormat("dd MMMM yy", Locale.US)
            val currentDate = dateFormat.format(Calendar.getInstance().time)

            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("meaasge/rfc822")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("pateltushar9601780945@gmail.com"))
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

        val itemList = listArray

        alertDialog.setSingleChoiceItems(itemList, checkedItem[0]) { dialog, which ->
            checkedItem[0] = which
        }

        alertDialog.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        alertDialog.setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }

        val customAlertDialog = alertDialog.create()
        customAlertDialog.show()
    }
}