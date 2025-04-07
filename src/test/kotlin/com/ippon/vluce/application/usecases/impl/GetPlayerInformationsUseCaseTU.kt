package com.ippon.vluce.application.usecases.impl

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.usecases.GetPlayerInformationsUseCase
import com.ippon.vluce.domain.usecases.impl.GetPlayerInformationsUseCaseImpl
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest

class GetPlayerInformationsUseCaseTU: KoinTest, DescribeSpec({

    val playerRepository: PlayerRepository = mockk<PlayerRepository>()
    val getPlayerInformationsUseCase: GetPlayerInformationsUseCase by KoinJavaComponent.inject(GetPlayerInformationsUseCase::class.java)

    fun playerModuleTest(): Module = module {
        single { playerRepository }
        single<GetPlayerInformationsUseCase> { GetPlayerInformationsUseCaseImpl() }
    }

    beforeSpec {
        startKoin {
            modules(playerModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    describe("GetPlayerInformationsUseCase") {

        it(" should get player from PlayerRepository with given pseudo when exists") {
            val pseudo = "joueur001"
            val player = Player("idPlayer", pseudo, 10, null)
            every { playerRepository.getAllOrderByScore() } returns listOf(player)

            val res = getPlayerInformationsUseCase.execute(pseudo)

            res shouldBe Player("idPlayer", pseudo, 10, 1)
        }

        it(" should return null without fail when no player found") {
            val pseudoSearched = "joueur001"
            val existingPseudo = "joueur002"
            val existingPlayer = Player("idPlayer", existingPseudo, 10, null)
            every { playerRepository.getAllOrderByScore() } returns listOf(existingPlayer)

            val res = getPlayerInformationsUseCase.execute(pseudoSearched)

            res shouldBe null
        }
    }
})