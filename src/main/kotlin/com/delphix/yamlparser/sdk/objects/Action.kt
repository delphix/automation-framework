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

data class Action (
     val type: String,
     val reference: String,
     val namespace: String,
     val title: String,
     val details: String,
     val startTime: String,
     val endTime: String,
     val user: String,
     val userAgent: String,
     val parentAction: String,
     val actionType: String,
     val state: String,
     val workSource: String,
     val workSourceName: String,
     val report: String,
     val failureDescription: String,
     val failureAction: String,
     val failureMessageCode: String
){
    companion object {
        @JvmStatic
        fun fromJson(node: JSONObject): Action {
            val action = Action(
                node.get("type").toString(),
                node.get("reference").toString(),
                node.get("namespace").toString(),
                node.get("title").toString(),
                node.get("details").toString(),
                node.get("startTime").toString(),
                node.get("endTime").toString(),
                node.get("user").toString(),
                node.get("userAgent").toString(),
                node.get("parentAction").toString(),
                node.get("actionType").toString(),
                node.get("state").toString(),
                node.get("workSource").toString(),
                node.get("workSourceName").toString(),
                node.get("report").toString(),
                node.get("failureDescription").toString(),
                node.get("failureAction").toString(),
                node.get("failureMessageCode").toString()
            )
            return action
        }
    }
}
