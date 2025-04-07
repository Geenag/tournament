package com.ippon.vluce.domain.usecases

interface ChangePlayerScoreUseCase {

    fun execute(pseudo : String, score: Int)

}