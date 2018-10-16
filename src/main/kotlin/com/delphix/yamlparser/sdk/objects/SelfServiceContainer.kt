/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
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
