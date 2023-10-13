package com.example.muo090.gerwords

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface MainContractPresenter {

    fun onCreateView()

    fun onGermanButtonClicked()

    fun onEnglishButtonClicked()

    fun onSolutionButtonClick()

    fun onNextButtonClick()

    fun onAddButtonClicked()

    fun onSaveButtonClick(question: String, solution: String)
}

interface MainView {

    fun showQuestion(question: String)

    fun showSolution(solution: String)

    fun updateLanguageIcons(@DrawableRes fromLanguageImage: Int, @DrawableRes toLanguageImage: Int)

    fun showAddWordDialog(@StringRes questionHintRes: Int)

}