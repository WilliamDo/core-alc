package com.ultimaspin.ply

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


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

    @Test
    fun `unknown player wins a game`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        val thrown = assertThrows<IllegalArgumentException> {
            match.addGame(Player("Ting") to 11, player1 to 5)
        }

        assertEquals("Some or all of player keys are not involved in this match", thrown.message)
    }

    @Test
    fun `conflicting player when adding game details`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        val thrown = assertThrows<IllegalArgumentException> {
            match.addGame(player1 to 11, player1 to 5)
        }

        assertEquals("Some or all of player keys are not involved in this match", thrown.message)
    }


    @Test
    fun `player 1 wins the match in straight games`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        for (i in 0..2) {
            match.addGame(player1 to 11, player2 to 5)
        }

        assertSame(player1, match.getWinner())

    }

    @Test
    fun `player 2 wins the match in straight games`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        for (i in 0..2) {
            match.addGame(player2 to 11, player1 to 5)
        }

        assertSame(player2, match.getWinner())

    }

    @Test
    fun `player wins too many games should throw exception`() {
        val player1 = Player("Joe")
        val player2 = Player("Fred")
        val match = Match(player1, player2, 5)

        val thrown = assertThrows<IllegalStateException> {
            for (i in 0..4) {
                match.addGame(player1 to 11, player2 to 5)
            }
        }

        assertEquals("Match has completed so cannot add another game", thrown.message)
    }
}