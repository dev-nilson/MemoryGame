package com.devnilson.memory
import kotlin.random.Random

const val GRID_SIZE = 5

class MemoryGame {
    private val lightsGrid = Array(GRID_SIZE) { Array(GRID_SIZE) { true } }

    fun newGame() {
        for (row in 0 until GRID_SIZE) {
            for (col in 0 until GRID_SIZE) {
                lightsGrid[row][col] = Random.nextBoolean()
            }
        }
    }

    fun isLightSelected(row: Int, col: Int): Boolean {
        return lightsGrid[row][col]
    }
}
