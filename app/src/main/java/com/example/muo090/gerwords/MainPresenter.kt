package com.example.muo090.gerwords

import android.content.DialogInterface.BUTTON_POSITIVE
import android.util.Log
import java.util.*

class MainPresenter(val fileUtils: FileUtils) : BasePresenter(), MainContractPresenter {

    val TAG = this.javaClass.simpleName
    var view: MainView? = null
    var words = mutableListOf<Pair<String, String>>()
    lateinit var currentWords: Pair<String, String>
    lateinit var currentFilename: String

    override fun onCreateView(view: MainView) {
        this.view = view
        loadData(GERMAN_DICTIONARY_FILENAME)
    }

    override fun onDestroyView() {
        view = null
    }

    private fun loadData(filename: String) {
        words.clear()

        if (!fileUtils.exists(filename)) fileUtils.createFile(filename)
        fileUtils.read(filename).forEach { addWords(it) }

        this.currentFilename = filename
        view?.updateLanguageIcons(
                if (GERMAN_DICTIONARY_FILENAME.equals(filename)) R.drawable.ic_germany else R.drawable.ic_catalonia,
                R.drawable.ic_england
        )
        setNewWord()
    }

    private fun addWords(line: String) {
        val splited = line.split(" ")
        if (splited.size != 2 || empty(splited[0]) || empty(splited[1])) {
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
        currentWords = words[Random().nextInt(words.size)]
        view?.showQuestion(currentWords.first)
    }

    override fun onActionButtonClicked(action: Int) {
        if (action == R.string.main_solution) {
            view?.showSolution(currentWords.second)
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

    override fun onSaveButtonClicked(question: String, solution: String, which : Int) {
        fileUtils.append(currentFilename, question, solution)
        loadData(currentFilename)
        if (which == BUTTON_POSITIVE) {
            view?.dismissAddWordDialog()
        } else {
            view?.cleanAddWordEditTexts()
        }
    }

    override fun onAddWordTextChanged(question: String, solution: String) {
        view?.enableAddWordButtons(question.isNotEmpty() && solution.isNotEmpty())
    }

    override fun onAddWordDialogShown() {
        view?.enableAddWordButtons(false)
        view?.setQuestionHint(if (GERMAN_DICTIONARY_FILENAME.equals(currentFilename)) R.string.add_dialog_german else R.string.add_dialog_catalan)
        view?.setSolutionHint(R.string.add_dialog_english)
    }
}