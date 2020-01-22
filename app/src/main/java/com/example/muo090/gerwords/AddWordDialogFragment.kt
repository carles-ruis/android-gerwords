package com.example.muo090.gerwords

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class AddWordDialogFragment : DialogFragment() {

    private var listener: AddWordDialogListener? = null
    private lateinit var customView: View
    private lateinit var addQuestionInputLayout: TextInputLayout
    private lateinit var addQuestionEditText: EditText
    private lateinit var addSolutionInputLayout: TextInputLayout
    private lateinit var addSolutionEditText: EditText
    private lateinit var positiveButton: Button

    private val afterTextChanged: (Editable?) -> Unit = {
        positiveButton.isEnabled = !addQuestionEditText.empty() && !addSolutionEditText.empty()
    }

    private val onSaveButtonClick: (View) -> Unit = {
        listener?.onSaveButtonClick(addQuestionEditText.content(), addSolutionEditText.content())
        dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? AddWordDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        customView = inflate(R.layout.dialog_add_word)

        val dialog = MaterialAlertDialogBuilder(context)
                .setTitle(R.string.add_dialog_title)
                .setView(customView)
                .setCancelable(false)
                .setNegativeButton(R.string.add_dialog_cancel, null)
                .setPositiveButton(R.string.add_dialog_ok, null)
                .create()
        dialog.setOnShowListener { initViews() }

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = customView

    private fun initViews() {
        addQuestionInputLayout = customView.findViewById(R.id.addword_question_inputlayout)
        addQuestionInputLayout.setHint(getString(R.string.add_dialog_question_hint, getString(arguments!!.getInt(EXTRA_QUESTION_HINT))))
        addQuestionEditText = customView.findViewById(R.id.addword_question_edittext)
        addQuestionEditText.doAfterTextChanged(afterTextChanged)

        addSolutionInputLayout = customView.findViewById(R.id.addword_solution_inputlayout)
        addSolutionInputLayout.setHint(getString(R.string.add_dialog_solution_hint, getString(R.string.add_dialog_english)))
        addSolutionEditText = customView.findViewById(R.id.addword_solution_edittext)
        addSolutionEditText.doAfterTextChanged(afterTextChanged)

        positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).apply {
            setOnClickListener(onSaveButtonClick)
            isEnabled = false
        }
    }

    companion object {
        const val TAG = "AddWordDialogFragment"
        const val EXTRA_QUESTION_HINT = "extra_question_hint"
        fun newInstance(@StringRes
                        questionHint: Int) =
                AddWordDialogFragment().apply {
                    arguments = Bundle().apply { putInt(EXTRA_QUESTION_HINT, questionHint) }
                }
    }

    interface AddWordDialogListener {
        fun onSaveButtonClick(question: String, solution: String)
    }
}