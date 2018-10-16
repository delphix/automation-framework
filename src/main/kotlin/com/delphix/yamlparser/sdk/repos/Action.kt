/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
 */

package com.delphix.yamlparser.sdk.repos

import com.delphix.yamlparser.sdk.Http as Http
import com.delphix.yamlparser.sdk.objects.Action as ActionObj
import org.json.JSONObject

class Action (
    var http: Http
) {
    val resource: String = "/resources/json/delphix/action"

    fun get(ref: String): ActionObj {
        val response = http.handleGet("$resource/$ref")
        val action = ActionObj.fromJson(response.getJSONObject("result"))
        return action
    }
}
