package com.delphix.yamlparser

data class Config(
  val notes: String? = null,
  val dataSources: List<DataSource>
)
