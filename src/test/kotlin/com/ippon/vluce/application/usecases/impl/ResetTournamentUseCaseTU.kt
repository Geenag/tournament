package com.ippon.vluce.application.usecases.impl

import com.ippon.vluce.domain.usecases.impl.ResetTournamentUseCaseImpl
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.ResetTournamentUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest

class ResetTournamentUseCaseTU: KoinTest, DescribeSpec({

    val playerRepository: PlayerRepository = mockk<PlayerRepository>()
    val resetTournamentUseCaseTest: ResetTournamentUseCase by KoinJavaComponent.inject(ResetTournamentUseCase::class.java)

    fun playerModuleTest(): Module = module {
        single { playerRepository }
        single<ResetTournamentUseCase> { ResetTournamentUseCaseImpl() }
    }

    beforeSpec {
        startKoin {
            modules(playerModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    it("ResetTournamentUseCase should call deleteAll method from PlayerRepository to reset tournament") {
        every { playerRepository.deleteAll() } just Runs

        resetTournamentUseCaseTest.execute()

        verify(exactly = 1) { playerRepository.deleteAll() }
    }
})