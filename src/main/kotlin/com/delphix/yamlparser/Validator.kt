package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode

class Validator(val contents: JsonNode) {

    var currentPosition: String = ""
    var errors = mutableListOf<String>()
    val allowedEvents = listOf<String>("push", "pull-request-opened", "pull-request-closed", "build-failure", "bookmark-complete")
    val allowedActions = listOf<String>("datapod.refresh", "datapod.create", "bookmark.share", "datapod.delete", "bookmark.create", "datapod.undo")

    fun getMessage(field:String): String {
        var message = "$currentPosition.$field"
        if (currentPosition.isEmpty()) {
            message = field
        }
        return message
    }

    fun missing(field: String) {
        errors.add(getMessage(field) + " is required.")
    }

    fun empty(field: String) {
        errors.add(getMessage(field) + " can not be empty.")
    }

    fun invalid(field: String) {
        errors.add(getMessage(field) + " is not a valid value.")
    }

    fun node(node: JsonNode, name: String): Any {
        return node[name] ?: missing(name)
    }

    fun field(node: JsonNode, name: String) {
        val field = node(node, name)
        if (field !is JsonNode) return
        if (field.isNull()) {
            empty(name)
        }
    }

    fun dataSources(dataSources: JsonNode) {
        for(dataSource in dataSources) {
            val name = Mapper().getNodeName(dataSource)
            currentPosition = "config.data-sources.$name"
            field(dataSource["$name"], "ami")
        }
    }

    fun connectors(connectors: JsonNode) {
        for(connector in connectors) {
            val name = Mapper().getNodeName(connector)
            currentPosition = "connectors.$name"
            field(connector["$name"], "host")
            field(connector["$name"], "port")
            field(connector["$name"], "database")
            field(connector["$name"], "username")
            field(connector["$name"], "password")
        }
    }

    fun events(events: JsonNode) {
        for(event in events){
            val name = Mapper().getNodeName(event)
            currentPosition = "$currentPosition.$name"
            if (name !in allowedEvents) invalid(name)
            if (event.get("$name").asText() !in allowedActions) invalid(event.get("$name").asText())
        }
    }

    fun environments(environments: JsonNode) {
        for(environment in environments){
            val name = Mapper().getNodeName(environment)
            currentPosition = "environments.$name"
            if (name != "all") {
                field(environment["$name"], "branch")
                field(environment["$name"], "datapod")
            }
            node(environment["$name"], "when")
            if (environment["$name"]["when"].size() == 0) empty("when")
            events(environment["$name"]["when"])
        }
    }

    fun renderErrors(): Nothing {
        var message = ""
        for(error in errors){
            message = "$message $error"
        }
        throw IllegalArgumentException(message);
    }

    fun validate() {
        field(contents, "template")
        field(contents, "api_key")
        node(contents, "environments")
        environments(contents["environments"])

        //Config has requirements if present
        val config = contents["config"] ?: null
        if(config is JsonNode) {
            currentPosition = "config"
            node(contents["config"], "data-sources")
            dataSources(contents["config"]["data-sources"])
        }

        //Connectors have requirements if present
        val connector = contents["connectors"] ?: null
        if(connector is JsonNode) {
            connectors(contents["connectors"])
        }

        if (errors.count() > 0) renderErrors()
    }

}
