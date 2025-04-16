package com.ippon.vluce.domain.usecases.impl

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.GetPlayerInformationsUseCase
import com.ippon.vluce.domain.usecases.exceptions.PlayerException
import com.mongodb.MongoException
import org.koin.java.KoinJavaComponent

class GetPlayerInformationsUseCaseImpl: GetPlayerInformationsUseCase {

    private val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    companion object {
        const val DIFFERENCE_BETWEEN_PLAYER_INDEX_AND_RANKING = 1
    }

    override fun execute(pseudo: String): Player {
        try {
            return playerRepository.getAllOrderByScore()
                .withIndex()
                .firstOrNull { player -> player.value.pseudo == pseudo }
                ?.also {
                    it.value.ranking = it.index + DIFFERENCE_BETWEEN_PLAYER_INDEX_AND_RANKING
                }
                ?.value ?: throw PlayerException.NoSuchPseudoException(pseudo)
        } catch (exception: PlayerException) {
            throw exception
        }
    }
}