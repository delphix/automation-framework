package com.delphix.yamlparser.tests

import com.delphix.yamlparser.Action
import kotlin.test.assertEquals
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class ActionTest {

    private val action = Action("event", "action")

    @Test fun `can get event`() : Unit {
        assertEquals("event", action.event)
    }
    @Test fun `can get action`() : Unit {
        assertEquals("action", action.action)
    }

    @Test fun `can mapFromNode`() : Unit {
        val jsonString : String = """
        {"push":"datapod.refresh"}
        """
        val mapper = jacksonObjectMapper()
        val node = mapper.readTree(jsonString)
        val newAction = Action.mapFromNode(node)
        assertEquals("push", newAction.event)
        assertEquals("datapod.refresh", newAction.action)
    }
}
