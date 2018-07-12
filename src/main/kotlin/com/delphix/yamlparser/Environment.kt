package com.delphix.yamlparser

data class Environment(
  val name: String,
  val branch: String,
  val datapod: String,
  val ami: String,
  val actions: List<Action>? = null
)
