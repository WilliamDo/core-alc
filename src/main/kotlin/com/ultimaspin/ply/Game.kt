package com.ultimaspin.ply

import java.lang.IllegalStateException

class Game(val player1: Player,
           val pointsForPlayer1: Int,
           val player2: Player,
           val pointsForPlayer2: Int,
           private val pointsToWin: Int = DEFAULT_POINTS_TO_WIN) {

    init {
        if (!(isPlayer1TheWinner() xor isPlayer2TheWinner())) {
            throw IllegalStateException("Invalid game state")
        }
    }


    fun getWinner(): Player {

        if (isPlayer1TheWinner()) {
            return player1
        } else if (isPlayer2TheWinner()) {
            return player2
        }

        throw IllegalStateException("Cannot determine winner of this game")

    }

    private fun isPlayer1TheWinner(): Boolean {
        val player1WinsCleanGame = pointsForPlayer1 == pointsToWin && pointsForPlayer2 <= pointsToWin - 2
        val player1WinsAfterDeuce = pointsForPlayer1 > pointsToWin && pointsForPlayer1 - pointsForPlayer2 >= 2
        return player1WinsCleanGame || player1WinsAfterDeuce
    }

    private fun isPlayer2TheWinner(): Boolean {
        val player2WinsCleanGame = pointsForPlayer2 == pointsToWin && pointsForPlayer1 <= pointsToWin - 2
        val player2WinsAfterDeuce = pointsForPlayer2 > pointsToWin && pointsForPlayer2 - pointsForPlayer1 >= 2
        return player2WinsCleanGame || player2WinsAfterDeuce
    }


    fun getLoser(): Player {
        if (isPlayer1TheWinner()) {
            return player2
        } else if (isPlayer2TheWinner()) {
            return player1
        }

        throw IllegalStateException("Cannot determine lost of this game")
    }

    companion object {
        const val DEFAULT_POINTS_TO_WIN: Int = 11
    }

}