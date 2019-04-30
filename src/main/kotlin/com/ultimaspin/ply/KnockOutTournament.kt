package com.ultimaspin.ply

class KnockOutTournament {

}


sealed class KnockOutTournamentNode {

    abstract fun getWinner(): Player?

    class KnockOutMatchNode(
        tournamentNode1: KnockOutTournamentNode,
        tournamentNode2: KnockOutTournamentNode
    ) : KnockOutTournamentNode() {

        lateinit var match: Match

        init {
            if (tournamentNode1.getWinner() != null && tournamentNode2.getWinner() != null) {
                match = Match(tournamentNode1.getWinner()!!, tournamentNode2.getWinner()!!, 5)
            }
        }

        override fun getWinner(): Player? {
            return match?.getWinner()
        }

    }

    class KnockOutPlayerNode(private val player: Player) : KnockOutTournamentNode() {
        override fun getWinner(): Player? = player
    }

}

