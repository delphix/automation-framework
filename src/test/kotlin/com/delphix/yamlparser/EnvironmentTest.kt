package com.delphix.yamlparser.tests

import com.delphix.yamlparser.Environment
import com.delphix.yamlparser.Action
import kotlin.test.assertEquals
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class EnvironmentTest {

    private val action = Action("event", "action")
    private val environment = Environment("develop", "branch", "datapod", listOf(action))

    @Test fun `can get name`() : Unit {
        assertEquals("develop", environment.name)
    }

    @Test fun `can get branch`() : Unit {
        assertEquals("branch", environment.branch)
    }

    @Test fun `can get datapod`() : Unit {
        assertEquals("datapod", environment.datapod)
    }

    @Test fun `can get action`() : Unit {
        assertEquals("event", environment.actions?.get(0)?.event)
    }

    @Test fun `can mapFromNode`() : Unit {
        val jsonString : String = """
        {"develop":{"branch":"origin/develop","datapod":"Develop","when":[{"push":"bookmark.create"}]}}
        """
        val mapper = jacksonObjectMapper()
        val node = mapper.readTree(jsonString)
        val newEnv = Environment.mapFromNode(node, mutableListOf(action))
        assertEquals("develop", newEnv.name)
        assertEquals("origin/develop", newEnv.branch)
        assertEquals("Develop", newEnv.datapod)
        assertEquals("event", newEnv.actions?.get(0)?.event)
        assertEquals("action", newEnv.actions?.get(0)?.action)
    }
}
