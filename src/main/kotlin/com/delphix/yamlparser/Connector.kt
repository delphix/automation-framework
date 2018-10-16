package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode;

data class Connector(
    val name: String,
    val host: String,
    val port: String,
    val database: String,
    val username: String,
    val password: String
) {
    companion object {
        @JvmStatic
        fun mapFromNode(node: JsonNode): Connector {
            val name = Mapper().getNodeName(node)
            val connector = Connector(
                "$name",
                node["$name"]["host"].asText(),
                node["$name"]["port"].asText(),
                node["$name"]["database"].asText(),
                node["$name"]["username"].asText(),
                node["$name"]["password"].asText()
            )
            return connector
        }
    }
}
