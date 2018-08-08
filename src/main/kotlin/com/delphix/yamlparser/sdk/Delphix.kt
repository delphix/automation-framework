package com.delphix.yamlparser.sdk

import com.delphix.yamlparser.sdk.repos.SelfServiceContainer as SelfServiceContainer
import org.json.JSONObject

class Delphix (
    var api: Api
){
    val loginResource: String = "/resources/json/delphix/login"
    
    fun requestLogin(username: String, password: String): Map<String, String> {
        return mapOf("type" to "LoginRequest", "username" to username, "password" to password)
    }

    fun login(username: String, password: String) {
        api.setSession()
        api.handlePost(loginResource, requestLogin(username, password))
    }

    fun selfServiceContainer(): SelfServiceContainer {
        return SelfServiceContainer(api)
    }
}
