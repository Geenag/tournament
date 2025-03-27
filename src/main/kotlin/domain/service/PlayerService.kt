package com.ippon.vluce.domain.service

interface PlayerService {

    fun createPlayer(pseudo : String)

    fun changePlayerScore(pseudo: String, score: Int)

    fun getPlayerInformations(pseudo: String)

    fun getRanking();

    fun resetTournamentDatas();

}