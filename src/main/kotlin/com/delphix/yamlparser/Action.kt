package com.delphix.yamlparser

import com.fasterxml.jackson.databind.JsonNode;

data class Action (
    val event: String,
    val action: String
){
    companion object {
        @JvmStatic
        fun mapFromNode(node: JsonNode): Action {
            val name = Mapper().getNodeName(node)
            val action = Action(
                "$name",
                node["$name"].asText()
            )
            return action
        }
    }
}
