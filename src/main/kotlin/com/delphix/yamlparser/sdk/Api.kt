package com.delphix.yamlparser.sdk

import khttp.get
import khttp.post
import khttp.responses.Response
import khttp.structures.cookie.CookieJar
import org.json.JSONObject

class Api(
    val engineAddress: String = "http://34.211.184.233",
    val versionMajor: Int = 1,
    val versionMinor: Int = 7,
    val versionMicro: Int = 0
){
    val sessionResource: String = "/resources/json/delphix/session"
    var sessionCookies: CookieJar = CookieJar()

    fun requestSessions(): Map<String, Any> {
        val version = mapOf("type" to "APIVersion", "major" to versionMajor, "minor" to versionMinor, "micro" to versionMicro)
        return mapOf("type" to "APISession", "version" to version)
    }

    fun setSession() {
        val r = post("$engineAddress$sessionResource", json = requestSessions())
        sessionCookies = r.cookies
    }

    fun validateResponse(response: JSONObject) {
        if (response.get("status") == "ERROR") {
            val error = response.getJSONObject("error")
            val details = error.get("details")
            val action = error.get("action")
            throw Exception("$details $action")
        }
    }

    fun handlePost(url: String, content: Map<String, Any>): JSONObject {
        val response =  post(
            "$engineAddress$url",
            json = content,
            cookies = sessionCookies
        )
        validateResponse(response.jsonObject)
        return response.jsonObject
    }

    fun handleGet(url: String): JSONObject {
        val response = get(
            "$engineAddress$url",
            cookies = sessionCookies
        )
        validateResponse(response.jsonObject)
        return response.jsonObject
    }

}
