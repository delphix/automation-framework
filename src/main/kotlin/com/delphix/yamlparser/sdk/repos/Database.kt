/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
 */

package com.delphix.yamlparser.sdk.repos

import com.delphix.yamlparser.sdk.Http as Http
import com.delphix.yamlparser.sdk.repos.Group as Group
import com.delphix.yamlparser.sdk.repos.Repository as Repository
import com.delphix.yamlparser.sdk.objects.Database as DatabaseObj
import org.json.JSONObject

class Database (
    var http: Http
) {
    val resource: String = "/resources/json/delphix/database"

    fun list(): List<DatabaseObj> {
        var databases = mutableListOf<DatabaseObj>()
        val response = http.handleGet(resource).getJSONArray("result")
        for (i in 0 until response.length()) {
            val database = response.getJSONObject(i);
            databases.add(DatabaseObj.fromJson(database))
        }
        return databases
    }

    fun getRefByName(name: String): DatabaseObj {
        val databases: List<DatabaseObj> = list()
        for (database in databases) {
            if (database.name == name) return database
        }
        throw IllegalArgumentException("Database '$name' does not exist.")
    }

    fun provision(name: String, groupName: String, dbName: String, repoName: String): JSONObject {
        val group = Group(http).getRefByName(groupName)
        val parentDb = getRefByName(dbName)
        val repo = Repository(http).getRefByName(repoName)
        val sourcingPolicy = mapOf("type" to "SourcingPolicy", "logsyncEnabled" to false)
        val container = mapOf("type" to "AppDataContainer", "name" to name, "group" to group.reference, "sourcingPolicy" to sourcingPolicy)
        val params = mapOf("timeStamp" to "", "postgresPort" to 5434)
        val source = mapOf("type" to "AppDataVirtualSource", "name" to name, "allowAutoVDBRestartOnHostReboot" to true, "parameters" to params)
        val sourceConfig = mapOf("type" to "AppDataDirectSourceConfig", "path" to "/mnt/provision/$name", "name" to name, "repository" to repo.reference, "linkingEnabled" to true)
        val timeflow = mapOf("type" to "TimeflowPointSemantic", "snapshot" to "LATEST_POINT", "container" to parentDb.reference)
        val request = mapOf(
            "type" to "AppDataProvisionParameters",
            "timeflowPointParameters" to timeflow,
            "sourceConfig" to sourceConfig,
            "source" to source,
            "container" to container
        )
        println(request)
        return http.handlePost("$resource/provision", request)
    }

    fun delete(name: String): JSONObject {
        val ref: String = getRefByName(name).reference
        val request = mapOf("type" to "DeleteParameters", "force" to false)
        return http.handlePost("$resource/$ref/delete", request)

    }
}
