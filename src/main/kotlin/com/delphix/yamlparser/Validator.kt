package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode

class Validator(val contents: JsonNode) {

    var currentPosition: String = ""
    var errors = mutableListOf<String>()

    fun getMessage(field:String): String {
        var message = "$currentPosition.$field"
        if (currentPosition.isEmpty()) {
            message = field
        }
        return message
    }

    fun missing(field: String) {
        errors.add(getMessage(field) + " is required. ")
    }

    fun empty(field: String) {
        errors.add(getMessage(field) + " can not be empty. ")
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

    fun environments(environments: JsonNode) {
        for(environment in environments){
            val name = Mapper().getNodeName(environment)
            currentPosition = "environments.$name"
            field(environment["$name"], "branch")
            field(environment["$name"], "datapod")
            node(environment["$name"], "when")
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
        node(contents, "config")
        node(contents, "connectors")
        node(contents, "environments")

        currentPosition = "config"
        node(contents["config"], "data-sources")

        connectors(contents["connectors"])
        environments(contents["environments"])

        if(errors.count() > 0) {
            renderErrors()
        }
    }

}
