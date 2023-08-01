package com.dailydigest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView

class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerLayout = binding.drawerLayout

        val toolbar = binding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            context as Activity?,
            drawerLayout,
            toolbar,
            R.string.app_open,
            R.string.app_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.btnFloating.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreateJournalFragment()
            view.findNavController().navigate(action)
        }

        binding.icSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            view.findNavController().navigate(action)
        }

//        binding.icNotificaiton.setOnClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToNotificationFragment()
//            view.findNavController().navigate(action)
//        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_profile -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                    view.findNavController().navigate(action)
                }

                R.id.nav_calender -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToCalendarFragment()
                    view.findNavController().navigate(action)
                }

                R.id.nav_settings -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                    view.findNavController().navigate(action)
                }
            }
            true
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}