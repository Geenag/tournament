package com.ippon.vluce.domain.ports.driven

import com.ippon.vluce.domain.model.Player

interface PlayerRepository {

    fun addPlayer(pseudo: String);

    fun updateScoreByPseudo(pseudo: String, score: Int);

    fun getAllOrderByScore(): List<Player>;

    fun deleteAll();

}