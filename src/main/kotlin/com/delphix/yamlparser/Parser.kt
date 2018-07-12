package com.delphix.yamlparser

import com.delphix.yamlparser.Yaml
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

object Parser {

    fun delphixYamlExists(file: File) {
        var fileExists = file.exists()
        if(!fileExists){
            throw NoSuchFileException(file)
        }
    }

    fun loadFromFile(file: File): JsonNode {
        val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule());
        return mapper.readTree(file.readText());
    }

    @JvmStatic
    fun main(args : Array<String>) {
        val file = File("delphix.yaml")

        delphixYamlExists(file)
        val contents = loadFromFile(file)

        println(contents.get("template"));
    }

}
