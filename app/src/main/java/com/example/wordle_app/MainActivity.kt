package com.example.wordle_app

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var guessNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val targetWordTextView = findViewById<TextView>(R.id.textView)
        val guess1 = findViewById<TextView>(R.id.guess1)
        val check1 = findViewById<TextView>(R.id.check1)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val check2 = findViewById<TextView>(R.id.check2)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val check3 = findViewById<TextView>(R.id.check3)
        val editText = findViewById<EditText>(R.id.editText)

        val context: InputMethodManager  = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        // Set the target word
        targetWordTextView.text = wordToGuess.lowercase()

        // Add onClick listener to your guess button
        button.setOnClickListener {
            guessNum++
            val yourGuess = editText.text.toString()
            if(guessNum == 1)
            {
                guess1.text = yourGuess
                check1.text = checkGuess(yourGuess.uppercase())
            }
            else if(guessNum == 2)
            {
                guess2.text = yourGuess
                check2.text = checkGuess(yourGuess.uppercase())
            }
            else
            {
                guess3.text = yourGuess
                check3.text = checkGuess(yourGuess.uppercase())
            }

            // check here if the guess was correct or guessNum == 3 and if, so show the target word and disable the guess button
            if(guessNum == 3 || checkGuess(yourGuess.uppercase()) == "OOOO")
            {
                // Show the word
                targetWordTextView.visibility = View.VISIBLE
                // Disable the Button
                button.isEnabled = false
            }
            // Clear the editText field, by setting it to empty string
            editText.setText("")
            // Hide the keyboard - this.currentFocus!! is used to forcefully assert that the current focus is not null and avoid a NullPointer to be thrown
            context.hideSoftInputFromWindow(
                    this.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )

        }
    }
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}