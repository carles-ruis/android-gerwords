package com.example.muo090.gerwords

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), MainView, AddWordDialogFragment.AddWordDialogListener {

    private lateinit var presenter: MainContractPresenter
    private lateinit var toolbar: Toolbar
    private lateinit var translateFromImage: ImageView
    private lateinit var translateToImage: ImageView
    private lateinit var questionTextView: TextView
    private lateinit var solutionTextView: TextView
    private lateinit var addButton: FloatingActionButton
    private lateinit var actionButton: Button
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        presenter = MainPresenter(this)
        presenter.onCreateView()
    }

    private fun findViews() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        translateFromImage = findViewById(R.id.main_translate_from_imageview)
        translateToImage = findViewById(R.id.main_translate_to_imageview)
        questionTextView = findViewById(R.id.main_question_textview)
        solutionTextView = findViewById(R.id.main_solution_textview)
        addButton = findViewById(R.id.main_add_button)
        addButton.setOnClickListener { presenter.onAddButtonClicked() }
        actionButton = findViewById(R.id.main_action_button)
        actionButton.setOnClickListener { presenter.onActionButtonClicked(actionButton.getTag() as Int) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_main_english -> presenter.onEnglishButtonClicked()
            R.id.menu_main_german -> presenter.onGermanButtonClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showQuestion(question: String) {
        questionTextView.setText(question)
        solutionTextView.setText("")
        actionButton.setText(R.string.main_solution)
        actionButton.setTag(R.string.main_solution)
    }

    override fun showSolution(solution: String) {
        solutionTextView.setText(solution)
        actionButton.setText(R.string.main_next)
        actionButton.setTag(R.string.main_next)
    }

    override fun updateLanguageIcons(fromLanguageImage: Int, toLanguageImage: Int) {
        translateFromImage.setImageResource(fromLanguageImage)
        translateToImage.setImageResource(toLanguageImage)
    }

    override fun onSaveButtonClick(question: String, solution: String) {
        presenter.onSaveButtonClicked(question, solution)
    }

    override fun showDialog(fragment: AddWordDialogFragment) {
        addButton.setEnabled(false)
        fragment.show(supportFragmentManager, AddWordDialogFragment.TAG)
    }
}

/*    inner class AddWordDialog(context: Context) : AlertDialog(context) {

        override fun onCreate(savedInstanceState: Bundle?) {

            val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_word, null)
            setView(view)
            setTitle(R.string.add_dialog_title)
            setCancelable(false)
            setButton(BUTTON_POSITIVE, getString(R.string.add_dialog_ok)) { _, _ -> }
            setButton(BUTTON_NEGATIVE, getString(R.string.add_dialog_cancel)) { _, _ -> }
            setButton(BUTTON_NEUTRAL, getString(R.string.add_dialog_next)) { _, _ -> }

            addQuestionInputLayout = view.findViewById(R.id.addword_question_inputlayout)
            addQuestionInputLayout.setHint(getString(R.string.add_dialog_question_hint, getString(R.string.add_dialog_german)))
            addQuestionEditText = view.findViewById(R.id.addword_question_edittext)
            addQuestionEditText.doAfterTextChanged { afterTextChanged }

            addSolutionInputLayout = view.findViewById(R.id.addword_solution_inputlayout)
            addSolutionInputLayout.setHint(getString(R.string.add_dialog_solution_hint, getString(R.string.add_dialog_english)))
            addSolutionEditText = view.findViewById(R.id.addword_solution_edittext)
            addSolutionEditText.doAfterTextChanged { afterTextChanged }

            setOnDismissListener {
                addButton.setEnabled(true)
                addWordDialog = null
            }
            setOnShowListener {
                saveButton = getButton(BUTTON_POSITIVE)
                saveButton.setOnClickListener { onSaveButtonClick(BUTTON_POSITIVE) }
                saveAndNextButton = getButton(BUTTON_NEUTRAL)
                saveAndNextButton.setOnClickListener { onSaveButtonClick(BUTTON_NEUTRAL) }
                addQuestionEditText.requestFocus()
                presenter.onAddWordDialogShown()
            }

            super.onCreate(savedInstanceState)
        }
    } */