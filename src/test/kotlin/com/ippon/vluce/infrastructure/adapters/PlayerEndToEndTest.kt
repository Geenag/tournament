package com.ippon.vluce.infrastructure.adapters

import com.ippon.vluce.application.playerUseCaseModule
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.infrastructure.adapters.driven.PlayerMongoRepositoryImpl
import com.ippon.vluce.infrastructure.adapters.driving.dto.PlayerDTO
import com.ippon.vluce.infrastructure.plugins.configureRouting
import com.ippon.vluce.infrastructure.plugins.configureSerialization
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers(disabledWithoutDocker = true)
class PlayerAdapterTI: DescribeSpec ({

    val mongoDBContainer = MongoDBContainer(
        DockerImageName.parse("mongo:latest"))

    fun playerEndToEndModuleTest(): Module = module {
        single<MongoDatabase> {
            mongoDBContainer.start()
            val uriTest: String = mongoDBContainer.replicaSetUrl

            val clientTestSettings = MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(uriTest))
                .retryWrites(true)
                .build()
            val clientTest = MongoClients.create(clientTestSettings);
            clientTest.getDatabase("tournament-E2E-test")
        }
        single<PlayerRepository> { PlayerMongoRepositoryImpl(get()) }
    }

    beforeSpec {
        startKoin {
            modules(playerEndToEndModuleTest(),
                playerUseCaseModule)
        }
    }

    afterSpec {
        stopKoin()
    }

    describe("Player module end to end") {

        it("should create players, update scores and return ranked players") {
            testApplication {
                environment {
                    config = MapApplicationConfig("ktor.environment" to "dev")
                }
                application {
                    configureRouting()
                    configureSerialization()
                }
                val client = createClient {
                    install(ContentNegotiation) {
                        json()
                    }
                }
                client.post("/tournament/player") {
                    contentType(ContentType.Application.Json)
                    setBody(PlayerDTO(pseudo = "joueur001"))
                }
                client.post("/tournament/player") {
                    contentType(ContentType.Application.Json)
                    setBody(PlayerDTO(pseudo = "joueur002"))
                }
                client.post("/tournament/player") {
                    contentType(ContentType.Application.Json)
                    setBody(PlayerDTO(pseudo = "joueur003"))
                }
                client.put("/tournament/player/joueur001") {
                    contentType(ContentType.Application.Json)
                    setBody(10)
                }
                client.put("/tournament/player/joueur002") {
                    contentType(ContentType.Application.Json)
                    setBody(20)
                }
                client.put("/tournament/player/joueur003") {
                    contentType(ContentType.Application.Json)
                    setBody(30)
                }
                val response = client.get("/tournament/player/ranking")
                response shouldHaveStatus HttpStatusCode.OK
                val ranking: List<PlayerDTO> = response.body()
                ranking.first().pseudo shouldBe "joueur003"
                ranking.last().pseudo shouldBe "joueur001"
            }
        }
    }
})
