package com.ippon.vluce.application

import com.ippon.vluce.application.usecase.impl.*
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.*
import com.ippon.vluce.infrastructure.adapters.driven.PlayerMongoRepositoryImpl
import com.ippon.vluce.infrastructure.adapters.driven.configuration.TournamentDatabase
import com.mongodb.client.MongoDatabase
import org.koin.dsl.module

val playerModule = module {
    single <MongoDatabase> { TournamentDatabase.getDatabase() }
    single <PlayerRepository> { PlayerMongoRepositoryImpl(get()) }
    single <CreatePlayerUseCase> { CreatePlayerUseCaseImpl() }
    single <ChangePlayerScoreUseCase> { ChangePlayerScoreUseCaseImpl() }
    single <GetPlayerInformationsUseCase> { GetPlayerInformationsUseCaseImpl() }
    single <GetRankingUseCase> { GetRankingUseCaseImpl() }
    single <ResetTournamentUseCase> { ResetTournamentUseCaseImpl() }
}