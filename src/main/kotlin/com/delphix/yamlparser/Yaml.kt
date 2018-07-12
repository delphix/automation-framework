package com.delphix.yamlparser

data class Yaml(
  val template: String,
  val api_key: String,
  val ami: String,
  val environments: List<Environment>
)
