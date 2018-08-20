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

    @Test fun `can map connectors`() : Unit {
        val jsonString : String = """
        [{"postgres":{"host":"host_ref_in_kms","port":"kms_ref","database":"db_name","username":"kms_ref","password":"kms_ref"}}]
        """
        val connectors = mapper.mapConnectors(mapJsonToNode(jsonString))
        assertEquals("postgres", connectors.get(0)?.name)
    }

    @Test fun `can map environments`() : Unit {
        val jsonString : String = """
        [{"staging":{"branch":"origin/staging","datapod":"Staging","data-sources":[{"oracle-source *arbit-name*":{"ami":"ami-0dfffed98347ecd *optional*"}}],"connectors *overwrite*":[{"oracle-connector":{"database":"name_of_db_in_kms"}}],"when":[{"push":"datapod.refresh"}]}},{"uat":{"branch":"origin/testing","datapod":"test","when":[{"push":"datapod.refresh"},{"pull-request-opened":"datapod.create"},{"pull-request-closed":"datapod.delete"}]}},{"develop":{"branch":"origin/develop","datapod":"Develop","when":[{"push":"bookmark.create"}]}}]
        """
        val environments = mapper.mapEnvironments(mapJsonToNode(jsonString))
        assertEquals("staging", environments.get(0)?.name)
    }

    @Test fun `can map yaml`() : Unit {
        val jsonString : String = """
        {"template":"name_of_template","api_key":"app_key_token in KMS","environments":[{"staging":{"branch":"origin/staging","datapod":"Staging","data-sources":[{"oracle-source *arbit-name*":{"ami":"ami-0dfffed98347ecd *optional*"}}],"connectors *overwrite*":[{"oracle-connector":{"database":"name_of_db_in_kms"}}],"when":[{"push":"datapod.refresh"}]}},{"uat":{"branch":"origin/testing","datapod":"test","when":[{"push":"datapod.refresh"},{"pull-request-opened":"datapod.create"},{"pull-request-closed":"datapod.delete"}]}},{"develop":{"branch":"origin/develop","datapod":"Develop","when":[{"push":"bookmark.create"}]}}]}
        """
        val yaml = mapper.mapYaml(mapJsonToNode(jsonString))
        assertEquals("name_of_template", yaml.template)
    }
}
