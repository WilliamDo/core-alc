package com.ultimaspin.ply

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException

class GameTest {

    @Test
    fun `player 1 wins with a score of 11`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val game = Game(
                player1 = player1,
                player2 = player2,
                pointsForPlayer1 = 11,
                pointsForPlayer2 = 5
        )

        assertSame(player1, game.getWinner())
        assertSame(player2, game.getLoser())

    }

    @Test
    fun `player 1 wins game with a score over 11`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val game = Game(
                player1 = player1,
                player2 = player2,
                pointsForPlayer1 = 14,
                pointsForPlayer2 = 12
        )

        assertSame(player1, game.getWinner())
        assertSame(player2, game.getLoser())
    }

    @Test
    fun `player 2 wins game with a score of 11`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val game = Game(
                player1 = player1,
                player2 = player2,
                pointsForPlayer1 = 5,
                pointsForPlayer2 = 11
        )

        assertSame(player2, game.getWinner())
        assertSame(player1, game.getLoser())
    }

    @Test
    fun `player 2 wins game with a score over 11`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val game = Game(
                player1 = player1,
                player2 = player2,
                pointsForPlayer1 = 12,
                pointsForPlayer2 = 14
        )

        assertSame(player2, game.getWinner())
        assertSame(player1, game.getLoser())
    }

    // todo consider using assertj to make this test more readable
    @Test
    fun `invalid game state throws exception`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")

        val thrown = assertThrows(IllegalStateException::class.java) {
            Game(
                player1 = player1,
                player2 = player2,
                pointsForPlayer1 = 0,
                pointsForPlayer2 = 0
            )
        }

        assertEquals("Invalid game state", thrown.message)

    }

}