package com.ippon.vluce.infrastructure.adapters.driving

import com.ippon.vluce.domain.usecases.*
import com.ippon.vluce.infrastructure.adapters.driving.dto.PlayerDTO
import com.ippon.vluce.infrastructure.adapters.driving.dto.mapper.toPlayerDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.playerRouting() {

    val createPlayerUseCase by inject<CreatePlayerUseCase>()
    val changePlayerScoreUseCase by inject<ChangePlayerScoreUseCase>()
    val getPlayerInformationsUseCase by inject<GetPlayerInformationsUseCase>()
    val getRankingUseCase by inject<GetRankingUseCase>()
    val resetTournamentUseCase by inject<ResetTournamentUseCase>()

    route("player") {
        post {
            val playerDTO = call.receive<PlayerDTO>()
            createPlayerUseCase.execute(playerDTO.pseudo)
            call.respondText("Joueur ${playerDTO.pseudo} créé et stocké",status = HttpStatusCode.Created)
        }
        put("{pseudo?}") {
            val pseudoPlayer = call.parameters["pseudo"] ?: return@put call.respondText("Pseudo manquant", status = HttpStatusCode.BadRequest)
            val score = call.receive<Int>()
            changePlayerScoreUseCase.execute(pseudoPlayer, score)
            call.respondText("Score du joueur $pseudoPlayer modifié",status = HttpStatusCode.OK)
        }
        get("{pseudo?}"){
            val pseudo = call.parameters["pseudo"]
                ?: return@get call.respondText("Pseudo manquant", status = HttpStatusCode.BadRequest)
            val player = getPlayerInformationsUseCase.execute(pseudo)
                ?: return@get call.respondText  ("Pas de joueur trouvé avec le pseudo \"$pseudo\"", status = HttpStatusCode.BadRequest)
            call.respond(player)
        }
        get("ranking") {
            val listPlayer = getRankingUseCase.execute()
            if(listPlayer.isNotEmpty()){
                call.respond(listPlayer.map { it.toPlayerDTO() })
            } else {
                return@get call.respondText("Pas de joueur trouvé", status = HttpStatusCode.NoContent)
            }
        }
        delete {
            resetTournamentUseCase.execute()
            call.respondText { "Tous les joueurs ont été supprimés, prêt pour un nouveau tournoi !" }
        }
    }
}