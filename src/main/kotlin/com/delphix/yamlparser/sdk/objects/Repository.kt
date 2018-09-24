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

data class Repository(
    val type: String,
    val reference: String,
    val name: String,
    val environment: String,
    val linkingEnabled: Boolean,
    val parameters: String,
    val provisioningEnabled: Boolean,
    val staging: Boolean,
    val toolkit: String,
    val version: String
){
    companion object {
        @JvmStatic
        fun fromJson(node: JSONObject): Repository {
            val repository = Repository(
                node.get("type").toString(),
                node.get("reference").toString(),
                node.get("name").toString(),
                node.get("environment").toString(),
                node.getBoolean("linkingEnabled"),
                node.get("parameters").toString(),
                node.getBoolean("provisioningEnabled"),
                node.getBoolean("staging"),
                node.get("toolkit").toString(),
                node.get("version").toString()
            )
            return repository
        }
    }
}
