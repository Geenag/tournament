package domain.service.impl

import com.ippon.vluce.domain.port.driven.PlayerRepository
import com.ippon.vluce.domain.service.PlayerService
import com.ippon.vluce.domain.service.impl.PlayerServiceImpl
import io.kotest.core.spec.style.DescribeSpec
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

    beforeTest {
        startKoin {
            modules(playerModuleTest())
        }
    }

    afterTest {
        stopKoin()
    }

    describe("PlayerService should call addPlayer method from PlayerRepository to create a new player") {
        every { playerRepository.addPlayer(any()) } just Runs
        val pseudo = "Joueur001"
        playerService.createPlayer(pseudo)
        verify(exactly = 1) { playerRepository.addPlayer(pseudo) }
    }
})
