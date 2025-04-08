package com.ippon.vluce.infrastructure.plugins

import com.ippon.vluce.infrastructure.adapters.driving.playerRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("tournament") {
            playerRouting()
        }
    }
}
