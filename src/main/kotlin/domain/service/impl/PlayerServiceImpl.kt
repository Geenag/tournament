package com.ippon.vluce.domain.service.impl

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.port.driven.PlayerRepository
import com.ippon.vluce.domain.service.PlayerService
import org.koin.java.KoinJavaComponent

class PlayerServiceImpl : PlayerService {

    private val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    override fun createPlayer(pseudo: String) {
        playerRepository.addPlayer(pseudo)
    }

    override fun changePlayerScore(pseudo: String, score: Int) {
        playerRepository.updateScoreByPseudo(pseudo, score)
    }

    override fun getPlayerInformations(pseudo: String): Player? {
        val player = playerRepository.getByPseudo(pseudo)
        return player?.also {
            it.ranking = playerRepository.getAllOrderByScore()
                .withIndex()
                .first { player -> player.value.pseudo == pseudo }.index + 1
        }
    }

    override fun getRanking(): List<Player> {
        return playerRepository.getAllOrderByScore()
    }

    override fun resetTournamentDatas() {
        playerRepository.deleteAll()
    }
}