package com.ippon.vluce.domain.port.driven

interface PlayerRepository {

    fun addPlayer(pseudo: String);

    fun updateScore(pseudo: String);

    fun getByPseudo(pseudo: String);

    fun getAllOrderByScore();

    fun deleteAll();

}