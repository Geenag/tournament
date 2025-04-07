package com.ippon.vluce.infrastructure.adapters.driven

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.infrastructure.adapters.driven.mapper.toPlayer
import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import org.bson.Document

class PlayerMongoRepositoryImpl(datatabase: MongoDatabase) : PlayerRepository {

    private val playerCollection: MongoCollection<Document> = datatabase.getCollection("player")

    override fun addPlayer(pseudo: String) {
        try {
            playerCollection.insertOne(Document("pseudo", pseudo).append("score", 0))
        } catch (exception: MongoWriteException) {
            throw exception
        }
    }

    override fun updateScoreByPseudo(pseudo: String, score: Int) {
        val filter = Filters.eq(Player::pseudo.name, pseudo)
        val update = Updates.set(Player::score.name, score)
        playerCollection.findOneAndUpdate(filter, update) ?: throw MongoException("Could not find any player with pseudo $pseudo")
    }

    override fun getAllOrderByScore(): List<Player> {
        val sort = Sorts.orderBy(Sorts.descending(Player::score.name))
        return playerCollection.find().sort(sort).toList().map { it.toPlayer() }
    }

    override fun deleteAll() {
        val filterAll = Filters.empty()
        playerCollection.deleteMany(filterAll)
    }
}