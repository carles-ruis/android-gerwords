package com.example.muo090.gerwords

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), MainView, AddWordDialogFragment.AddWordDialogListener {

    private lateinit var presenter: MainContractPresenter
    private lateinit var toolbar: MaterialToolbar
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

    override fun onDialogDismiss() {
        addButton.isEnabled = true
    }

    override fun showAddWordDialog(@StringRes questionHintRes: Int) {
        addButton.setEnabled(false)
        AddWordDialogFragment.newInstance(questionHintRes).show(supportFragmentManager, AddWordDialogFragment.TAG)
    }
}