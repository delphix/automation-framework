package com.delphix.yamlparser

import com.delphix.yamlparser.sdk.Delphix as Delphix
import com.delphix.yamlparser.sdk.Api as Api

import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

object Parser {

    fun delphixYamlExists(file: File) {
        if(!file.exists()){
            throw NoSuchFileException(file)
        }
    }

    fun loadFromFile(file: File): JsonNode {
        val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule());
        return mapper.readTree(file.readText())
    }

    fun loadEnvs(): Map<String, String>  {
        val gitBranch: String = System.getenv("GIT_BRANCH") ?: throw IllegalArgumentException("GIT_BRANCH Environment Variable Required.")
        val gitCommit: String = System.getenv("GIT_COMMIT") ?: throw IllegalArgumentException("GIT_COMMIT Environment Variable Required.")
        val gitEvent: String = System.getenv("GIT_EVENT") ?: throw IllegalArgumentException("GIT_EVENT Environment Variable Required.")
        val delphixEngine: String = System.getenv("DELPHIX_ENGINE") ?: throw IllegalArgumentException("DELPHIX_ENGINE Environment Variable Required.")
        val delphixUser: String = System.getenv("DELPHIX_USER") ?: throw IllegalArgumentException("DELPHIX_USER Environment Variable Required.")
        val delphixPass: String = System.getenv("DELPHIX_PASS") ?: throw IllegalArgumentException("DELPHIX_PASS Environment Variable Required.")
        return mapOf("gitBranch" to gitBranch, "gitCommit" to gitCommit, "gitEvent" to gitEvent, "delphixEngine" to delphixEngine, "delphixUser" to delphixUser, "delphixPass" to delphixPass)
    }

    @JvmStatic
    fun main(args : Array<String>) {
        val file = File("delphix.yaml")
        delphixYamlExists(file)
        val contents = loadFromFile(file)

        Validator(contents).validate()

        val yaml: Yaml = Mapper().mapYaml(contents)
        val env: Map<String, String> = loadEnvs()
        val delphix: Delphix = Delphix(Api(env["delphixEngine"]?: ""))
        
        val runner: Runner = Runner(yaml, env, delphix)
        runner.run()
    }

}
