package com.delphix.yamlparser

import com.delphix.yamlparser.sdk.Delphix as Delphix
import com.delphix.yamlparser.sdk.Http as Http

import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

object Parser {

    fun fileExists(file: File) {
        if(!file.exists()){
            throw NoSuchFileException(file)
        }
    }

    fun loadYamlFromFile(file: File): JsonNode {
        val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule())
        return mapper.readTree(file.readText())
    }

    fun loadJsonFromFile(file: File): JsonNode {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        return mapper.readTree(file.readText())
    }

    fun loadEventFromPayload(): String {
        val payloadFile = File("payload.json")
        try {
            fileExists(payloadFile)
        } catch (e: NoSuchFileException) {
            System.err.println(e.message + " is required.")
            System.exit(0)
        }
        val payload = loadJsonFromFile(payloadFile)
        var event: String = payload["action"]?.textValue() ?: "push"
        if (event == "opened" || event == "closed") event = "pull-request-$event"
        return event
    }

    fun loadEnvs(): Map<String, String>  {
        val gitBranch: String = System.getenv("GIT_BRANCH") ?: throw IllegalArgumentException("GIT_BRANCH Environment Variable Required.")
        val gitCommit: String = System.getenv("GIT_COMMIT") ?: throw IllegalArgumentException("GIT_COMMIT Environment Variable Required.")
        val gitEvent = System.getenv("GIT_EVENT") ?: loadEventFromPayload()
        val delphixEngine: String = System.getenv("DELPHIX_ENGINE") ?: throw IllegalArgumentException("DELPHIX_ENGINE Environment Variable Required.")
        val delphixUser: String = System.getenv("DELPHIX_USER") ?: throw IllegalArgumentException("DELPHIX_USER Environment Variable Required.")
        val delphixPass: String = System.getenv("DELPHIX_PASS") ?: throw IllegalArgumentException("DELPHIX_PASS Environment Variable Required.")
        return mapOf(
          "gitBranch" to gitBranch,
          "gitCommit" to gitCommit,
          "gitEvent" to gitEvent,
          "delphixEngine" to delphixEngine,
          "delphixUser" to delphixUser,
          "delphixPass" to delphixPass,
          "delphixRepository" to "Postgres vFiles (9.6.8)"
        )
    }

    @JvmStatic
    fun main(args : Array<String>) {
        val file = File("delphix.yaml")
        try {
            fileExists(file)
        } catch (e: NoSuchFileException) {
            System.err.println(e.message + " is required.")
            System.exit(0)
        }

        val contents = loadYamlFromFile(file)
        try {
            Validator(contents).validate()
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message)
            System.exit(0)
        }

        val env: Map<String, String> = loadEnvs()
        val delphix: Delphix = Delphix(Http(env["delphixEngine"]?: ""))
        val yaml: Yaml = Mapper().mapYaml(contents)
        val runner: Runner = Runner(yaml, env, delphix)

        try {
            runner.run()
        }
        catch (e: Exception) {
            System.err.println(e.message)
            System.exit(0)
        }
        
    }
}
