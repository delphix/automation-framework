package com.delphix.yamlparser.tests

import com.delphix.yamlparser.Connector
import kotlin.test.assertEquals
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class ConnectorTest {

    private val connector = Connector("name", "host", "port", "database", "username", "password")

    @Test fun `can get name`() : Unit {
        assertEquals("name", connector.name)
    }

    @Test fun `can get host`() : Unit {
        assertEquals("host", connector.host)
    }

    @Test fun `can get port`() : Unit {
        assertEquals("port", connector.port)
    }

    @Test fun `can get database`() : Unit {
        assertEquals("database", connector.database)
    }

    @Test fun `can get username`() : Unit {
        assertEquals("username", connector.username)
    }

    @Test fun `can get password`() : Unit {
        assertEquals("password", connector.password)
    }

    @Test fun `can mapFromNode`() : Unit {
        val jsonString : String = """
        {"postgres":{"host":"host_ref_in_kms","port":"kms_ref","database":"db_name","username":"kms_ref","password":"kms_ref"}}
        """
        val mapper = jacksonObjectMapper()
        val node = mapper.readTree(jsonString)
        val newConnector = Connector.mapFromNode(node)
        assertEquals("postgres", newConnector.name)
        assertEquals("host_ref_in_kms", newConnector.host)
        assertEquals("kms_ref", newConnector.port)
        assertEquals("db_name", newConnector.database)
        assertEquals("kms_ref", newConnector.username)
        assertEquals("kms_ref", newConnector.password)
    }
}
