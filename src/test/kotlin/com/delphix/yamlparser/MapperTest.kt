package com.delphix.yamlparser.tests

import com.delphix.yamlparser.Mapper
import kotlin.test.assertEquals
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class MapperTest {
    val mapper = Mapper()

    fun mapJsonToNode(json: String) : JsonNode {
        val mapper = jacksonObjectMapper()
        return mapper.readTree(json)
    }

    @Test fun `can map config`() : Unit {
        val jsonString : String = """
        {"notes":"description *optional*","data-sources":[{"postgres":{"notes":"Here are notes","replica":"Default","source":"dbdhcp3","start-order":1,"ami":"ami-0dfffed98347ecd"}}]}
        """
        val config = mapper.mapConfig(mapJsonToNode(jsonString))
        assertEquals("description *optional*", config.notes)
    }
}
