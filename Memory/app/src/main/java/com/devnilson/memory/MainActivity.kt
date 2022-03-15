package com.devnilson.memory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var game: MemoryGame
    private lateinit var memoryGridLayout: GridLayout
    private var selectedLightColor = 0
    private var unselectedLightColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memoryGridLayout = findViewById(R.id.memory_grid)
        selectedLightColor = ContextCompat.getColor(this, R.color.blue)
        unselectedLightColor = ContextCompat.getColor(this, R.color.gray)

        game = MemoryGame()
        startGame()
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

            if (game.isLightSelected(row, col)) {
                gridButton.setBackgroundColor(selectedLightColor)
            } else {
                gridButton.setBackgroundColor(unselectedLightColor)
            }
        }
    }

    fun onNewGameClick(view: View) {
        startGame()
    }
}