/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
 */

package com.delphix.yamlparser.sdk.repos

import com.delphix.yamlparser.sdk.Http as Http
import com.delphix.yamlparser.sdk.objects.Group as GroupObj
import org.json.JSONObject

class Group (
    var http: Http
) {
    val resource: String = "/resources/json/delphix/group"

    fun list(): List<GroupObj> {
        var groups = mutableListOf<GroupObj>()
        val response = http.handleGet(resource).getJSONArray("result")
        for (i in 0 until response.length()) {
            val group = response.getJSONObject(i);
            groups.add(GroupObj.fromJson(group))
        }
        return groups
    }

    fun getRefByName(name: String): GroupObj {
        val groups: List<GroupObj> = list()
        for (group in groups) {
            if (group.name == name) return group
        }
        throw IllegalArgumentException("Group '$name' does not exist.")
    }

}
