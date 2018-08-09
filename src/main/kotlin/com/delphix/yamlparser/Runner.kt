package com.delphix.yamlparser

import com.delphix.yamlparser.sdk.Delphix as Delphix
import com.delphix.yamlparser.sdk.objects.Action as Action
import com.delphix.yamlparser.sdk.objects.Job as Job
import org.json.JSONObject

class Runner (
    val yaml: Yaml,
    val env: Map<String, String>,
    val delphix: Delphix
) {
    var currentAction: JSONObject = JSONObject()

    fun callDelphix(datapod: String, event: String) {
        delphix.login(env["delphixUser"]?: "", env["delphixPass"]?: "")
        when (event){
            "bookmark.create" -> println(datapod)
            "bookmark.share" -> println(datapod)
            "datapod.create" -> println(datapod)
            "datapod.delete" -> println(datapod)
            "datapod.refresh" -> currentAction = delphix.selfServiceContainer().refresh(datapod)
            "datapod.undo" -> println(datapod)
        }
    }

    fun runActions(environment: Environment) {
        for (action in environment.actions) {
            if (action.event == env["gitEvent"]) callDelphix(environment.datapod, action.action)
            println(currentAction)
            val actionObj: Action = delphix.action().get(currentAction.getString("action"))
            if (actionObj.state == "COMPLETED") {
                println(actionObj)
            } else {
                val job: Job = delphix.job().get(currentAction.getString("job"))
                println(job)
            }
        }
    }

    fun run() {
        for(environment in yaml.environments) {
            if (environment.branch == env["gitBranch"]) runActions(environment)
        }
    }
}
