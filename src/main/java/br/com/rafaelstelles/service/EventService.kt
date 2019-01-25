package br.com.rafaelstelles.service

import br.com.rafaelstelles.dto.EventRequestDTO
import br.com.rafaelstelles.dto.EventResponseDTO
import br.com.rafaelstelles.model.Event
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class EventService {

    fun create(eventRequest: EventRequestDTO) : Int {
        var id = 0;
        transaction {
            id = (Event.insert {
                it[idGithub] = eventRequest.issue.id
                it[action] = eventRequest.action
                it[number] = eventRequest.issue.number
                it[title] = eventRequest.issue.title
                it[createdAt] = DateTime.parse(eventRequest.issue.createdAt)
                it[updatedAt] = DateTime.parse(eventRequest.issue.updatedAt)
                it[login] = eventRequest.issue.user.login;
            } get Event.id)!!
        }
        return id;
    }

    fun findAllEventDTOByNumber(number: String): List<EventResponseDTO> {
        var result: List<EventResponseDTO> = ArrayList()
        transaction {
            result = Event.select(Op.build { Event.number eq number })
                    .map { EventResponseDTO(it[Event.action], it[Event.createdAt].toString()) }
        }
        return result;
    }
}