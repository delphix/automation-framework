/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
 */

package com.delphix.yamlparser.sdk.objects

import org.json.JSONObject

data class Group(
    val type: String,
    val description: String,
    val reference: String,
    val namespace: String,
    val name: String
){
    companion object {
        @JvmStatic
        fun fromJson(node: JSONObject): Group {
            val group = Group(
                node.get("type").toString(),
                node.get("description").toString(),
                node.get("reference").toString(),
                node.get("namespace").toString(),
                node.get("name").toString()
            )
            return group
        }
    }
}
