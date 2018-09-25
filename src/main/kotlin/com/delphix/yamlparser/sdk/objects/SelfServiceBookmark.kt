/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
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
    val expiration: String,
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
                node.get("expiration").toString(),
                node.get("creationTime").toString()
            )
            return selfServiceBookmark
        }
    }
}
