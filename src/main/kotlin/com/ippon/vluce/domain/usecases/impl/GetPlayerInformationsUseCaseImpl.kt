package com.ippon.vluce.domain.usecases.impl

import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.usecases.GetPlayerInformationsUseCase
import org.koin.java.KoinJavaComponent

class GetPlayerInformationsUseCaseImpl: GetPlayerInformationsUseCase {

    private val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    override fun execute(pseudo: String): Player? {
        val player = playerRepository.getByPseudo(pseudo)
        return player?.also {
            it.ranking = playerRepository.getAllOrderByScore()
                .withIndex()
                .first { player -> player.value.pseudo == pseudo }.index + 1
        }
    }
}