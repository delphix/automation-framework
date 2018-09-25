package com.delphix.yamlparser

import com.delphix.yamlparser.sdk.Http as Http
import com.delphix.yamlparser.sdk.Delphix as Delphix
import com.delphix.yamlparser.sdk.objects.Action as Action
import com.delphix.yamlparser.sdk.objects.Job as Job
import com.delphix.yamlparser.Mapper
import kotlin.test.assertEquals
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.*
import org.json.JSONObject

class RunnerTest {
    fun mapJsonToNode(json: String) : JsonNode {
        val mapper = jacksonObjectMapper()
        return mapper.readTree(json)
    }
    val jsonString : String = """
    {"template":"name_of_template","parent":"parent-source","api_key":"app_key_token in KMS","config":{"notes":"description *optional*","data-sources":[{"postgres":{"notes":"Here are notes","replica":"Default","source":"dbdhcp3","start-order":1,"ami":"ami-0dfffed98347ecd"}}]},"connectors":[{"postgres":{"host":"host_ref_in_kms","port":"kms_ref","database":"db_name","username":"kms_ref","password":"kms_ref"}}],"environments":[{"staging":{"branch":"origin/staging","datapod":"Staging","data-sources":[{"oracle-source *arbit-name*":{"ami":"ami-0dfffed98347ecd *optional*"}}],"connectors *overwrite*":[{"oracle-connector":{"database":"name_of_db_in_kms"}}],"when":[{"push":"datapod.refresh"}]}},{"uat":{"branch":"origin/testing","datapod":"test","when":[{"push":"datapod.refresh"},{"pull-request-opened":"datapod.create"},{"pull-request-closed":"datapod.delete"}]}},{"develop":{"branch":"origin/develop","datapod":"Develop","when":[{"push":"bookmark.create"}]}}]}
    """
    val yaml: Yaml = Mapper().mapYaml(mapJsonToNode(jsonString))
    val env: Map<String, String> = mapOf("gitBranch" to "origin/develop", "gitCommit" to "0bb822091eed2ae15d67ed91f3ba8591b39e6c4e", "gitEvent" to "push", "delphixEngine" to "delphixEngine", "delphixUser" to "delphixUser", "delphixPass" to "delphixPass", "delphixRepository" to "Postgres vFiles (9.6.8)")
    val delphix : Delphix = mock()
    val runner: Runner = Runner(yaml, env, delphix)

    @Test fun `can call Delphix`() : Unit {
        /*
        doNothing().whenever(delphix).login(any(), any())
        val responseString : String = """
        {"result":"","action":"ACTION-590","type":"OKResult","job":"JOB-371","status":"OK"}
        """
        whenever(delphix.selfServiceContainer().refresh(any())).thenReturn(JSONObject(responseString))
        val result = runner.callDelphix("Develop", "datapod.refresh")
        */
    }

}
