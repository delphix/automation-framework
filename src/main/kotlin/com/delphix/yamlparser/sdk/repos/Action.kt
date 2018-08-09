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
import com.delphix.yamlparser.sdk.objects.Action as ActionObj
import org.json.JSONObject

class Action (
    var api: Api
) {
    val resource: String = "/resources/json/delphix/action"

    fun get(ref: String): ActionObj {
        val response = api.handleGet("$resource/$ref")
        val action = ActionObj.fromJson(response.getJSONObject("result"))
        return action
    }
}
