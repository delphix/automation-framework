package com.delphix.yamlparser.sdk

import com.delphix.yamlparser.sdk.repos.Action as Action
import com.delphix.yamlparser.sdk.repos.Job as Job
import com.delphix.yamlparser.sdk.repos.SelfServiceContainer as SelfServiceContainer
import com.delphix.yamlparser.sdk.repos.SelfServiceBookmark as SelfServiceBookmark
import org.json.JSONObject

open class Delphix (
    var api: Api
){
    val loginResource: String = "/resources/json/delphix/login"

    fun requestLogin(username: String, password: String): Map<String, String> {
        return mapOf("type" to "LoginRequest", "username" to username, "password" to password)
    }

    open fun login(username: String, password: String) {
        api.setSession()
        api.handlePost(loginResource, requestLogin(username, password))
    }

    fun action(): Action {
        return Action(api)
    }

    fun job(): Job {
        return Job(api)
    }

    fun selfServiceContainer(): SelfServiceContainer {
        return SelfServiceContainer(api)
    }

    fun selfServiceBookmark(): SelfServiceBookmark {
        return SelfServiceBookmark(api)
    }
}
