package com.ippon.vluce.domain.usecases

import com.ippon.vluce.domain.model.Player

interface GetRankingUseCase {

    fun execute(): List<Player>

}