package com.ippon.vluce.domain.port.driven

import com.ippon.vluce.domain.model.Player

interface PlayerRepository {

    fun addPlayer(pseudo: String);

    fun updateScoreByPseudo(pseudo: String, score: Int);

    fun getByPseudo(pseudo: String): Player?;

    fun getAllOrderByScore(): List<Player>;

    fun deleteAll();

}