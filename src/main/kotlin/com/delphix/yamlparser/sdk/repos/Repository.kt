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

package com.delphix.yamlparser.sdk.repos

import com.delphix.yamlparser.sdk.Api as Api
import com.delphix.yamlparser.sdk.objects.Repository as RepoObj
import org.json.JSONObject

class Repository (
    var api: Api
) {
    val resource: String = "/resources/json/delphix/repository"

    fun list(): List<RepoObj> {
        var repositories = mutableListOf<RepoObj>()
        val response = api.handleGet(resource).getJSONArray("result")
        for (i in 0 until response.length()) {
            val repository = response.getJSONObject(i);
            repositories.add(RepoObj.fromJson(repository))
        }
        return repositories
    }

    fun getRepoByName(name: String): RepoObj {
        val repositories: List<RepoObj> = list()
        for (repository in repositories) {
            if (repository.name == name) return repository
        }
        throw IllegalArgumentException("Repo '$name' does not exist.")
    }
}
