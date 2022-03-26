package com.devnilson.memory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children

const val GAME_RESULT = "gameResult"

class PlayActivity : AppCompatActivity() {
    private lateinit var game: MemoryGame
    private lateinit var playGridLayout: GridLayout
    private var selectedColor = 0
    private var unselectedColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        playGridLayout = findViewById(R.id.play_grid)

        for (gridButton in playGridLayout.children) {
            gridButton.setOnClickListener(this::onButtonClick)
        }

        selectedColor = ContextCompat.getColor(this, R.color.blue)
        unselectedColor = ContextCompat.getColor(this, R.color.gray)

        game = MemoryGame()

        if (savedInstanceState == null) {
            startGame()
        } else {
            game.state = savedInstanceState.getString(GAME_RESULT)!!
            setButtonColors()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_RESULT, game.state)
    }

    private fun onButtonClick(view: View) {
        val buttonIndex = playGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        if (game.isColorSelected(row, col)) {
            game.unselectLight(row, col)
        }
        else {
            game.selectLight(row, col)
        }

        setButtonColors()
    }

    private fun startGame() {
        game.newGame()
        setPlayGrid()
    }

    private fun setPlayGrid() {
        for (buttonIndex in 0 until playGridLayout.childCount) {
            val gridButton = playGridLayout.getChildAt(buttonIndex)

            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            game.unselectLight(row, col)
            gridButton.setBackgroundColor(unselectedColor)
        }
    }

    private fun setButtonColors() {
        for (buttonIndex in 0 until playGridLayout.childCount) {
            val gridButton = playGridLayout.getChildAt(buttonIndex)

            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            if (game.isColorSelected(row, col)) {
                gridButton.setBackgroundColor(selectedColor)
            } else {
                gridButton.setBackgroundColor(unselectedColor)
            }
        }
    }

    fun onClearClick(view: View) {
        setPlayGrid()
    }

    fun onCheckClick(view: View) {
        val dataIntent = Intent()
        dataIntent.putExtra(GAME_RESULT, game.state)
        setResult(RESULT_OK, dataIntent)
        finish()
    }
}