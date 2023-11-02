package com.dailydigest

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dailydigest.utils.NOTE_DESC
import com.dailydigest.utils.NOTE_REF
import com.dailydigest.utils.NOTE_TITLE
import com.dailydigest.utils.PINNED_NOTE_REF
import com.dailydigest.databinding.ActivityUpdateNoteBinding
import com.google.firebase.firestore.FirebaseFirestore

class UpdateNoteActivity: AppCompatActivity() {
    private lateinit var binding : ActivityUpdateNoteBinding

    private lateinit var noteTitle: String
    private lateinit var noteDesc: String
    private lateinit var noteId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        noteTitle = intent.getStringExtra("NOTE_TITLE").toString()
        noteDesc = intent.getStringExtra("NOTE_DESC").toString()
        noteId = intent.getStringExtra("NOTE_DOC_ID").toString()
        
        binding.updateEdtTitle.setText(noteTitle)
        binding.updateEdtDesc.setText(noteDesc)
    }

    override fun onStart() {
        super.onStart()
        
        binding.btnBack.setOnClickListener { 
            finish()
            hideKeyboard()
        }
        
        binding.btnBold.setOnClickListener { 
            val spannableString = SpannableStringBuilder(binding.updateEdtDesc.text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                binding.updateEdtDesc.selectionStart,
                binding.updateEdtDesc.selectionEnd,
                0
            )
            binding.updateEdtDesc.text = spannableString
        }
        
        binding.btnItalic.setOnClickListener { 
            val spannableString = SpannableStringBuilder(binding.updateEdtDesc.text)
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC),
                binding.updateEdtDesc.selectionStart,
                binding.updateEdtDesc.selectionEnd,
                0
            )
            binding.updateEdtDesc.text = spannableString
        }
        
        binding.btnUnderline.setOnClickListener { 
            val spannableString = SpannableStringBuilder(binding.updateEdtDesc.text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                binding.updateEdtDesc.selectionStart,
                binding.updateEdtDesc.selectionEnd,
                0
            )
            binding.updateEdtDesc.text = spannableString
        }
        
        binding.btnFormatLeft.setOnClickListener { 
            binding.updateEdtDesc.textAlignment = EditText.TEXT_ALIGNMENT_VIEW_START
            val spannableString = SpannableStringBuilder(binding.updateEdtDesc.text)
            binding.updateEdtDesc.text = spannableString
        }
        
        binding.btnFormatCenter.setOnClickListener { 
            binding.updateEdtDesc.textAlignment = EditText.TEXT_ALIGNMENT_CENTER
            val spannableString = SpannableStringBuilder(binding.updateEdtDesc.text)
            binding.updateEdtDesc.text = spannableString
        }
        
        binding.btnFormatRight.setOnClickListener { 
            binding.updateEdtDesc.textAlignment = EditText.TEXT_ALIGNMENT_VIEW_END
            val spannableString = SpannableStringBuilder(binding.updateEdtDesc.text)
            binding.updateEdtDesc.text = spannableString
        }
    }

    override fun onStop() {
        super.onStop()
        FirebaseFirestore.getInstance().collection(NOTE_REF).document(noteId)
            .update(
                NOTE_TITLE, binding.updateEdtTitle.text.toString(),
                NOTE_DESC, binding.updateEdtDesc.text.toString())
            .addOnSuccessListener {
                finish()
                hideKeyboard()
            }
            .addOnFailureListener { exception ->
                Log.e("Exception", "could not update note ${exception.localizedMessage}")
            }

        FirebaseFirestore.getInstance().collection(PINNED_NOTE_REF).document(noteId)
            .update(
                NOTE_TITLE, binding.updateEdtTitle.text.toString(),
                NOTE_DESC, binding.updateEdtDesc.text.toString())
            .addOnSuccessListener {
                finish()
                hideKeyboard()
            }
            .addOnFailureListener { exception ->
                Log.e("Exception", "could not update note ${exception.localizedMessage}")
            }
    }

    private fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
