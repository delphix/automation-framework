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

    fun getBuildTag(environment: String): String {
      val commit =  env["gitCommit"]?: "BUILDTAG"
      return environment + "-" + commit.substring(0,6)
    }

    fun callDelphix(datapod: String, environment: String, event: String) {
        delphix.login(env["delphixUser"]?: "", env["delphixPass"]?: "")
        when (event){
            "bookmark.create" -> currentAction = delphix.selfServiceBookmark().create(getBuildTag(environment), datapod)
            "bookmark.share" -> currentAction = delphix.selfServiceBookmark().share(getBuildTag(environment))
            "bookmark.delete" -> currentAction = delphix.selfServiceBookmark().delete(getBuildTag(environment))
            "datapod.create" -> currentAction = delphix.database().provision(getBuildTag(environment), yaml.template, yaml.parent, env["delphixRepository"]?: "")
            "datapod.delete" -> currentAction = delphix.database().delete(getBuildTag(environment))
            "datapod.refresh" -> currentAction = delphix.selfServiceContainer().refresh(datapod)
            "datapod.undo" -> currentAction = delphix.selfServiceContainer().undo(datapod)
        }
    }

    fun outputStatus(environment: String, event: String, action: String) {
        var actionObj: Action = delphix.action().get(currentAction.getString("action"))
        if (actionObj.state == "COMPLETED") {
            println("-$environment: $event: $action - COMPLETED")
        } else {
            var job: Job = delphix.job().get(currentAction.getString("job"))
            var percent: Int = 0
            println("-$environment: $event: $action - " + percent + "% COMPLETED")
            while (job.jobState == "RUNNING") {
                if (percent !=  job.percentComplete) {
                    println("-$environment: $event: $action - " + job.percentComplete + "% COMPLETED")
                }
                percent = job.percentComplete
                Thread.sleep(4000)
                job = delphix.job().get(currentAction.getString("job"))
            }
            println("-$environment: $event: $action - " + job.jobState)
        }
    }

    fun execActionPhase(environment: Environment) {
        for (action in environment.actions) {
            if (action.event == env["gitEvent"]) {
                callDelphix(environment.datapod, environment.name, action.action)
                outputStatus(environment.name, action.event, action.action)
            } else {
                println("-${environment.name}: ${action.event}: Not Triggered.")
            }
        }
    }

    fun run() {
        for(environment in yaml.environments) {
            if (environment.branch == env["gitBranch"]) execActionPhase(environment)
        }
    }
}
