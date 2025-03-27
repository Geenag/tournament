package com.ippon.vluce.infrastructure.plugins

import com.ippon.vluce.application.playerModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
    }
}
