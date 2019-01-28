package br.com.rafaelstelles

import br.com.rafaelstelles.dto.EventRequest
import br.com.rafaelstelles.dto.EventResponse
import br.com.rafaelstelles.dto.Issue
import br.com.rafaelstelles.dto.User
import br.com.rafaelstelles.model.Event
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.Javalin
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OctoControllerTest {

    private lateinit var app: Javalin
    private lateinit var url: String
    private lateinit var urlEvent: String

    @Before
    fun beforeTest() {
        val port = 8000;
        app = OctoEventsApplication().startJavalin(port, "octo_events_it")
        url = "http://localhost:$port/"
        urlEvent = url + "events"
    }

    @After
    fun afterTest() {
        transaction {
            SchemaUtils.drop(Event)
        }
        app.stop();
    }

    @Test
    fun createEventsNumber1000() {
        val user = User(1, "login1", "url1")
        val number = "1000"

        val issue1 = Issue(1, number, "title1", "2019-01-26T12:49:48Z", "2019-01-26T12:49:48Z", user);
        val request1 = EventRequest("opened", issue1);

        val response1 = khttp.post(url = urlEvent, data = jacksonObjectMapper().writeValueAsString(request1))
        Assert.assertEquals(200, response1.statusCode)

        val issue2 = Issue(2, number, "title1", "2019-01-26T12:49:48Z", "2019-01-26T12:59:48Z", user);
        val request2 = EventRequest("closed", issue2);

        val response2 = khttp.post(url = urlEvent, data = jacksonObjectMapper().writeValueAsString(request2))
        Assert.assertEquals(200, response2.statusCode)

        val issue3 = Issue(3, number, "title1", "2019-01-26T12:49:48Z", "2019-01-26T13:49:48Z", user);
        val request3 = EventRequest("reopened", issue3);

        val response3 = khttp.post(url = urlEvent, data = jacksonObjectMapper().writeValueAsString(request3))
        Assert.assertEquals(200, response3.statusCode)

        val issues = findAllEventDTOByNumber(number)

        Assert.assertEquals(3, issues.size)
        Assert.assertEquals("opened", issues.get(0).action)
        Assert.assertEquals("closed", issues.get(1).action)
        Assert.assertEquals("reopened", issues.get(2).action)
    }

    @Test
    fun createEventsNumber2000() {
        val urlEvent = url + "events"

        val user = User(2, "login2", "url2")
        val issue1 = Issue(4, "2000", "title2", "2019-01-26T12:49:48Z", "2019-01-26T12:59:48Z", user);
        val request1 = EventRequest("closed", issue1);

        val response1 = khttp.post(url = urlEvent, data = jacksonObjectMapper().writeValueAsString(request1))
        Assert.assertEquals(200, response1.statusCode)

        val issue2 = Issue(5, "2000", "title2", "2019-01-26T12:49:48Z", "2019-01-26T12:49:48Z", user);
        val request2 = EventRequest("opened", issue2);

        val response2 = khttp.post(url = urlEvent, data = jacksonObjectMapper().writeValueAsString(request2))
        Assert.assertEquals(200, response2.statusCode)

        val issue3 = Issue(6, "4000", "title2", "2019-01-26T12:49:48Z", "2019-01-26T12:49:48Z", user);
        val request3 = EventRequest("opened", issue3);

        val response3 = khttp.post(url = urlEvent, data = jacksonObjectMapper().writeValueAsString(request3))
        Assert.assertEquals(200, response3.statusCode)

        val issues = findAllEventDTOByNumber("2000");
        Assert.assertEquals(2, issues.size)
        Assert.assertEquals("opened", issues.get(0).action)
        Assert.assertEquals("closed", issues.get(1).action)
    }

    private fun findAllEventDTOByNumber(number : String) : List<EventResponse>{
        val urlEvent = url + "issues/$number/events"

        val response1 = khttp.get(url = urlEvent)
        Assert.assertEquals(200, response1.statusCode)
        return jacksonObjectMapper().readValue(response1.jsonArray.toString())
    }

}

