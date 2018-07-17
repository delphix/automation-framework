package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode;

data class Environment(
  val name: String,
  val branch: String,
  val datapod: String,
  val ami: String?= null
) {
    companion object {
        @JvmStatic
        fun mapFromNode(node: JsonNode): Environment {
            val it: Iterator<String> = node.fieldNames()
            var name: String = ""
            while (it.hasNext()) {
                name = it.next()
            }
            val environment = Environment(
                "$name",
                node["$name"]["branch"]?.asText() ?: "",
                node["$name"]["datapod"]?.asText() ?: "",
                node["$name"]["ami"]?.asText()
            )
            return environment
        }
    }
}
