package com.ippon.vluce.infrastructure.adapters.driven.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

object TournamentDatabase {
    fun getDatabase(uri: String) : MongoDatabase {
        val clientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(uri))
            .retryWrites(true)
            .build()
        val client = MongoClients.create(clientSettings);
        return client.getDatabase("tournament")
    }
}