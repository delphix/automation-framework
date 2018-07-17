package com.delphix.yamlparser

data class Yaml(
  val template: String,
  val api_key: String,
  val config: Config,
  val connectors: List<Connector>,
  val environments: List<Environment>
)
