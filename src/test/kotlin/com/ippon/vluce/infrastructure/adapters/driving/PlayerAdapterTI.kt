package com.ippon.vluce.infrastructure.adapters.driving

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.usecases.*
import com.ippon.vluce.infrastructure.adapters.driving.dto.PlayerDTO
import com.ippon.vluce.infrastructure.plugins.configureRouting
import com.ippon.vluce.infrastructure.plugins.configureSerialization
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class PlayerAdapterTI: DescribeSpec ({

    val createPlayerUseCase: CreatePlayerUseCase = mockk<CreatePlayerUseCase>()
    val changePlayerScoreUseCase: ChangePlayerScoreUseCase = mockk<ChangePlayerScoreUseCase>()
    val getPlayerInformationsUseCase: GetPlayerInformationsUseCase = mockk<GetPlayerInformationsUseCase>()
    val getRankingUseCase: GetRankingUseCase = mockk<GetRankingUseCase>()
    val resetTournamentUseCase: ResetTournamentUseCase = mockk<ResetTournamentUseCase>()

    fun playerAdapterModuleTest(): Module = module {
        single<CreatePlayerUseCase> { createPlayerUseCase }
        single<ChangePlayerScoreUseCase> { changePlayerScoreUseCase }
        single<GetPlayerInformationsUseCase> { getPlayerInformationsUseCase }
        single<GetRankingUseCase> { getRankingUseCase }
        single<ResetTournamentUseCase> { resetTournamentUseCase }
    }

    beforeSpec {
        startKoin {
            modules(playerAdapterModuleTest())
        }
    }

    afterSpec {
        stopKoin()
    }

    describe("PlayerAdapter") {

        it("should create player") {
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
                every { createPlayerUseCase.execute(any()) } just Runs
                val response = client.post("/tournament/player") {
                    contentType(ContentType.Application.Json)
                    setBody(PlayerDTO(pseudo = "joueur001"))
                }
                response shouldHaveStatus HttpStatusCode.Created
                response.bodyAsText() shouldBe "Joueur joueur001 créé et stocké"
            }
        }
        it("should update player score") {
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
                every { changePlayerScoreUseCase.execute(any(), any()) } just Runs
                val response = client.put("/tournament/player/joueur001") {
                    contentType(ContentType.Application.Json)
                    setBody(10)
                }
                response shouldHaveStatus HttpStatusCode.OK
                response.bodyAsText() shouldBe "Score du joueur joueur001 modifié"
            }
        }
        it("should return player informations") {
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
                val expectedPlayer = Player("idPlayer", "joueur001", 10, 1)
                every { getPlayerInformationsUseCase.execute(any()) } returns expectedPlayer
                val response = client.get("/tournament/player/joueur001")
                response shouldHaveStatus HttpStatusCode.OK
                val returnedPlayer: Player = response.body()
                returnedPlayer.shouldNotBeNull()
                returnedPlayer shouldBe expectedPlayer
            }
        }
    }
})
