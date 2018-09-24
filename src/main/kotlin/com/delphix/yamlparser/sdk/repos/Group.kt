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
import com.delphix.yamlparser.sdk.objects.Group as GroupObj
import org.json.JSONObject

class Group (
    var api: Api
) {
    val resource: String = "/resources/json/delphix/group"

    fun list(): List<GroupObj> {
        var groups = mutableListOf<GroupObj>()
        val response = api.handleGet(resource).getJSONArray("result")
        for (i in 0 until response.length()) {
            val group = response.getJSONObject(i);
            groups.add(GroupObj.fromJson(group))
        }
        return groups
    }

    fun getGroupByName(name: String): GroupObj {
        val groups: List<GroupObj> = list()
        for (group in groups) {
            if (group.name == name) return group
        }
        throw IllegalArgumentException("Group '$name' does not exist.")
    }

}
