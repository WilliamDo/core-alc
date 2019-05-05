package com.ultimaspin.ply

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

}


sealed class KnockOutTournamentNode {

    abstract fun getWinner(): Player?

    abstract fun subscribe(node: KnockOutTournamentNode)

    abstract fun onUpdate()

    class KnockOutMatchNode(
        val tournamentNode1: KnockOutTournamentNode,
        val tournamentNode2: KnockOutTournamentNode
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


