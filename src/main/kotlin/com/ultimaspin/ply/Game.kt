package com.ultimaspin.ply

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class Game(private val player1: Player,
           private val pointsForPlayer1: Int,
           private val player2: Player,
           private val pointsForPlayer2: Int,
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

    fun pointsFor(player: Player): Int {
        if (player == player1) {
            return pointsForPlayer1
        } else if (player == player2) {
            return pointsForPlayer2
        }

        throw IllegalArgumentException("Invalid player in this game")
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