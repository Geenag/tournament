package com.ippon.vluce.application.usecases.impl

import com.ippon.vluce.domain.usecases.impl.CreatePlayerUseCaseImpl
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.CreatePlayerUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest

class CreatePlayerUseCaseTU: KoinTest, DescribeSpec({

    val playerRepository: PlayerRepository = mockk<PlayerRepository>()
    val createPlayerUseCase: CreatePlayerUseCase by KoinJavaComponent.inject(CreatePlayerUseCase::class.java)

    fun playerModuleTest(): Module = module {
        single { playerRepository }
        single<CreatePlayerUseCase> { CreatePlayerUseCaseImpl() }
    }

    beforeSpec {
        startKoin {
            modules(playerModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    it("CreatePlayerUseCase should call addPlayer method from PlayerRepository to create a new player") {
        every { playerRepository.addPlayer(any()) } just Runs
        val pseudo = "Joueur001"
        createPlayerUseCase.execute(pseudo)
        verify(exactly = 1) { playerRepository.addPlayer(pseudo) }
    }
})