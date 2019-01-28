package br.com.rafaelstelles.factory

import br.com.rafaelstelles.model.Event
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(database: String) {
        Database.connect(hikari(database));
        transaction {
            SchemaUtils.create(Event)
        }
    }

    private fun hikari(database: String): HikariDataSource {
        val config = HikariConfig()
        val host = "localhost"
        val port = "5432"

        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://$host:$port/$database"
        config.maximumPoolSize = 3
        config.username = "postgres"
        config.password = "postgres"
        config.validate()
        return HikariDataSource(config)
    }

}