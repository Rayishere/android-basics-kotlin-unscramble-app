package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.ViewModel


class GameViewModel:ViewModel() {

    // Declare private mutable variable
    // only be modified within the class it is declared
    private var _score = 0
    private var currentWordCount = 0
    private lateinit var _currentScrambledWord: String // No initial value provided

    val currentScrambledWord: String
        get() =_currentScrambledWord

    val score: Int
        get() = _score //backing property

    // a log statement with initializer block
    // initial setup code <-- initialization of an object
    // fist created the object and initialized
    init {
        Log.d("GameFragment", "GameViewModel created and working!")
        getNextWord()
    }

    // the [ViewModel] is destroyed when the associated fragment is detached
    // or the activity is finished. --> [onCleared()] is called
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord:String

    /**
     * Updates currentWord & currentScrambledWord with the next word
     * No return value
     */
    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray() // fixed size
        tempWord.shuffle()

        // filter out the shuffled tempWord == currentWord
        while (tempWord.toString().equals(currentWord, false)){
            tempWord.shuffle()
        }

        //check the currentWord is used already
        if (wordsList.contains(currentWord)){
            // yes it is used, get to next one
            getNextWord()
        }else{
            // convert from charArray to String
            _currentScrambledWord = String(tempWord)
            // update the currentWordCount + 1
            ++currentWordCount
            // add the new word to the "wordsList"
            wordsList.add(currentWord)
        }
    }

    /**
     *  Returns true if the current word count is less than "MAX_OF_WORDS".
     *  Updates the next word
     */
    fun nextWord():Boolean{
        return if(currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }
}