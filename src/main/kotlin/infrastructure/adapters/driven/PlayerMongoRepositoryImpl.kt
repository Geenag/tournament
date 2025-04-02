package com.ippon.vluce.infrastructure.adapters.driven

import com.ippon.vluce.domain.model.Player
import com.ippon.vluce.domain.ports.driven.PlayerRepository
import com.ippon.vluce.infrastructure.adapters.driven.configuration.TournamentDatabase
import com.ippon.vluce.infrastructure.adapters.driven.mapper.toPlayer
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import org.bson.Document

class PlayerMongoRepositoryImpl() : PlayerRepository {

    private val playerCollection: MongoCollection<Document> = TournamentDatabase
        .getDatabase().getCollection("player")

    override fun addPlayer(pseudo: String) {
        playerCollection.insertOne(Document("pseudo", pseudo).append("score", 0))
    }

    override fun updateScoreByPseudo(pseudo: String, score: Int) {
        val filter = Filters.eq(Player::pseudo.name, pseudo)
        val update = Updates.set(Player::score.name, score)
        playerCollection.findOneAndUpdate(filter, update)
    }

    override fun getByPseudo(pseudo: String): Player? {
        val filter = Filters.eq(Player::pseudo.name, pseudo)
        return playerCollection.find(filter).first()?.toPlayer()
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