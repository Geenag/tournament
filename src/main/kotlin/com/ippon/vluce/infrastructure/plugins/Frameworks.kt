package com.ippon.vluce.infrastructure.plugins

import com.ippon.vluce.application.playerRepositoryModule
import com.ippon.vluce.application.playerUseCaseModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(
            module {
                single {
                    environment.config.propertyOrNull("ktor.mongodb.uri")?.getString()?.takeIf { it.isNotBlank() }
                        ?: run {
                            println("MONGO_URI is undefined. Please enter a valid URI :")
                            readlnOrNull()?.takeIf { it.isNotBlank() }
                                ?: throw RuntimeException("Failed to access MongoDB URI")
                        }
                }
            },
            playerRepositoryModule,
            playerUseCaseModule
        )
    }
}
