package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode;

data class DataSource(
    val name: String,
    val notes: String? = null,
    val replica: String? = "Default",
    val source: String?= null,
    val startOrder: Int?= null,
    val ami: String?= null
) {
    companion object {
        @JvmStatic
        fun mapFromNode(node: JsonNode): DataSource {
            val name = Mapper().getNodeName(node)
            val dataSource = DataSource(
                "$name",
                node["$name"]["notes"].asText(),
                node["$name"]["replica"].asText(),
                node["$name"]["source"].asText(),
                node["$name"]["start-order"].asInt(),
                node["$name"]["ami"].asText()
            )
            return dataSource
        }
    }
}
