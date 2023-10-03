package com.dailydigest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
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
        
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<TextView>(R.id.txt_username)
        val navUserEmail = headerView.findViewById<TextView>(R.id.txt_user_email)
        navUsername.text = auth.currentUser?.displayName ?: "UserData"
        navUserEmail.text = auth.currentUser?.email ?: "abc@xyz.com"

        binding.btnFloating.setOnClickListener { 
            val intent = Intent(activity, CreateJournalActivity::class.java)
            startActivity(intent)
        }
        
        binding.icNotification.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNotificationListFragment()
            view.findNavController().navigate(action)
        }
        
        binding.icSearch.setOnClickListener { 
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            view.findNavController().navigate(action)
        }

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
                
                R.id.nav_logout -> { 
                    auth.currentUser?.let { _ ->
                        auth.signOut()
                    }
                    
                }
            }
            true
        }
    }
    
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return onNavigationItemSelected(item)
    }
}