/**
* Copyright (c) 2018 by Delphix. All rights reserved. Licensed under the Apache License, Version
* 2.0 (the "License"); you may not use this file except in compliance with the License. You may
* obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.delphix.yamlparser.sdk.repos

import com.delphix.yamlparser.sdk.Api as Api
import org.json.JSONObject

class Database (
    var api: Api
) {
    val resource: String = "/resources/json/delphix/database"

    fun provision(): JSONObject {
        val sourcingPolicy = mapOf("type" to "SourcingPolicy", "logsyncEnabled" to false)
        val container = mapOf("type" to "AppDataContainer", "name" to "Vdms_OZZ", "group" to "GROUP-1", "sourcingPolicy" to sourcingPolicy)
        val params = mapOf("timeStamp" to "", "postgresPort" to 5434)
        val source = mapOf("type" to "AppDataVirtualSource", "name" to "Vdms_OZZ", "allowAutoVDBRestartOnHostReboot" to true, "parameters" to params)
        val sourceConfig = mapOf("type" to "AppDataDirectSourceConfig", "path" to "/mnt/provision/dms-source_Q1Q56U2P", "name" to "Vdms_OZZ", "repository" to "APPDATA_REPOSITORY-2", "linkingEnabled" to true, "environmentUser" to "HOST_USER-1")
        val timeflow = mapOf( "type" to "TimeflowPointSnapshot", "snapshot" to "APPDATA_SNAPSHOT-357")
        val request = mapOf(
            "type" to "AppDataProvisionParameters",
            "timeflowPointParameters" to timeflow,
            "sourceConfig" to sourceConfig,
            "source" to source,
            "container" to container
        )
        return api.handlePost("$resource/provision", request)
    }
}
