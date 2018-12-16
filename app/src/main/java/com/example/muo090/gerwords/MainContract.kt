package com.example.muo090.gerwords

import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes

interface MainContractPresenter {

    fun onCreateView(view: MainView)

    fun onDestroyView()

    fun onGermanButtonClicked()

    fun onEnglishButtonClicked()

    fun onActionButtonClicked(action: Int)

    fun onSaveButtonClicked(question: String, solution: String, which : Int)

    fun onAddWordTextChanged(question: String, solution:String)

    fun onAddWordDialogShown()
}

interface MainView {

    fun showQuestion(question: String)

    fun showSolution(solution: String)

    fun updateLanguageIcons(@DrawableRes fromLanguageImage: Int, @DrawableRes toLanguageImage: Int)

    fun enableAddWordButtons(enabled : Boolean)

    fun setQuestionHint(@StringRes hint : Int)

    fun setSolutionHint(@StringRes hint : Int)

    fun dismissAddWordDialog()

    fun cleanAddWordEditTexts()
}