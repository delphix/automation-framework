package com.delphix.yamlparser.tests

import com.delphix.yamlparser.Config
import com.delphix.yamlparser.DataSource
import kotlin.test.assertEquals
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class ConfigTest {

    private val dataSource = DataSource("name", "notes", "replica", "source", 0, "ami")
    private val config = Config("notes", listOf(dataSource))

    @Test fun `can get notes`() : Unit {
        assertEquals("notes", config.notes)
    }

    @Test fun `can get dataSource`() : Unit {
        assertEquals("name", config.dataSources?.get(0)?.name)
    }
}
