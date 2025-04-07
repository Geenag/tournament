package com.ippon.vluce.application.usecases.impl

import com.ippon.vluce.domain.usecases.impl.ChangePlayerScoreUseCaseImpl
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.ChangePlayerScoreUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest

class ChangePlayerScoreUseCaseTU: KoinTest, DescribeSpec({

    val playerRepository: PlayerRepository = mockk<PlayerRepository>()
    val changePlayerScoreUseCase: ChangePlayerScoreUseCase by KoinJavaComponent.inject(ChangePlayerScoreUseCase::class.java)

    fun playerModuleTest(): Module = module {
        single { playerRepository }
        single<ChangePlayerScoreUseCase> { ChangePlayerScoreUseCaseImpl() }
    }

    beforeSpec {
        startKoin {
            modules(playerModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    it("ChangePlayerScoreUseCase should call updateScoreByPseudo method from PlayerRepository to change score of a player") {
        every { playerRepository.updateScoreByPseudo(any(), any()) } just Runs
        val pseudo = "Joueur001"
        val score = 10
        changePlayerScoreUseCase.execute(pseudo, score)
        verify(exactly = 1) { playerRepository.updateScoreByPseudo(pseudo, score) }
    }
})