package com.ippon.vluce.application

import com.ippon.vluce.domain.usecases.*
import com.ippon.vluce.domain.usecases.impl.*
import org.koin.dsl.module

val playerUseCaseModule = module {
    single <CreatePlayerUseCase> { CreatePlayerUseCaseImpl() }
    single <ChangePlayerScoreUseCase> { ChangePlayerScoreUseCaseImpl() }
    single <GetPlayerInformationsUseCase> { GetPlayerInformationsUseCaseImpl() }
    single <GetRankingUseCase> { GetRankingUseCaseImpl() }
    single <ResetTournamentUseCase> { ResetTournamentUseCaseImpl() }
}