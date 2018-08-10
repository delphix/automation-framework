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

    fun outputStatus(environment: String, event: String) {
        var actionObj: Action = delphix.action().get(currentAction.getString("action"))
        if (actionObj.state == "COMPLETED") {
            println("$environment - $event: COMPLETED")
        } else {
            var job: Job = delphix.job().get(currentAction.getString("job"))
            while (job.jobState == "RUNNING") {
                println("$environment - $event: " + job.percentComplete + "% COMPLETED")
                Thread.sleep(4000)
                job = delphix.job().get(currentAction.getString("job"))
            }
            println("$environment - $event: " + job.jobState)
        }
    }

    fun execActionPhase(environment: Environment) {
        for (action in environment.actions) {
            if (action.event == env["gitEvent"]) callDelphix(environment.datapod, action.action)
            outputStatus(environment.name, action.event)
        }
    }

    fun run() {
        for(environment in yaml.environments) {
            if (environment.branch == env["gitBranch"]) execActionPhase(environment)
        }
    }
}
