package com.delphix.yamlparser

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import io.github.cdimascio.dotenv.dotenv as Dotenv

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

    fun loadEnvs(env: String): Map<String, String>  {

        val dotenv = Dotenv {
            filename = "$env"
            ignoreIfMalformed = true
            ignoreIfMissing = true
        }

        val gitBranch: String = dotenv["GIT_BRANCH"] ?: throw IllegalArgumentException("GIT_BRANCH Environment Variable Required.")
        val gitCommit: String = dotenv["GIT_COMMIT"] ?: throw IllegalArgumentException("GIT_COMMIT Environment Variable Required.")
        val gitEvent = dotenv["GIT_EVENT"] ?: loadEventFromPayload()
        val delphixEngine: String = dotenv["DELPHIX_ENGINE"] ?: throw IllegalArgumentException("DELPHIX_ENGINE Environment Variable Required.")
        val delphixUser: String = dotenv["DELPHIX_USER"] ?: throw IllegalArgumentException("DELPHIX_USER Environment Variable Required.")
        val delphixPass: String = dotenv["DELPHIX_PASS"] ?: throw IllegalArgumentException("DELPHIX_PASS Environment Variable Required.")
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

    class Parse : CliktCommand() {
      val env: String by option(help="Path to env file.").default(".env")

      override fun run(){

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

        val env: Map<String, String> = loadEnvs(env)
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

    @JvmStatic
    fun main(args: Array<String>) = Parse().main(args)
}
