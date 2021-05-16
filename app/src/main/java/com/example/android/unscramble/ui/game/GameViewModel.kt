package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


class GameViewModel:ViewModel() {

    // Declare private mutable variable
    // only be modified within the class it is declared
    private val _score = MutableLiveData(0)

    val score: LiveData<Int>
        get() = _score //backing property

    private val _currentWordCount = MutableLiveData(0)

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
        // No initial value provided

    // convert to Spannable for Google Talk-back feature
    val currentScrambledWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord){
        if(it == null){
            SpannableString("")
        }else{
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0,
                scrambledWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }
       // get() =_currentScrambledWord
// The list and strings are needed to declare first
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord:String
    // a log statement with initializer block
    // initial setup code <-- initialization of an object
    // fist created the object and initialized
    init {
        Log.d("GameFragment", "GameViewModel created and working!")
        getNextWord()
    }

    // the [ViewModel] is destroyed when the associated fragment is detached
    // or the activity is finished. --> [onCleared()] is called
    /*
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
    */



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
            // access the data form "LiveData"
            Log.d("Unscramble", "currentWord= $currentWord")
            _currentScrambledWord.value = String(tempWord)
            // update the currentWordCount + 1
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            // add the new word to the "wordsList"
            wordsList.add(currentWord)
        }
    }

    /**
     *  Returns true if the current word count is less than "MAX_OF_WORDS".
     *  Updates the next word
     */
    fun nextWord():Boolean{
        return if(_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }

    // A helper method to validate player word
    private fun increaseScore(){
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    // check the word is correct
    fun isUSerWordCorrect(playerWord: String):Boolean{
        if(playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }

    // Re-initializes the game data to restart the game
    fun reinitializeData(){
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
}