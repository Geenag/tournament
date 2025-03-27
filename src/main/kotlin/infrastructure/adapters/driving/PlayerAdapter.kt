package com.ippon.vluce.infrastructure.adapters.driving

import com.ippon.vluce.domain.service.PlayerService
import com.ippon.vluce.infrastructure.adapters.driving.dto.PlayerDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.playerRouting() {

    val playerServiceKoin by inject<PlayerService>()

    route("player") {
        post {
            val playerDTO = call.receive<PlayerDTO>()
            playerServiceKoin.createPlayer(playerDTO.pseudo)
            call.respondText("Joueur ${playerDTO.pseudo} créé et stocké",status = HttpStatusCode.Created)
        }
        delete {
            playerServiceKoin.resetTournamentDatas()
            call.respondText { "Tous les joueurs ont été supprimés, prêt pour un nouveau tournoi !" }
        }
        put("{pseudo?}") {
            val pseudoPlayer = call.parameters["pseudo"] ?: return@put call.respondText("Pseudo manquant", status = HttpStatusCode.BadRequest)
            val score = call.receive<Int>()
            playerServiceKoin.changePlayerScore(pseudoPlayer, score)
            call.respondText("Score du joueur $pseudoPlayer modifié",status = HttpStatusCode.OK)
        }
    }
}