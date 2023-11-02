package com.dailydigest

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.dailydigest.utils.NOTE_DESC
import com.dailydigest.utils.NOTE_IS_PINNED
import com.dailydigest.utils.NOTE_REF
import com.dailydigest.utils.NOTE_TIMESTAMP
import com.dailydigest.utils.NOTE_TITLE
import com.dailydigest.databinding.ActivityCreateJournalBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.concurrent.thread

class CreateJournalActivity : AppCompatActivity() {

    private lateinit var createJournalBinding: ActivityCreateJournalBinding
    private lateinit var editText: EditText
    private var db = FirebaseFirestore.getInstance().collection(NOTE_REF).document()

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
                0
            )
            editText.text = spannableString
        }

        // Italic Formatting on Italic button click
        createJournalBinding.btnItalic.setOnClickListener {
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC),
                editText.selectionStart,
                editText.selectionEnd,
                0
            )
            editText.text = spannableString
        }

        // Underline Formatting on Underline button click
        createJournalBinding.btnUnderline.setOnClickListener {
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                UnderlineSpan(),
                editText.selectionStart,
                editText.selectionEnd,
                0
            )
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
            getFirebaseData()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        getFirebaseData()
    }

    private fun getFirebaseData() {
        Log.d("CreateJournalActivity", "button clicked")
        val data = HashMap<String, Any>()
        data[NOTE_TITLE] = createJournalBinding.edtTitle.text.toString()
        data[NOTE_DESC] = createJournalBinding.edtDesc.text.toString()
        data[NOTE_TIMESTAMP] = FieldValue.serverTimestamp()
        data[NOTE_IS_PINNED] = false
        thread {
            val batch = FirebaseFirestore.getInstance().batch()
            batch.set(db, data)
            batch.commit()
                .addOnSuccessListener {
                    Log.i("Success", " note added to note collection")
                }
                .addOnFailureListener { exception ->
                    Log.e(
                        "Exception",
                        "could not add note ${exception.localizedMessage}"
                    )
                }
        }
    }
}
