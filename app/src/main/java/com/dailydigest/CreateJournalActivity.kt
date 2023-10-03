package com.dailydigest

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.dailydigest.databinding.ActivityCreateJournalBinding

class CreateJournalActivity : AppCompatActivity() {
    
    private lateinit var createJournalBinding: ActivityCreateJournalBinding
    private lateinit var editText: EditText
    
    private val TAG = "CreateJournalActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createJournalBinding = ActivityCreateJournalBinding.inflate(layoutInflater)
        setContentView(createJournalBinding.root)
    }

    override fun onStart() {
        super.onStart()
        editText = createJournalBinding.edtDesc
        
        // Bold Formatting on Bold button click
        createJournalBinding.btnBold.setOnClickListener { 
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                editText.selectionStart,
                editText.selectionEnd,
                0)
            editText.text = spannableString
        }
        
        // Italic Formatting on Italic button click
        createJournalBinding.btnItalic.setOnClickListener { 
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC),
                editText.selectionStart,
                editText.selectionEnd,
                0)
            editText.text = spannableString
        }
        
        // Underline Formatting on Underline button click
        createJournalBinding.btnUnderline.setOnClickListener { 
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                UnderlineSpan(),
                editText.selectionStart,
                editText.selectionEnd,
                0)
            editText.text = spannableString
        }
        
        // Left Text Alignment Button Click Listener
        createJournalBinding.btnFormatLeft.setOnClickListener { 
            editText.textAlignment = EditText.TEXT_ALIGNMENT_VIEW_START
            val spannableString = SpannableStringBuilder(editText.text)
            editText.text = spannableString
        }
        
        // Center Text Alignment Button Click Listener
        createJournalBinding.btnFormatCenter.setOnClickListener { 
            editText.textAlignment = EditText.TEXT_ALIGNMENT_CENTER
            val spannableString = SpannableStringBuilder(editText.text)
            editText.text = spannableString
        }
        
        // Right Text Alignment Button Click Listener
        createJournalBinding.btnFormatRight.setOnClickListener { 
            editText.textAlignment = EditText.TEXT_ALIGNMENT_VIEW_END
            val spannableString = SpannableStringBuilder(editText.text)
            editText.text = spannableString
        }
        
        // Journal Menu Icon Click Listener
        createJournalBinding.icMenu.setOnClickListener { 
            val popupMenu = PopupMenu(this, createJournalBinding.icMenu)
            popupMenu.menuInflater.inflate(R.menu.create_journal_menu, popupMenu.menu)
            popupMenu.show()
        }
        
        // Back Button Click Listener
        createJournalBinding.btnBack.setOnClickListener { 
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}