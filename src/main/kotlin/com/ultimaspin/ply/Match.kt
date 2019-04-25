package com.ultimaspin.ply

class Match(val player1: Player,
            val player2: Player,
            numberOfGames: Int) {

    private val games = mutableListOf<Game>()

    fun getWinner(): Player {
        TODO()
    }

    fun getLoser(): Player {
        TODO()
    }

    fun countOfGamesFor(player: Player): Int {
        return games.filter { it.getWinner() == player }.size
    }

    fun addGame(pointsOfSomePlayer: Pair<Player, Int>, pointsOfOtherPlayer: Pair<Player, Int>) {

        // todo assert that both players are for this match
        // todo check maximum number of games are not exceeded

        // interesting side effect of using player as keys means that it doesn't matter who is player 1 or 2

        games.add(Game(
            player1 = pointsOfSomePlayer.first,
            pointsForPlayer1 = pointsOfSomePlayer.second,
            player2 = pointsOfOtherPlayer.first,
            pointsForPlayer2 = pointsOfOtherPlayer.second
        ))
    }

    fun updateGame(gameNumber: Int, pointsOfSomePlayer: Pair<Player, Int>, pointsOfOtherPlayer: Pair<Player, Int>) {
        TODO()
    }

}