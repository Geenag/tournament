package com.ippon.vluce.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Player (val idPlayer: String?, val pseudo: String, var score: Int, var ranking: Int?)