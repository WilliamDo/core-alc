package com.ultimaspin.ply

import com.ultimaspin.ply.KnockOutTournamentNode.KnockOutPlayerNode
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class KnockOutTournamentTest {

    @Test
    fun `player node returns itself as the winner`() {

        val player = Player("Bob")
        val playerNode = KnockOutPlayerNode(player)

        assertSame(player, playerNode.getWinner())

    }

    @Test
    fun `tournament match node`() {

        val player1 = Player("Bob")
        val playerNode1 = KnockOutPlayerNode(player1)

        val player2 = Player("Fred")
        val playerNode2 = KnockOutPlayerNode(player2)

        val matchNode = KnockOutTournamentNode.KnockOutMatchNode(playerNode1, playerNode2)

        matchNode.match.addGame(player1 to 11, player2 to 5)
        matchNode.match.addGame(player1 to 11, player2 to 5)
        matchNode.match.addGame(player1 to 11, player2 to 5)

        assertSame(player1, matchNode.getWinner())

    }

}