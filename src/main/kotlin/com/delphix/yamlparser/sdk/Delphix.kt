/**
 * Copyright (c) 2018 by Delphix. All rights reserved. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.delphix.yamlparser.sdk

import com.delphix.yamlparser.sdk.repos.Action as Action
import com.delphix.yamlparser.sdk.repos.Job as Job
import com.delphix.yamlparser.sdk.repos.SelfServiceContainer as SelfServiceContainer
import com.delphix.yamlparser.sdk.repos.SelfServiceBookmark as SelfServiceBookmark
import com.delphix.yamlparser.sdk.repos.Database as Database
import com.delphix.yamlparser.sdk.repos.Group as Group
import com.delphix.yamlparser.sdk.repos.Repository as Repository
import org.json.JSONObject

open class Delphix (
    var http: Http
){
    val loginResource: String = "/resources/json/delphix/login"

    fun requestLogin(username: String, password: String): Map<String, String> {
        return mapOf("type" to "LoginRequest", "username" to username, "password" to password)
    }

    open fun login(username: String, password: String) {
        http.setSession()
        http.handlePost(loginResource, requestLogin(username, password))
    }

    fun action(): Action {
        return Action(http)
    }

    fun job(): Job {
        return Job(http)
    }

    fun selfServiceContainer(): SelfServiceContainer {
        return SelfServiceContainer(http)
    }

    fun selfServiceBookmark(): SelfServiceBookmark {
        return SelfServiceBookmark(http)
    }

    fun database(): Database {
        return Database(http)
    }

    fun group(): Group {
      return Group(http)
    }

    fun repository(): Repository {
      return Repository(http)
    }
}
