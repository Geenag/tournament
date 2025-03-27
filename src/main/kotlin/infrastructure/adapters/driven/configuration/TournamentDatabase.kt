package com.ippon.vluce.infrastructure.adapters.driven.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

object TournamentDatabase {
    private const val URI = "mongodb+srv://admin:bk5RUGlBQ0x8@clustervlu.vxjrghe.mongodb.net"
    fun getDatabase() : MongoDatabase {
        val clientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(URI))
            .retryWrites(true)
            .build()
        val client = MongoClients.create(clientSettings);
        return client.getDatabase("tournament")
    }
}