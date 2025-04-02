package domain.service.impl

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.port.driven.PlayerRepository
import com.ippon.vluce.domain.service.PlayerService
import com.ippon.vluce.domain.service.impl.PlayerServiceImpl
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.test.KoinTest


class PlayerServiceTest: KoinTest, DescribeSpec({

    val playerRepository: PlayerRepository = mockk<PlayerRepository>()
    val playerService: PlayerService by KoinJavaComponent.inject(PlayerService::class.java)

    fun playerModuleTest(): Module = module {
        single { playerRepository }
        single<PlayerService> { PlayerServiceImpl() }
    }

    beforeSpec {
        startKoin {
            modules(playerModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    describe("PlayerService") {
        it("should call addPlayer method from PlayerRepository to create a new player") {
            every { playerRepository.addPlayer(any()) } just Runs
            val pseudo = "Joueur001"
            playerService.createPlayer(pseudo)
            verify(exactly = 1) { playerRepository.addPlayer(pseudo) }
        }
        it("should call updateScoreByPseudo method from PlayerRepository to change score of a player") {
            every { playerRepository.updateScoreByPseudo(any(), any()) } just Runs
            val pseudo = "Joueur001"
            val score = 10
            playerService.changePlayerScore(pseudo, score)
            verify(exactly = 1) { playerRepository.updateScoreByPseudo(pseudo, score) }
        }
        it("should get player from PlayerRepository with given pseudo") {
            val pseudo = "joueur001"
            val player = Player("idPlayer", pseudo, 10, null)
            every { playerRepository.getByPseudo(pseudo) } returns player
            every { playerRepository.getAllOrderByScore() } returns listOf(player)
            val res = playerService.getPlayerInformations(pseudo)
            res shouldBe Player("idPlayer", pseudo, 10, 1)
        }
        it("should get list of ranked players from PlayerRepository") {
            val pseudoJoueur1 = "joueur001"
            val player1 = Player("idPlayer1", pseudoJoueur1, 10, null)
            val pseudoJoueur2 = "joueur002"
            val player2 = Player("idPlayer2", pseudoJoueur2, 20, null)
            val pseudoJoueur3 = "joueur003"
            val player3 = Player("idPlayer3", pseudoJoueur3, 30, null)
            val expectedRanking = listOf(player3, player2, player1)
            every { playerRepository.getAllOrderByScore() } returns expectedRanking
            val res = playerService.getRanking()
            res shouldBe expectedRanking
        }
        it("should call deleteAll method from PlayerRepository to reset tournament") {
            every { playerRepository.deleteAll() } just Runs
            playerService.resetTournamentDatas()
            verify(exactly = 1) { playerRepository.deleteAll() }
        }
    }
})
