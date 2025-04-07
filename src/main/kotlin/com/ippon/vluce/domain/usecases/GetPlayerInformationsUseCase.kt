package com.ippon.vluce.domain.usecases

import com.ippon.vluce.domain.model.Player

interface GetPlayerInformationsUseCase {

    fun execute(pseudo: String): Player?

}