package com.ippon.vluce.domain.usecases.impl

import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.ChangePlayerScoreUseCase
import com.ippon.vluce.domain.usecases.exceptions.PlayerException
import com.mongodb.MongoException
import org.koin.java.KoinJavaComponent

class ChangePlayerScoreUseCaseImpl: ChangePlayerScoreUseCase {

    private val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    override fun execute(pseudo: String, score: Int) {
        try {
            playerRepository.updateScoreByPseudo(pseudo, score)
        } catch (exception: MongoException) {
            throw PlayerException.NoSuchPseudoException(pseudo)
        }
    }

}