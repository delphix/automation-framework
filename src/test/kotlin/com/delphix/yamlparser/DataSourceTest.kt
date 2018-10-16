package com.delphix.yamlparser.tests

import com.delphix.yamlparser.DataSource
import kotlin.test.assertEquals
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class DataSourceTest {

    private val dataSource = DataSource("name", "notes", "replica", "source", 0, "ami")

    @Test fun `can get name`() : Unit {
        assertEquals("name", dataSource.name)
    }

    @Test fun `can get notes`() : Unit {
        assertEquals("notes", dataSource.notes)
    }

    @Test fun `can get replica`() : Unit {
        assertEquals("replica", dataSource.replica)
    }

    @Test fun `can get source`() : Unit {
        assertEquals("source", dataSource.source)
    }

    @Test fun `can get startOrder`() : Unit {
        assertEquals(0, dataSource.startOrder)
    }

    @Test fun `can get ami`() : Unit {
        assertEquals("ami", dataSource.ami)
    }

    @Test fun `can mapFromNode`() : Unit {
        val jsonString : String = """
        {"postgres":{"notes":"Here are notes","replica":"Default","source":"dbdhcp3","start-order":1,"ami":"ami-0dfffed98347ecd"}}
        """
        val mapper = jacksonObjectMapper()
        val node = mapper.readTree(jsonString)
        val newDataSource = DataSource.mapFromNode(node)
        assertEquals("postgres", newDataSource.name)
        assertEquals("Here are notes", newDataSource.notes)
        assertEquals("Default", newDataSource.replica)
        assertEquals("dbdhcp3", newDataSource.source)
        assertEquals(1, newDataSource.startOrder)
        assertEquals("ami-0dfffed98347ecd", newDataSource.ami)
    }
}
