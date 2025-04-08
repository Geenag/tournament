package com.ippon.vluce.application

import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.infrastructure.adapters.driven.PlayerMongoRepositoryImpl
import com.ippon.vluce.infrastructure.adapters.driven.configuration.TournamentDatabase
import com.mongodb.client.MongoDatabase
import org.koin.dsl.module

val playerRepositoryModule = module {
    single<MongoDatabase> { TournamentDatabase.getDatabase(get()) }
    single<PlayerRepository> { PlayerMongoRepositoryImpl(get()) }
}