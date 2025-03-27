package com.ippon.vluce.domain.port.driven

interface PlayerRepository {

    fun addPlayer(pseudo: String);

    fun updateScoreByPseudo(pseudo: String, score: Int);

    fun getByPseudo(pseudo: String);

    fun getAllOrderByScore();

    fun deleteAll();

}