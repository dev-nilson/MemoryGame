package com.devnilson.memory
import kotlin.random.Random

const val GRID_SIZE = 5

class MemoryGame {
    private val memoryGrid = Array(GRID_SIZE) { Array(GRID_SIZE) { true } }

    var state: String
        get() {
            val boardString = StringBuilder()
            for (row in 0 until GRID_SIZE) {
                for (col in 0 until GRID_SIZE) {
                    val value = if (memoryGrid[row][col]) 'T' else 'F'
                    boardString.append(value)
                }
            }
            return boardString.toString()
        }
        set(value) {
            var index = 0
            for (row in 0 until GRID_SIZE) {
                for (col in 0 until GRID_SIZE) {
                    memoryGrid[row][col] = value[index] == 'T'
                    index++
                }
            }
        }

    fun newGame() {
        for (row in 0 until GRID_SIZE) {
            for (col in 0 until GRID_SIZE) {
                memoryGrid[row][col] = Random.nextBoolean()
            }
        }
    }

    fun isColorSelected(row: Int, col: Int): Boolean {
        return memoryGrid[row][col]
    }
}
