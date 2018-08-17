package com.delphix.yamlparser

data class Yaml(
  val template: String,
  val api_key: String,
  val config: Config? = null,
  val connectors: List<Connector>? = null,
  val environments: List<Environment>
)
