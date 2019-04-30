package com.ultimaspin.ply

import com.ultimaspin.ply.KnockOutTournamentNode.KnockOutPlayerNode
import org.junit.jupiter.api.Assertions.*
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

        matchNode.match!!.addGame(player1 to 11, player2 to 5)
        matchNode.match!!.addGame(player1 to 11, player2 to 5)
        matchNode.match!!.addGame(player1 to 11, player2 to 5)

        assertSame(player1, matchNode.getWinner())

    }

    @Test
    fun `tournament match node subscribes to other match nodes`() {
        val player1 = Player("Bob")
        val playerNode1 = KnockOutPlayerNode(player1)

        val player2 = Player("Fred")
        val playerNode2 = KnockOutPlayerNode(player2)

        val player3 = Player("Joe")
        val playerNode3 = KnockOutPlayerNode(player3)

        val player4 = Player("Albert")
        val playerNode4 = KnockOutPlayerNode(player4)

        val matchNode1 = KnockOutTournamentNode.KnockOutMatchNode(playerNode1, playerNode2)
        val matchNode2 = KnockOutTournamentNode.KnockOutMatchNode(playerNode3, playerNode4)

        val matchNodeFinal = KnockOutTournamentNode.KnockOutMatchNode(matchNode1, matchNode2)

        matchNode1.addGame(player1 to 11, player2 to 5)
        matchNode1.addGame(player1 to 11, player2 to 5)
        matchNode1.addGame(player1 to 11, player2 to 5)
        assertSame(player1, matchNode1.getWinner())

        matchNode2.addGame(player3 to 11, player4 to 5)
        matchNode2.addGame(player3 to 11, player4 to 5)
        matchNode2.addGame(player3 to 11, player4 to 5)
        assertSame(player3, matchNode2.getWinner())

        assertSame(player1, matchNodeFinal.getPlayer1())
        assertSame(player3, matchNodeFinal.getPlayer2())

        assertNotNull(matchNodeFinal.match)
        assertSame(player1, matchNodeFinal.match!!.player1)
        assertSame(player3, matchNodeFinal.match!!.player2)

        matchNodeFinal.addGame(player1 to 11, player3 to 7)
        matchNodeFinal.addGame(player1 to 11, player3 to 7)
        matchNodeFinal.addGame(player1 to 11, player3 to 7)
        assertSame(player1, matchNodeFinal.getWinner())

    }


}