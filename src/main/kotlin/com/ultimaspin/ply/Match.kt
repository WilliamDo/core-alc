package com.ultimaspin.ply

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class Match(val player1: Player,
            val player2: Player,
            private val numberOfGames: Int) {

    private val games = mutableListOf<Game>()

    fun getWinner(): Player {
        val (gamesForPlayer1, gamesForPlayer2) = games.partition { it.getWinner() == player1 }
        val gamesToWin = numberOfGames / 2 + 1

        if (gamesForPlayer1.size == gamesToWin) {
            return player1
        } else if (gamesForPlayer2.size == gamesToWin) {
            return player2
        }

        throw IllegalStateException("Unable to determine game winner")
    }

    fun getLoser(): Player {
        TODO()
    }

    fun countOfGamesFor(player: Player): Int {
        return games.filter { it.getWinner() == player }.size
    }

    fun addGame(pointsOfSomePlayer: Pair<Player, Int>, pointsOfOtherPlayer: Pair<Player, Int>) {

        validatePlayers(pointsOfSomePlayer.first, pointsOfOtherPlayer.first)
        validateMaxNumberOfGames()

        // interesting side effect of using player as keys means that it doesn't matter who is player 1 or 2

        games.add(Game(
            player1 = pointsOfSomePlayer.first,
            pointsForPlayer1 = pointsOfSomePlayer.second,
            player2 = pointsOfOtherPlayer.first,
            pointsForPlayer2 = pointsOfOtherPlayer.second
        ))
    }

    fun updateGame(gameNumber: Int, pointsOfSomePlayer: Pair<Player, Int>, pointsOfOtherPlayer: Pair<Player, Int>) {
        TODO()
    }

    private fun validatePlayers(somePlayer: Player, otherPlayer: Player) {
        if (setOf(player1, player2) != setOf(somePlayer, otherPlayer)) {
            throw IllegalArgumentException("Some or all of player keys are not involved in this match")
        }
    }

    private fun validateMaxNumberOfGames() {
        if (games.size == numberOfGames) {
            throw IllegalStateException("Maximum number of games reached")
        }
    }

}