package com.ippon.vluce.infrastructure.adapters.driving.dto.mapper

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.infrastructure.adapters.driving.dto.PlayerDTO

fun Player.toPlayerDTO(): PlayerDTO = PlayerDTO(
    this.idPlayer,
    this.pseudo,
    this.score)