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
import com.delphix.yamlparser.sdk.objects.SelfServiceBookmark as SelfServiceBookmarkObj
import com.delphix.yamlparser.sdk.repos.SelfServiceContainer as SelfServiceContainer
import com.delphix.yamlparser.sdk.objects.SelfServiceContainer as SelfServiceContainerObj
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class SelfServiceBookmark (
    var api: Api
) {
    val resource: String = "/resources/json/delphix/jetstream/bookmark"

    fun list(): List<SelfServiceBookmarkObj> {
        var bookmarks = mutableListOf<SelfServiceBookmarkObj>()
        val response = api.handleGet(resource).getJSONArray("result")
        for (i in 0 until response.length()) {
            val bookmark = response.getJSONObject(i);
            bookmarks.add(SelfServiceBookmarkObj.fromJson(bookmark))
        }
        return bookmarks
    }

    fun getRefByName(name: String): String {
        val bookmarks: List<SelfServiceBookmarkObj> = list()
        for (bookmark in bookmarks) {
            if (bookmark.name == name) return bookmark.reference
        }
        throw IllegalArgumentException("Self Service Container '$name' does not exist.")
    }

    fun create(name: String): JSONObject {
        val container: SelfServiceContainerObj = SelfServiceContainer(api).getContainerByName(name)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        val bookmark = mapOf("type" to "JSBookmark", "name" to "DAF: $formatted", "branch" to container.activeBranch)
        val timeline = mapOf("type" to "JSTimelinePointLatestTimeInput", "sourceDataLayout" to container.reference)
        val request = mapOf("type" to "JSBookmarkCreateParameters", "bookmark" to bookmark, "timelinePointParameters" to timeline)
        return api.handlePost("$resource", request)
    }
}
