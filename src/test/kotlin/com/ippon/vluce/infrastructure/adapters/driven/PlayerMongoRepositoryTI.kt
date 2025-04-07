package com.ippon.vluce.infrastructure.adapters.driven

import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.infrastructure.adapters.driven.PlayerMongoRepositoryImpl
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers(disabledWithoutDocker = true)
class PlayerMongoRepositoryTI: DescribeSpec ({

    val mongoDBContainer = MongoDBContainer(
        DockerImageName.parse("mongo:latest"))

    val playerRepository by KoinJavaComponent.inject<PlayerRepository>(PlayerRepository::class.java)

    fun playerMongoRepositoryModuleTest(): Module = module {
        single<MongoDatabase> {
            mongoDBContainer.start()
            val uriTest: String = mongoDBContainer.replicaSetUrl

            val clientTestSettings = MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(uriTest))
                .retryWrites(true)
                .build()
            val clientTest = MongoClients.create(clientTestSettings);
            clientTest.getDatabase("tournament-test")
        }
        single<PlayerRepository> { PlayerMongoRepositoryImpl(get()) }
    }

    beforeSpec {
        startKoin {
            modules(playerMongoRepositoryModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    describe("PlayerMongoRepository") {

        val pseudo1 = "joueur001"
        playerRepository.addPlayer(pseudo1)

        it("should return player created with id, given pseudo, score 0 and ranking null") {
            val playerBDD = playerRepository.getByPseudo(pseudo1)
            playerBDD shouldNotBeNull { idPlayer }
            playerBDD!!.pseudo shouldBe pseudo1
            playerBDD.score shouldBe 0
            playerBDD.ranking shouldBe null
        }

        it("should update scores and return ranked players") {
            val pseudo2 = "joueur002"
            val pseudo3 = "joueur003"
            playerRepository.addPlayer(pseudo2)
            playerRepository.addPlayer(pseudo3)
            playerRepository.updateScoreByPseudo(pseudo1, 10)
            playerRepository.updateScoreByPseudo(pseudo2, 20)
            playerRepository.updateScoreByPseudo(pseudo3, 30)
            val ranking = playerRepository.getAllOrderByScore()
            ranking.size shouldBe 3
            ranking.map { it.score }.zipWithNext().all { it.first >= it.second } shouldBe true
        }

        it("should delete all players in database") {
            playerRepository.getAllOrderByScore().size shouldBeGreaterThan 0
            playerRepository.deleteAll()
            playerRepository.getAllOrderByScore().size shouldBe 0
        }
    }
})