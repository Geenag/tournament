package com.ippon.vluce.application

import com.ippon.vluce.application.usecase.impl.*
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.*
import com.ippon.vluce.infrastructure.adapters.driven.PlayerMongoRepositoryImpl
import org.koin.dsl.module

val playerModule = module {
    single <PlayerRepository> { PlayerMongoRepositoryImpl() }
    single <CreatePlayerUseCase> { CreatePlayerUseCaseImpl() }
    single <ChangePlayerScoreUseCase> { ChangePlayerScoreUseCaseImpl() }
    single <GetPlayerInformationsUseCase> { GetPlayerInformationsUseCaseImpl() }
    single <GetRankingUseCase> { GetRankingUseCaseImpl() }
    single <ResetTournamentUseCase> { ResetTournamentUseCaseImpl() }
}