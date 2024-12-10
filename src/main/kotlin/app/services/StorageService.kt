package app.services

import kotlinx.serialization.json.Json
import app.models.Task
import kotlinx.serialization.encodeToString
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class StorageService(
    private val fileName: String = "tasks.json"
) {
    private val resourcesDir = File(System.getProperty("user.dir"), "src/main/resources")
    private val filePath = File(resourcesDir, fileName).absolutePath

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    init {
        resourcesDir.mkdirs()
    }

    fun saveTasks(tasks: List<Task>): Boolean {
        // Prevent duplicate IDs
        val uniqueTasks = tasks.distinctBy { it.id }
        // Ensure the number of unique tasks matches original tasks
        if (uniqueTasks.size != tasks.size) {
            println("Error: Duplicate task IDs detected. Saving only unique tasks.")
        }
        return try {
            val file = File(filePath)
            file.writeText(json.encodeToString(uniqueTasks))
            // Verify file was created and is not empty
            Files.exists(Path(filePath)) && file.length() > 0
        } catch (e: Exception) {
            println("Error saving tasks: ${e.message}")
            false
        }
    }

    fun loadTasks(): MutableList<Task> {
        val file = File(filePath)
        // Return empty list if file doesn't exist
        return try {
            val fileContent = file.readText()
            json.decodeFromString<List<Task>>(fileContent).toMutableList()
        } catch (e: Exception) {
            println("Error loading tasks: ${e.message}")
            println("File may be corrupted. Resetting to empty list.")
            file.writeText("[]")
            mutableListOf()
        }
    }
}