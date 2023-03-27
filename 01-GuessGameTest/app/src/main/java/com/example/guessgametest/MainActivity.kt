package com.example.guessgametest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessgametest.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //Game status pointer class
    private enum class GameStatus {
        START,
        RUN,
        STOP
    }
    private var gameStatus: GameStatus = GameStatus.START
    //Random number
    private val maxRndNumber = 40
    private var rndNumber = 0
    //Guess counter
    private val maxGuessCount = 20
    private var actualGuessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initItems()
    }

    private fun initItems() {
        //Generate random number, set actual guess counter
        fun generateRandomNumber() {
            rndNumber = (0..maxRndNumber).random() + 1
            Log.d("RANDOM", "Random number generate: $rndNumber")
            actualGuessCount = 0
        }

        fun increaseGuessCount() {
            actualGuessCount++
            binding.tvGuessCount.text = "${getString(R.string.tvGuessCount)} $actualGuessCount"
        }

        //Re/start game, set default value
        fun reStartGame() {
            gameStatus = GameStatus.RUN

            generateRandomNumber()
            binding.tvGuessCount.text = "${getString(R.string.tvGuessCount)} $actualGuessCount"

            binding.tvAnsverLayout.setBackgroundColor(Color.RED)
            binding.etGuess.setText("")
            binding.btGuess.text = getString(R.string.btGuess)
            binding.tvGuessCount.text = getString(R.string.tvGuessCount)
            binding.tvAnsverLine1.text = getString(R.string.tvAnsverLine1Question)
            binding.tvAnsverLine2.text = getString(R.string.tvAnsverLine2Start)
        }

        //First init
        //if (gameStatus == GameStatus.START)
        reStartGame()

        //Click on button
        binding.btGuess.setOnClickListener {
            when (gameStatus) {
                GameStatus.STOP -> reStartGame()

                GameStatus.RUN -> {
                    //If the editText is not empty
                    if (binding.etGuess.text.isNotEmpty()) {
                        //Restart game?
                        fun newGame() {
                            binding.btGuess.text = getString(R.string.btGuessNewGame)

                            gameStatus = GameStatus.STOP

                        }

                        increaseGuessCount()

                        var nIntFromET: Int
                        try {
                            nIntFromET = Integer.parseInt(binding.etGuess.text.toString())
                            Log.d("nIntFromET", "Guess number: $nIntFromET")
                        } catch (e: NumberFormatException) {
                            // handle the exception
                            nIntFromET = 0
                        }

                        //Find it!
                        if (nIntFromET == rndNumber) {
                            binding.tvAnsverLine1.text = nIntFromET.toString()
                            binding.tvAnsverLine2.text = getString(R.string.tvAnsverLine2FindIt)
                            binding.tvAnsverLayout.setBackgroundColor(Color.GREEN)

                            newGame()

                            //Smaller guess
                        } else if (nIntFromET > rndNumber) {
                            binding.tvAnsverLine1.text =
                                "${nIntFromET.toString()} ${getString(R.string.tvAnsverLine1Smaller)} X"
                            binding.tvAnsverLine2.text = getString(R.string.tvAnsverLine2Smaller)

                            //Bigger guess
                        } else if (nIntFromET < rndNumber) {
                            binding.tvAnsverLine1.text =
                                "${nIntFromET.toString()} ${getString(R.string.tvAnsverLine1Bigger)} X"
                            binding.tvAnsverLine2.text = getString(R.string.tvAnsverLine2Bigger)

                            //Too many guess
                        } else if (actualGuessCount >= maxGuessCount) {
                            binding.tvAnsverLine1.text = getString(R.string.tvAnsverLine1Question)
                            binding.tvAnsverLine2.text = getString(R.string.tvAnsverLine2NotFindIt)

                            newGame()

                        }

                        //Erase editText
                        binding.etGuess.text.clear()

                    } else {    //If editText is empty
                        binding.etGuess.setError("This cannot be empty!")
                    }
                }

                GameStatus.START -> reStartGame()
            }
        }
    }
}

