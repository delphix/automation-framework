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

data class SelfServiceBookmark(
    val type: String,
    val reference: String,
    val namespace: String,
    val name: String,
    val branch: String,
    val timestamp: String,
    val description: String,
    val shared: Boolean,
    val container: String,
    val template: String,
    val containerName: String,
    val templateName: String,
    val usable: Boolean,
    val checkoutCount: Int,
    val bookmarkType: String,
    val expiration: Boolean,
    val creationTime: String
){
    companion object {
        @JvmStatic
        fun fromJson(node: JSONObject): SelfServiceBookmark {
            val selfServiceBookmark = SelfServiceBookmark(
                node.get("type").toString(),
                node.get("reference").toString(),
                node.get("namespace").toString(),
                node.get("name").toString(),
                node.get("branch").toString(),
                node.get("timestamp").toString(),
                node.get("description").toString(),
                node.getBoolean("shared"),
                node.get("container").toString(),
                node.get("template").toString(),
                node.get("containerName").toString(),
                node.get("templateName").toString(),
                node.getBoolean("usable"),
                node.getInt("checkoutCount"),
                node.get("bookmarkType").toString(),
                node.getBoolean("expiration"),
                node.get("creationTime").toString()
            )
            return selfServiceBookmark
        }
    }
}
