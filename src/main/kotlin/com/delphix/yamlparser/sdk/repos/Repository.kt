/**
 * Copyright (c) 2018 by Delphix. All rights reserved.
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

    fun getRefByName(name: String): RepoObj {
        val repositories: List<RepoObj> = list()
        for (repository in repositories) {
            if (repository.name == name) return repository
        }
        throw IllegalArgumentException("Repo '$name' does not exist.")
    }
}
