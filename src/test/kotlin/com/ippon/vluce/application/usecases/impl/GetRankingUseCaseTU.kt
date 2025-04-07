package com.ippon.vluce.application.usecases.impl

import com.ippon.vluce.domain.usecases.impl.GetRankingUseCaseImpl
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.usecases.GetRankingUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest

class GetRankingUseCaseTU: KoinTest, DescribeSpec({

    val playerRepository: PlayerRepository = mockk<PlayerRepository>()
    val getRankingUseCase: GetRankingUseCase by KoinJavaComponent.inject(GetRankingUseCase::class.java)

    fun playerModuleTest(): Module = module {
        single { playerRepository }
        single<GetRankingUseCase> { GetRankingUseCaseImpl() }
    }

    beforeSpec {
        startKoin {
            modules(playerModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    it("GetRankingUseCase should get list of ranked players from PlayerRepository") {
        val pseudoJoueur1 = "joueur001"
        val player1 = Player("idPlayer1", pseudoJoueur1, 10, null)
        val pseudoJoueur2 = "joueur002"
        val player2 = Player("idPlayer2", pseudoJoueur2, 20, null)
        val pseudoJoueur3 = "joueur003"
        val player3 = Player("idPlayer3", pseudoJoueur3, 30, null)
        val expectedRanking = listOf(player3, player2, player1)
        every { playerRepository.getAllOrderByScore() } returns expectedRanking
        val res = getRankingUseCase.execute()
        res shouldBe expectedRanking
    }
})