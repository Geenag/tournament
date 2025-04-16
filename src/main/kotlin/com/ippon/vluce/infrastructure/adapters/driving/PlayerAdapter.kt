package com.ippon.vluce.infrastructure.adapters.driving

import com.ippon.vluce.domain.usecases.*
import com.ippon.vluce.domain.usecases.exceptions.PlayerException
import com.ippon.vluce.infrastructure.adapters.driving.dto.PlayerDTO
import com.ippon.vluce.infrastructure.adapters.driving.dto.mapper.toPlayerDTO
import com.mongodb.MongoException
import com.mongodb.MongoWriteException
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
            val pseudo = call.receive<PlayerDTO>().pseudo
            try {
                createPlayerUseCase.execute(pseudo)
                call.respondText("Joueur $pseudo créé et stocké", status = HttpStatusCode.Created)
            } catch (exception: PlayerException) {
                call.respondText(exception.message
                    ?: "Une erreur inattendue est survenue, réessayez ou contactez le support",
                    status = HttpStatusCode.Conflict)
            }
        }
        put("{pseudo?}") {
            val pseudo = call.parameters["pseudo"] ?: return@put call.respondText("Pseudo manquant", status = HttpStatusCode.BadRequest)
            val score = call.receive<Int>()
            try {
                changePlayerScoreUseCase.execute(pseudo, score)
                call.respondText("Score du joueur $pseudo modifié",status = HttpStatusCode.OK)
            } catch (exception: PlayerException) {
                call.respondText(exception.message
                    ?: "Une erreur inattendue est survenue, réessayez ou contactez le support",
                    status = HttpStatusCode.BadRequest)
            }
        }
        get("{pseudo?}"){
            val pseudo = call.parameters["pseudo"]
                ?: return@get call.respondText("Pseudo manquant", status = HttpStatusCode.BadRequest)
            try {
                val player = getPlayerInformationsUseCase.execute(pseudo)
                call.respond(player)
            } catch (exception: PlayerException) {
                call.respondText(exception.message
                    ?: "Une erreur inattendue est survenue, réessayez ou contactez le support",
                    status = HttpStatusCode.BadRequest)
            }
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