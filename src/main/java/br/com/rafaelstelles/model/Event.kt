package br.com.rafaelstelles.model

import org.jetbrains.exposed.sql.Table

object Event : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val idGithub = integer("id_github")
    val action = varchar("action", length = 255)
    val number = varchar("number", length = 255)
    val title = varchar("title", length = 255)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updatedAt")
    val login = varchar("login", length = 255)
}