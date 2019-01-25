package br.com.rafaelstelles.factory

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.json.JavalinJackson

object JacksonFactory {
    fun init() {
        val objectMapper = jacksonObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.registerModule(KotlinModule())

        JavalinJackson.configure(objectMapper)
    }
}