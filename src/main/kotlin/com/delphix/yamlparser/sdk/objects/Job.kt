/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
 */

package com.delphix.yamlparser.sdk.objects

import org.json.JSONObject

data class Job(
     val type: String,
     val reference: String,
     val namespace: String,
     val name: String,
     val actionType: String,
     val target: String,
     val targetObjectType: String,
     val jobState: String,
     val startTime: String,
     val updateTime: String,
     val suspendable: Boolean,
     val cancelable: Boolean,
     val queued: Boolean,
     val user: String,
     val emailAddresses: String,
     val title: String,
     val percentComplete: Int,
     val targetName: String,
     val parentActionState: String,
     val parentAction: String
){
    companion object {
        @JvmStatic
        fun fromJson(node: JSONObject): Job {
            val job = Job(
                node.get("type").toString(),
                node.get("reference").toString(),
                node.get("namespace").toString(),
                node.get("name").toString(),
                node.get("actionType").toString(),
                node.get("target").toString(),
                node.get("targetObjectType").toString(),
                node.get("jobState").toString(),
                node.get("startTime").toString(),
                node.get("updateTime").toString(),
                node.getBoolean("suspendable"),
                node.getBoolean("cancelable"),
                node.getBoolean("queued"),
                node.get("user").toString(),
                node.get("emailAddresses").toString(),
                node.get("title").toString(),
                node.getInt("percentComplete"),
                node.get("targetName").toString(),
                node.get("parentActionState").toString(),
                node.get("parentAction").toString()
            )
            return job
        }
    }
}
