package com.ultimaspin.ply

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MatchTest {
    @Disabled("this is an example test")
    @Test
    fun foo() {
        val match = Match(Player("Joe"), Player("Fred"),5)
        assertEquals("Joe", match.player1.name)
        assertEquals("Fred", match.player2.name)
    }
}