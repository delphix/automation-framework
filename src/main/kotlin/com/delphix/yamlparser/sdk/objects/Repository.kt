/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
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
