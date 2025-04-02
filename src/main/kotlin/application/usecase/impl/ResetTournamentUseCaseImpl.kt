package com.ippon.vluce.application.usecase.impl

import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.ResetTournamentUseCase
import org.koin.java.KoinJavaComponent

class ResetTournamentUseCaseImpl: ResetTournamentUseCase {

    private val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    override fun execute() {
        playerRepository.deleteAll()
    }

}