package com.ippon.vluce.domain.usecases.impl

import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.usecases.GetRankingUseCase
import org.koin.java.KoinJavaComponent

class GetRankingUseCaseImpl: GetRankingUseCase {

    private val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    override fun execute(): List<Player> {
        return playerRepository.getAllOrderByScore()
    }

}