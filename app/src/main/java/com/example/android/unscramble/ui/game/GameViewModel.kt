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
    private var score = 0
    private var currentWordCount = 0
    private var _currentScrambledWord = "test"

    val currentScrambledWord: String
        get() =_currentScrambledWord

    // a log statement with initializer block
    // initial setup code <-- initialization of an object
    // fist created the object and initialized
    init {
        Log.d("GameFragment", "GameViewModel created and working!")
    }

    // the [ViewModel] is destroyed when the associated fragment is detached
    // or the activity is finished. --> [onCleared()] is called
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

}