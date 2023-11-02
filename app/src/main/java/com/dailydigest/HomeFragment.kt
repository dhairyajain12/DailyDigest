package com.dailydigest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dailydigest.adapters.HomeNotesAdapter
import com.dailydigest.utils.DELETE_NOTE_TITLE
import com.dailydigest.utils.NOTE_DESC
import com.dailydigest.utils.NOTE_IS_PINNED
import com.dailydigest.utils.NOTE_REF
import com.dailydigest.utils.NOTE_TITLE
import com.dailydigest.utils.PIN_NOTE_TITLE
import com.dailydigest.utils.UPDATE_NOTE_DESC
import com.dailydigest.utils.UPDATE_NOTE_DOC_ID
import com.dailydigest.utils.UPDATE_NOTE_TITLE
import com.dailydigest.databinding.FragmentHomeBinding
import com.dailydigest.interfaces.NoteOptionClickListener
import com.dailydigest.models.NotesHomeModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.concurrent.thread

class HomeFragment : Fragment(), OnNavigationItemSelectedListener, NoteOptionClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var noteAdapter: HomeNotesAdapter
    private lateinit var noteListener: ListenerRegistration
    private var notesList = arrayListOf<NotesHomeModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        drawerLayout = binding.drawerLayout
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val navView = binding.navView
        navView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            context as Activity,
            drawerLayout,
            toolbar,
            R.string.app_open,
            R.string.app_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val headerView = navView.getHeaderView(0)
        val navUserName = headerView.findViewById<TextView>(R.id.nav_head_username)
        val navUserEmail = headerView.findViewById<TextView>(R.id.nav_header_user_email)
        navUserName.text = auth.currentUser!!.displayName
        navUserEmail.text = auth.currentUser!!.email
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFloating.setOnClickListener {
            val intent = Intent(activity, CreateJournalActivity::class.java)
            startActivity(intent)
        }
        
        binding.icSearch.setOnClickListener { 
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            view.findNavController().navigate(action)
        }
        
        binding.icNotification.setOnClickListener { 
            val action = HomeFragmentDirections.actionHomeFragmentToNotificationListFragment()
            view.findNavController().navigate(action)
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_profile -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                    binding.navView.findNavController().navigate(action)
                }
                R.id.nav_calender -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToCalendarFragment()
                    binding.navView.findNavController().navigate(action)
                }
                R.id.nav_settings -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                    binding.navView.findNavController().navigate(action)
                }
                R.id.nav_logout -> {
                    auth.currentUser.let { _ ->
                        auth.signOut()
                    }
                    requireActivity().installSplashScreen()
                    val intent = Intent(activity, LoginSignUpActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
            true
        }

        noteAdapter = HomeNotesAdapter(notesList, this) {}
        binding.homeNotesRecyclerView.adapter = noteAdapter
        binding.homeNotesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return onNavigationItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setNoteListener()
    }

    private fun setNoteListener() {
        noteListener = FirebaseFirestore.getInstance().collection(NOTE_REF)
            .whereEqualTo(NOTE_IS_PINNED, false)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e(tag, "Could not add note: ${exception.localizedMessage}")
                }

                if (snapshot != null) {
                    notesList.clear()
                    for (document in snapshot.documents) {
                        val data = document.data
                        val noteTitle = data?.get(NOTE_TITLE) as String
                        val noteDesc = data[NOTE_DESC] as String
                        val documentId = document.id

                        val newNote = NotesHomeModel(noteTitle, noteDesc, documentId)
                        notesList.add(newNote)
                    }
                    noteAdapter.notifyDataSetChanged()
                }
            }
    }

    private fun deleteCollection(
        collection: DocumentReference,
        note: NotesHomeModel,
        complete: (Boolean) -> Unit
    ) {
        collection.get()
            .addOnSuccessListener { _ ->
                thread {
                    val batch = FirebaseFirestore.getInstance().batch()
                    val noteRef = FirebaseFirestore.getInstance().collection(NOTE_REF)
                        .document(note.documentId)
                    batch.delete(noteRef)
                    batch.commit()
                        .addOnSuccessListener {
                            complete(true)
                        }
                        .addOnFailureListener { exception ->
                            Log.e(
                                "Exception",
                                "could not delete note ${exception.localizedMessage}"
                            )
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e(tag, "Could not get note: ${exception.localizedMessage}")
            }
    }

    override fun noteOptionMenuClicked(note: NotesHomeModel, view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.note_popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menu ->
            if (menu.title == PIN_NOTE_TITLE) {
                FirebaseFirestore.getInstance().collection(NOTE_REF).document(note.documentId)
                    .update(NOTE_IS_PINNED, true)
                    .addOnSuccessListener {
                        popupMenu.dismiss()
                    }
                    .addOnFailureListener { exception ->
                        Log.e(tag, "Could not update thought: ${exception.localizedMessage}")
                    }
                noteAdapter.notifyDataSetChanged()
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.detach(this).attach(this).commit()
            } else if (menu.title == DELETE_NOTE_TITLE) {
                val noteRef = FirebaseFirestore.getInstance().collection(NOTE_REF)
                    .document(note.documentId)

                deleteCollection(noteRef, note) { success ->
                    if (success) {
                        Log.i(tag, "Note deleted successfully")
                    } else {
                        Log.e(tag, "Could not delete note")
                    }
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun noteSelectionClicked(note: NotesHomeModel) {
        val intent = Intent(context, UpdateNoteActivity::class.java)
        intent.putExtra(UPDATE_NOTE_TITLE, note.noteTitle)
        intent.putExtra(UPDATE_NOTE_DESC, note.noteDesc)
        intent.putExtra(UPDATE_NOTE_DOC_ID, note.documentId)

        startActivity(intent)
    }
}
