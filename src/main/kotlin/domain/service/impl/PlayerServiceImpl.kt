package com.ippon.vluce.domain.service.impl

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

    override fun getPlayerInformations(pseudo: String) {
        TODO("Not yet implemented")
    }

    override fun getRanking() {
        TODO("Not yet implemented")
    }

    override fun resetTournamentDatas() {
        playerRepository.deleteAll()
    }
}