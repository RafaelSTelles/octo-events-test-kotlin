package br.com.rafaelstelles.service

import br.com.rafaelstelles.dto.EventRequest
import br.com.rafaelstelles.dto.EventResponse
import br.com.rafaelstelles.model.Event
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class EventServiceImpl : EventService {

    override fun create(eventRequest: EventRequest) : Int {
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

    override fun findAllEventDTOByNumber(number: String): List<EventResponse> = transaction {
        Event.select(Op.build { Event.number eq number })
                .map { EventResponse(it[Event.action], it[Event.createdAt].toString()) }
    }
}