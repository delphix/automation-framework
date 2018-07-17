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
            environmentsList.add(Environment.mapFromNode(environment))
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

    fun mapConfig(config: JsonNode) : Config {
        return Config(
            config["notes"].asText(),
            mapDataSources(config["data-sources"])
        )
    }

    fun mapYaml(contents: JsonNode): Yaml {
        return Yaml(
            contents.get("template").asText(),
            contents.get("api_key").asText(),
            mapConfig(contents["config"]),
            mapConnectors(contents["connectors"]),
            mapEnvironments(contents["environments"])
        );
    }

}
