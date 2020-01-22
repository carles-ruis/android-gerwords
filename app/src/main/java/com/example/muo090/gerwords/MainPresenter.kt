package com.example.muo090.gerwords

import android.content.Context
import android.util.Log
import java.io.File
import java.util.*

class MainPresenter(val view: MainView) : MainContractPresenter {

    companion object {
        private const val TAG = "MainPresenter"
        private const val ENGLISH_DICTIONARY_ASSET = "assets/englishwords.txt"
        private const val GERMAN_DICTIONARY_ASSET: String = "assets/germanwords.txt"
    }

    private val INTERNAL_DIRECTORY : String
    private val GERMAN_DICTIONARY_FILENAME : String
    private val ENGLISH_DICTIONARY_FILENAME : String
    init {
        INTERNAL_DIRECTORY = (view as Context).filesDir.path + "/"
        GERMAN_DICTIONARY_FILENAME = INTERNAL_DIRECTORY + "germanwords.txt"
        ENGLISH_DICTIONARY_FILENAME = INTERNAL_DIRECTORY + "englishwords.txt"
    }

    var words = mutableListOf<Pair<String, String>>()
    private var currentWords: Pair<String, String>? = null
    private lateinit var currentFilename: String

    override fun onCreateView() {
        currentFilename = GERMAN_DICTIONARY_FILENAME
        loadData(currentFilename)
    }

    private fun loadData(filename: String) {
        words.clear()

        if (!filename.existsFile()) {
            val asset = if (GERMAN_DICTIONARY_FILENAME.equals(filename)) GERMAN_DICTIONARY_ASSET else ENGLISH_DICTIONARY_ASSET
            filename.createFileFromAsset(view as Context, asset)
        }
        filename.readLines { line -> addWords(line) }

        this.currentFilename = filename
        view.updateLanguageIcons(
                if (GERMAN_DICTIONARY_FILENAME.equals(filename)) R.drawable.ic_germany else R.drawable.ic_catalonia,
                R.drawable.ic_england
        )
        setNewWord()
    }

    private fun addWords(line: String) {
        val splited = line.split(" ")
        if (splited.size != 2 || splited[0].isEmpty() || splited[1].isEmpty()) {
            Log.w(TAG, line + " doesn't have two tokens")
            return
        }

        var key = parseToken(splited[0])
        val solution = parseToken(splited[1])
        val solutionCount = getSolutionCount(solution)
        if (solutionCount > 1) key += " (" + solutionCount + ")"
        words.add(Pair(key, solution))
    }

    private fun parseToken(token: String) = token.split(".").joinToString(" ")

    private fun getSolutionCount(solution: String) = solution.split(",").size

    private fun setNewWord() {
        currentWords = words[Random().nextInt(words.size)].apply { view.showQuestion(first) }
    }

    override fun onActionButtonClicked(action: Int) {
        if (action == R.string.main_solution) {
            currentWords?.apply { view.showSolution(second) }
        } else {
            setNewWord()
        }
    }

    override fun onGermanButtonClicked() {
        loadData(GERMAN_DICTIONARY_FILENAME)
    }

    override fun onEnglishButtonClicked() {
        loadData(ENGLISH_DICTIONARY_FILENAME)
    }

    override fun onAddButtonClicked() {
        val questionHintRes =
                if (GERMAN_DICTIONARY_FILENAME.equals(currentFilename)) R.string.add_dialog_german else R.string.add_dialog_catalan

        AddWordDialogFragment.newInstance(questionHintRes).apply {
            isCancelable = false
            this@MainPresenter.view.showDialog(this)
        }
    }

    override fun onSaveButtonClicked(question: String, solution: String) {
        currentFilename.append(Pair(question, solution))
        loadData(currentFilename)
    }
}