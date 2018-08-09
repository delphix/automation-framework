package com.delphix.yamlparser

import com.delphix.yamlparser.sdk.Delphix as Delphix

class Runner (
    val yaml: Yaml,
    val env: Map<String, String>,
    val delphix: Delphix
) {

    fun callDelphix(datapod: String, event: String) {
        delphix.login(env["delphixUser"]?: "", env["delphixPass"]?: "")
        when (event){
            "datapod.refresh" -> delphix.selfServiceContainer().refresh(datapod)
            "datapod.create" -> println(datapod)
            "bookmark.share" -> println(datapod)
            "datapod.delete" -> println(datapod)
            "bookmark.create" -> println(datapod)
            "datapod.undo" -> println(datapod)
        }
    }

    fun runActions(environment: Environment) {
        for (action in environment.actions) {
            if (action.event == env["gitEvent"]) callDelphix(environment.datapod, action.action)
        }
    }

    fun run() {
        for(environment in yaml.environments) {
            if (environment.branch == env["gitBranch"]) runActions(environment)
        }
    }
}
