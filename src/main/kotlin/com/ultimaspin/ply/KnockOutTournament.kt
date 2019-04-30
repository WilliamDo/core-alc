package com.ultimaspin.ply

class KnockOutTournament {

}


sealed class KnockOutTournamentNode {

    abstract fun getWinner(): Player?

    abstract fun subscribe(node: KnockOutTournamentNode)

    abstract fun onUpdate()

    class KnockOutMatchNode(
        private val tournamentNode1: KnockOutTournamentNode,
        private val tournamentNode2: KnockOutTournamentNode
    ) : KnockOutTournamentNode() {


        private val subscribers = mutableListOf<KnockOutTournamentNode>()

        var match: Match? = null

        init {

            tournamentNode1.subscribe(this)
            tournamentNode2.subscribe(this)

            if (getPlayer1() != null && getPlayer2() != null) {
                match = Match(getPlayer1()!!, getPlayer2()!!, 5)
            }
        }

        override fun getWinner(): Player? {
            return match?.getWinner()
        }

        fun getPlayer1(): Player? = tournamentNode1.getWinner()

        fun getPlayer2(): Player? = tournamentNode2.getWinner()

        fun addGame(pointsOfSomePlayer: Pair<Player, Int>, pointsOfOtherPlayer: Pair<Player, Int>) {
            match!!.addGame(pointsOfSomePlayer, pointsOfOtherPlayer)
            subscribers.forEach { it.onUpdate() }
        }

        override fun subscribe(node: KnockOutTournamentNode) {
            subscribers.add(node)
        }

        override fun onUpdate() {
            if (match == null && getPlayer1() != null && getPlayer2() != null) {
                match = Match(getPlayer1()!!, getPlayer2()!!, 5)
            }
        }
    }



    class KnockOutPlayerNode(private val player: Player) : KnockOutTournamentNode() {
        override fun getWinner(): Player? = player

        override fun subscribe(node: KnockOutTournamentNode) {
            // Do nothing, no need to notify since this class should be immutable
        }

        override fun onUpdate() {
            // Do nothing, no state to update
        }
    }

}


