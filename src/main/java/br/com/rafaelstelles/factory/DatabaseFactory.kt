package br.com.rafaelstelles.factory

import br.com.rafaelstelles.model.Event
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(hikari());
        transaction {
            SchemaUtils.create(Event)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        val host = "localhost"
        val port = "5432"
        val database = "octo_events"

        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://$host:$port/$database"
        config.maximumPoolSize = 3
        config.username = "postgres"
        config.password = "postgres"
        config.validate()
        return HikariDataSource(config)
    }

}