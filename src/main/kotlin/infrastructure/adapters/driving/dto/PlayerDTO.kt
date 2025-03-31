package com.ippon.vluce.infrastructure.adapters.driving.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO (val idPlayer: String? = null, val pseudo: String, var score: Int = 0)