package com.ultimaspin.ply

import java.util.*

class KnockOutTournament(val finalMatchNode: KnockOutTournamentNode) {

    fun getPlayers(): List<Player> {
        return getPlayers(finalMatchNode)
    }

    private fun getPlayers(tournamentNode: KnockOutTournamentNode): List<Player> {

        return when (tournamentNode) {
            is KnockOutTournamentNode.KnockOutMatchNode -> {
                getPlayers(tournamentNode.tournamentNode1) + getPlayers(tournamentNode.tournamentNode2)
            }
            is KnockOutTournamentNode.KnockOutPlayerNode -> {
                listOf(tournamentNode.getWinner()!!)
            }
        }

    }

    fun getMatch(id: UUID): Match? {
        return getMatch(finalMatchNode, id)
    }

    fun updateMatch(id: UUID, match: Match) {
        TODO("What additional parameters should be used to store match details?")
    }

    // todo why does this smell so much?
    private fun getMatch(tournamentNode: KnockOutTournamentNode, id: UUID): Match? {
        if (tournamentNode is KnockOutTournamentNode.KnockOutMatchNode) {
            if (id == tournamentNode.id) {
                return tournamentNode.match
            } else {
                var match = getMatch(tournamentNode.tournamentNode1, id)
                if (match != null) {
                    return match
                }

                match = getMatch(tournamentNode.tournamentNode2, id)
                if (match != null) {
                    return match
                }
            }
        }

        return null
    }

}


sealed class KnockOutTournamentNode {

    abstract fun getWinner(): Player?
    // todo probably want a more testable way to assign IDs
    val id: UUID = UUID.randomUUID()

    class KnockOutMatchNode(
        val tournamentNode1: KnockOutTournamentNode,
        val tournamentNode2: KnockOutTournamentNode
    ) : KnockOutTournamentNode() {

        private var _match: Match? = null

        var match: Match?
            get() {
                return if (this._match != null) {
                    _match
                } else if (getPlayer1() != null && getPlayer2() != null) {
                    _match = Match(getPlayer1()!!, getPlayer2()!!, 5)
                    _match
                } else {
                    null
                }
            }
            set(value) {
                // todo validate the players
                _match = value
            }

        init {
            if (getPlayer1() != null && getPlayer2() != null) {
                _match = Match(getPlayer1()!!, getPlayer2()!!, 5)
            }
        }

        override fun getWinner(): Player? {
            return _match?.getWinner()
        }

        fun getPlayer1(): Player? = tournamentNode1.getWinner()

        fun getPlayer2(): Player? = tournamentNode2.getWinner()

    }



    class KnockOutPlayerNode(private val player: Player) : KnockOutTournamentNode() {
        override fun getWinner(): Player? = player
    }

}

class KnockOutTournamentBuilder {

    companion object {
        fun toKnockOutTournament(players: List<Player>): KnockOutTournament {
            return KnockOutTournament(toTournamentTree(players))
        }

        private fun toTournamentTree(players: List<Player>): KnockOutTournamentNode {

            if (players.isEmpty()) {
                throw IllegalArgumentException("Cannot create tournament for no players")
            }

            if (players.size == 1) {
                return KnockOutTournamentNode.KnockOutPlayerNode(player = players[0])
            }

            val (first, second) = partition(players)

            return KnockOutTournamentNode.KnockOutMatchNode(
                toTournamentTree(first),
                toTournamentTree(second)
            )

        }

        private fun partition(players: List<Player>): Pair<List<Player>, List<Player>> {
            val first = mutableListOf<Player>()
            val second = mutableListOf<Player>()


            players.forEachIndexed { index, player ->
                if (index % 2 == 0) {
                    first.add(player)
                } else {
                    second.add(player)
                }
            }

            return first to second
        }


    }

}


