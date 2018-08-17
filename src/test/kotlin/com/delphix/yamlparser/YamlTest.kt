package com.delphix.yamlparser.tests

import com.delphix.yamlparser.Yaml
import com.delphix.yamlparser.Config
import com.delphix.yamlparser.DataSource
import com.delphix.yamlparser.Environment
import com.delphix.yamlparser.Action
import com.delphix.yamlparser.Connector
import kotlin.test.assertEquals
import org.junit.Test

class YamlTest {

    private val dataSource = DataSource("name", "notes", "replica", "source", 0, "ami")
    private val config = Config("notes", listOf(dataSource))
    private val connector = Connector("name", "host", "port", "database", "username", "password")
    private val action = Action("event", "action")
    private val environment = Environment("develop", "branch", "datapod", listOf(action))
    private val yaml = Yaml("template", "api_key", config, listOf(connector), listOf(environment));

    @Test fun `can get template`() : Unit {
        assertEquals("template", yaml.template)
    }

    @Test fun `can get api_key`() : Unit {
        assertEquals("api_key", yaml.api_key)
    }

    @Test fun `can get environment`() : Unit {
        assertEquals("branch", yaml.environments.get(0).branch);
    }

    @Test fun `can get connector`() : Unit {
        assertEquals("name", yaml.connectors?.get(0)?.name);
    }

    @Test fun `can get config`() : Unit {
        assertEquals("notes", yaml.config?.notes);
    }
}
