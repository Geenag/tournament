package com.ippon.vluce.infrastructure.adapters.driven.mapper

import com.ippon.vluce.domain.model.Player
import org.bson.Document

fun Document.toPlayer(): Player = Player(
    this.getObjectId("_id").toString(),
    this.getString("pseudo"),
    this.getInteger("score") ?: 0,
    null
)
