package com.example.muo090.gerwords

import android.content.Context
import android.util.Log
import java.util.*

class MainPresenter(private val view: MainView) : MainContractPresenter, FileHandler by FileDelegate() {

    private val internalDirectory by lazy { (view as Context).filesDir.path + "/" }
    private val germanDictionaryFile by lazy { internalDirectory + "germanwords.txt" }
    private val englishDictionaryFile by lazy { internalDirectory + "englishwords.txt" }
    private val germanDictionaryAsset = "assets/germanwords.txt"
    private val englishDictionaryAsset = "assets/englishwords.txt"
    private lateinit var filename: String

    private val words = mutableListOf<Pair<String, String>>()
    private var currentWords: Pair<String, String>? = null

    override fun onCreateView() {
        loadData(germanDictionaryFile)
    }

    private fun loadData(filename: String) {
        this.filename = filename
        val dictionary = try {
            loadFile(filename)
        } catch (e: FileDoesNotExistException) {
            createFile(
                context = view as Context,
                file = filename,
                asset = if (filename == germanDictionaryFile) germanDictionaryAsset else englishDictionaryAsset
            )
            loadFile(filename)
        }
        updateWords(dictionary)
        view.updateLanguageIcons(
            fromLanguageImage = if (filename == germanDictionaryFile) R.drawable.ic_germany else R.drawable.ic_catalonia,
            toLanguageImage = R.drawable.ic_england
        )
        setNewWord()
    }

    private fun updateWords(dictionary: List<String>) {
        words.clear()
        dictionary.forEach { line ->
            val splitLine = line.split(" ")
            if (splitLine.size != 2 || splitLine[0].isEmpty() || splitLine[1].isEmpty()) {
                Log.w(TAG, "$line doesn't have two tokens")
                return
            }

            var key = parseToken(splitLine[0])
            val solution = parseToken(splitLine[1])
            val solutionCount = solution.split(",").size
            if (solutionCount > 1) key += " ($solutionCount)"
            words.add(Pair(key, solution))
        }
    }

    private fun parseToken(token: String) = token.split(".").joinToString(" ")

    private fun setNewWord() {
        currentWords = words[Random().nextInt(words.size)].apply { view.showQuestion(first) }
    }

    override fun onSolutionButtonClick() {
        currentWords?.let { view.showSolution(it.second) }
    }

    override fun onNextButtonClick() {
        setNewWord()
    }

    override fun onGermanButtonClicked() {
        loadData(germanDictionaryFile)
    }

    override fun onEnglishButtonClicked() {
        loadData(englishDictionaryFile)
    }

    override fun onAddButtonClicked() {
        val questionHintRes =
            if (filename == germanDictionaryFile) R.string.add_dialog_german else R.string.add_dialog_catalan
        view.showAddWordDialog(questionHintRes)
    }

    override fun onSaveButtonClick(question: String, solution: String) {
        appendToFile(file = filename, line = "$question $solution")
        words.add(Pair(question, solution))
    }

    companion object {
        private const val TAG = "MainPresenter"
    }
}
