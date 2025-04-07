package com.ippon.vluce.domain.usecases.impl

import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.CreatePlayerUseCase
import org.koin.java.KoinJavaComponent

class CreatePlayerUseCaseImpl: CreatePlayerUseCase {

    private val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    override fun execute(pseudo: String) {
        playerRepository.addPlayer(pseudo)
    }

}
