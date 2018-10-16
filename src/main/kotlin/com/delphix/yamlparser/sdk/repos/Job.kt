/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
 */

package com.delphix.yamlparser.sdk.repos

import com.delphix.yamlparser.sdk.Http as Http
import com.delphix.yamlparser.sdk.objects.Job as JobObj
import org.json.JSONObject

class Job (
    var http: Http
) {
    val resource: String = "/resources/json/delphix/job"

    fun get(ref: String): JobObj {
        val response = http.handleGet("$resource/$ref")
        val job = JobObj.fromJson(response.getJSONObject("result"))
        return job
    }
}
