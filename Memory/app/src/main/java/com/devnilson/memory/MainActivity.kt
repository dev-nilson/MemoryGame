package com.devnilson.memory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


const val GAME_STATE = "gameState"

class MainActivity : AppCompatActivity() {
    private lateinit var game: MemoryGame
    private lateinit var memoryGridLayout: GridLayout
    private var selectedColor = 0
    private var unselectedColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memoryGridLayout = findViewById(R.id.memory_grid)
        selectedColor = ContextCompat.getColor(this, R.color.blue)
        unselectedColor = ContextCompat.getColor(this, R.color.gray)

        game = MemoryGame()

        if (savedInstanceState == null) {
            startGame()
        } else {
            game.state = savedInstanceState.getString(GAME_STATE)!!
            setButtonColors()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_STATE, game.state)
    }

    private fun startGame() {
        game.newGame()
        setButtonColors()
    }

    private fun setButtonColors() {
        for (buttonIndex in 0 until memoryGridLayout.childCount) {
            val gridButton = memoryGridLayout.getChildAt(buttonIndex)

            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            if (game.isColorSelected(row, col)) {
                gridButton.setBackgroundColor(selectedColor)
            } else {
                gridButton.setBackgroundColor(unselectedColor)
            }
        }
    }

    fun onNewGameClick(view: View) {
        startGame()
    }

    fun onPlayClick(view: View) {
        val intent = Intent(this, PlayActivity::class.java)
        gameResultLauncher.launch(intent)
    }

    private val gameResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val gameResult = result.data!!.getStringExtra(GAME_RESULT)
            checkResults(gameResult)
        }
    }

    private fun checkResults(gameResult: String?) {
        if (gameResult == game.state) {
            Toast.makeText(this, "YOU WIN \uD83D\uDC4D", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "YOU LOSE \uD83D\uDC4E", Toast.LENGTH_SHORT).show()
        }


    }
}