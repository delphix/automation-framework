package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode

class Mapper {

    fun mapConnectors(connectors: JsonNode): MutableList<Connector> {
        var connectorsList = mutableListOf<Connector>()
        for (connector in connectors) {
            connectorsList.add(Connector.mapFromNode(connector))
        }
        return connectorsList
    }

    fun mapEnvironments(environments: JsonNode): MutableList<Environment> {
        var environmentsList = mutableListOf<Environment>()
        for (environment in environments) {
            var actionsList = mutableListOf<Action>()
            val name = Mapper().getNodeName(environment)
            for(action in environment["$name"]["when"]) {
                actionsList.add(Action.mapFromNode(action))
            }
            environmentsList.add(Environment.mapFromNode(environment, actionsList))
        }
        return environmentsList
    }

    fun mapDataSources(dataSources: JsonNode): MutableList<DataSource> {
        var dataSourcesList = mutableListOf<DataSource>()
        for (dataSource in dataSources) {
            dataSourcesList.add(DataSource.mapFromNode(dataSource))
        }
        return dataSourcesList
    }

    fun getNodeName(node: JsonNode): String {
        val it: Iterator<String> = node.fieldNames()
        var name: String = ""
        while (it.hasNext()) {
            name = it.next()
        }
        return name
    }

    fun mapConfig(config: JsonNode) : Config {
        return Config(
            config["notes"].asText(),
            mapDataSources(config["data-sources"])
        )
    }

    fun mapYaml(contents: JsonNode): Yaml {

        val config = contents["config"] ?: null
        var mappedConfig = null
        if(config is JsonNode) {
            var mappedConfig: Config = mapConfig(contents["config"])
        }

        val connector = contents["connectors"] ?: null
        var mappedConnector = null
        if(connector is JsonNode) {
            var mappedConnector: List<Connector> = mapConnectors(contents["connectors"])
        }

        return Yaml(
            contents.get("template").asText(),
            contents.get("parent").asText(),
            contents.get("api_key").asText(),
            mappedConfig,
            mappedConnector,
            mapEnvironments(contents["environments"])
        );
    }

}
