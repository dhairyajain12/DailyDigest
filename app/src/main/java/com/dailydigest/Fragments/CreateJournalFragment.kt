package com.dailydigest.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dailydigest.R
import com.dailydigest.databinding.FragmentCreateJournalBinding


class CreateJournalFragment : Fragment() {
    // var declaration
    private var _binding : FragmentCreateJournalBinding? = null
    private val createJournalBinding get() = _binding!!
    private lateinit var editText : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // binding initialization
        _binding = FragmentCreateJournalBinding.inflate(inflater, container, false)
        return createJournalBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        editText = createJournalBinding.edtDesc

        //Bold Text
        createJournalBinding.btnBold.setOnClickListener {
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                editText.selectionStart,
                editText.selectionEnd,
                0)

            editText.text = spannableString
        }

        //Italic Text
        createJournalBinding.btnItalic.setOnClickListener {
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC),
                editText.selectionStart,
                editText.selectionEnd,
                0)

            editText.text = spannableString
        }

        //Underline Text
        createJournalBinding.btnUnderline.setOnClickListener {
            val spannableString = SpannableStringBuilder(editText.text)
            spannableString.setSpan(
                UnderlineSpan(),
                editText.selectionStart,
                editText.selectionEnd,
                0)

            editText.text = spannableString
        }

        //text alignment start
        createJournalBinding.btnFormatLeft.setOnClickListener {
            editText.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            val spannableString = SpannableStringBuilder(editText.text)
            editText.text = spannableString
        }

        //text alignment center
        createJournalBinding.btnFormatCenter.setOnClickListener {
            editText.textAlignment = View.TEXT_ALIGNMENT_CENTER
            val spannableString = SpannableStringBuilder(editText.text)
            editText.text = spannableString
        }

        //text alignment right
        createJournalBinding.btnFormatRight.setOnClickListener {
            editText.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
            val spannableString = SpannableStringBuilder(editText.text)
            editText.text = spannableString
        }

        // set pop-up menu
        createJournalBinding.icMenu.setOnClickListener {
            val popupMenu = android.widget.PopupMenu(context, createJournalBinding.icMenu)
            popupMenu.menuInflater.inflate(R.menu.create_journal_menu, popupMenu.menu)
            popupMenu.show()
        }

        createJournalBinding.btnBack.setOnClickListener {
            val action = CreateJournalFragmentDirections.actionCreateJournalFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }

    }
}