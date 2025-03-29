package com.ippon.vluce.domain.service

import com.ippon.vluce.domain.model.Player

interface PlayerService {

    fun createPlayer(pseudo : String)

    fun changePlayerScore(pseudo: String, score: Int)

    fun getPlayerInformations(pseudo: String): Player?

    fun getRanking(): List<Player>;

    fun resetTournamentDatas();

}