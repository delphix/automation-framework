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

package com.delphix.yamlparser.sdk.objects

import org.json.JSONObject

data class SelfServiceContainer(
    val type: String,
    val name: String,
    val activeBranch: String,
    val firstOperation: String,
    val lastOperation: String,
    val lastUpdated: String,
    val notes: String,
    val operationCount: Int,
    val properties: String,
    val reference: String,
    val state: String,
    val template: String
){
    companion object {
        @JvmStatic
        fun fromJson(node: JSONObject): SelfServiceContainer {
            val selfServiceContainer = SelfServiceContainer(
                node.getString("type"),
                node.getString("name"),
                node.getString("activeBranch"),
                node.getString("firstOperation"),
                node.getString("lastOperation"),
                node.getString("lastUpdated"),
                node.get("notes").toString(),
                node.getInt("operationCount"),
                node.get("properties").toString(),
                node.getString("reference"),
                node.getString("state"),
                node.getString("template")
            )
            return selfServiceContainer
        }
    }
}
