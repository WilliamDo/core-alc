package com.ultimaspin.ply

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class MatchTest {

    @Test
    fun `players are assigned correctly to match`() {
        val match = Match(Player("Joe"), Player("Fred"), 5)
        assertEquals("Joe", match.player1.name)
        assertEquals("Fred", match.player2.name)
    }

    @Test
    fun `player1 wins first game`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        match.addGame(player1 to 11, player2 to 5)
        assertEquals(1, match.countOfGamesFor(player1))
        assertEquals(0, match.countOfGamesFor(player2))
    }

    @Test
    fun `player2 wins first game`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        match.addGame(player1 to 5, player2 to 11)
        assertEquals(0, match.countOfGamesFor(player1))
        assertEquals(1, match.countOfGamesFor(player2))
    }

    @Test
    fun `player2 wins first game but reverses method parameters`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        match.addGame(player2 to 11, player1 to 5)
        assertEquals(0, match.countOfGamesFor(player1))
        assertEquals(1, match.countOfGamesFor(player2))
    }

}