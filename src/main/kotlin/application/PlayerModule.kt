package com.ippon.vluce.application

import com.ippon.vluce.domain.port.driven.PlayerRepository
import com.ippon.vluce.domain.service.PlayerService
import com.ippon.vluce.domain.service.impl.PlayerServiceImpl
import com.ippon.vluce.infrastructure.adapters.driven.PlayerMongoRepositoryImpl
import org.koin.dsl.module

val playerModule = module {
    single <PlayerRepository> { PlayerMongoRepositoryImpl() }
    single <PlayerService> { PlayerServiceImpl() }
}