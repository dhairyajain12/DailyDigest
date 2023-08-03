package com.dailydigest.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentNotificationListBinding


class NotificationListFragment : Fragment() {
//    var declaration
    private var _binding : FragmentNotificationListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding initialization
        _binding = FragmentNotificationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        back button click event
        binding.icBack.setOnClickListener {
            val action = NotificationListFragmentDirections.actionNotificationListFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
    }
}