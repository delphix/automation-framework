package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode;

data class Environment(
  val name: String,
  val branch: String,
  val datapod: String,
  val actions: List<Action>
) {
    companion object {
        @JvmStatic
        fun mapFromNode(node: JsonNode, actionList: MutableList<Action>): Environment {
            val name = Mapper().getNodeName(node)
            val environment = Environment(
                "$name",
                node["$name"]["branch"].asText(),
                node["$name"]["datapod"].asText(),
                actionList
            )
            return environment
        }
    }
}
