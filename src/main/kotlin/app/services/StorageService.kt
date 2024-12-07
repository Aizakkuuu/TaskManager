package app.services

import kotlinx.serialization.json.Json
import app.models.Task
import kotlinx.serialization.encodeToString
import java.io.File

class StorageService(
    private val fileName: String = "tasks.json"
) {
    private val storageDir = File(
        StorageService::class.java.classLoader.getResource("")?.path
            ?: (System.getProperty("user.dir") + "/src/main/resources")
    )

    private val filePath = File(storageDir, fileName).absolutePath

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun saveTasks(tasks: List<Task>) {
        val file = File(filePath)
        file.writeText(json.encodeToString(tasks))
        println("Tasks saved to: $filePath")
    }

    fun loadTasks(): MutableList<Task> {
        val file = File(filePath)
        return try {
            if (file.exists()) {
                json.decodeFromString(file.readText())
            } else {
                mutableListOf()
            }
        } catch (e: Exception) {
            println("Error loading tasks from $filePath: ${e.message}")
            mutableListOf()
        }
    }
}