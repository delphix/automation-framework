/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
 */

package com.delphix.yamlparser.sdk.objects

import org.json.JSONObject

data class Database(
    val type: String,
    val previousTimeflow: String,
    val creationTime: String,
    val os: String,
    val masked: Boolean,
    val description: String,
    val transformation: Boolean,
    val processor: String,
    val reference: String,
    val currentTimeflow: String,
    val toolkit: String,
    val performanceMode: String,
    val namespace: String,
    val name: String,
    val provisionContainer: String,
    val restoration: Boolean,
    val group: String
){
    companion object {
        @JvmStatic
        fun fromJson(node: JSONObject): Database {
            val database = Database(
                node.get("type").toString(),
                node.get("previousTimeflow").toString(),
                node.get("creationTime").toString(),
                node.get("os").toString(),
                node.getBoolean("masked"),
                node.get("description").toString(),
                node.getBoolean("transformation"),
                node.get("processor").toString(),
                node.get("reference").toString(),
                node.get("currentTimeflow").toString(),
                node.get("toolkit").toString(),
                node.get("performanceMode").toString(),
                node.get("namespace").toString(),
                node.get("name").toString(),
                node.get("provisionContainer").toString(),
                node.getBoolean("restoration"),
                node.get("group").toString()
            )
            return database
        }
    }
}
