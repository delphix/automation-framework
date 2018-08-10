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
import com.delphix.yamlparser.sdk.objects.SelfServiceContainer as SelfServiceContainerObj
import org.json.JSONObject

class SelfServiceContainer (
    var api: Api
) {
    val resource: String = "/resources/json/delphix/jetstream/container"

    fun list(): List<SelfServiceContainerObj> {
        var containers = mutableListOf<SelfServiceContainerObj>()
        val response = api.handleGet(resource).getJSONArray("result")
        for (i in 0 until response.length()) {
            val container = response.getJSONObject(i);
            containers.add(SelfServiceContainerObj.fromJson(container))
        }
        return containers
    }

    fun get(ref: String): SelfServiceContainerObj {
        val response = api.handleGet("$resource/$ref")
        val container = SelfServiceContainerObj.fromJson(response.getJSONObject("result"))
        return container
    }

    fun getRefByName(name: String): String {
        val containers: List<SelfServiceContainerObj> = list()
        for (container in containers) {
            if (container.name == name) return container.reference
        }
        throw IllegalArgumentException("Self Service Container '$name' does not exist.")
    }

    fun refresh(name: String): JSONObject {
        val request = mapOf("type" to "JSDataContainerRefreshParameters", "forceOption" to false)
        val ref: String = getRefByName(name)
        return api.handlePost("$resource/$ref/refresh", request)
    }
}
