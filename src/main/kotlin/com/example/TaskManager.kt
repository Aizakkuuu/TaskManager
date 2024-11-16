import com.example2.Task
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.io.InputStream

class TaskManager(private val filePath: String) {

    private val tasks = mutableListOf<Task>()
    private var nextId = 1

    init {
        loadTasks()
    }

    // Add a new task
    fun addTask(taskName: String) {
        tasks.add(Task(nextId++, taskName))
        saveTasks()
        println("Task added successfully.")
    }

    // Remove a task by ID
    fun removeTask(taskId: Int) {
        val task = tasks.find { it.id == taskId }
        if (task != null) {
            tasks.remove(task)
            saveTasks()
            println("Task removed successfully.")
        } else {
            println("Task not found.")
        }
    }

    // List all tasks
    fun listTasks() {
        if (tasks.isEmpty()) {
            println("No tasks to display.")
            return
        }
        tasks.forEach { task ->
            val status = if (task.isCompleted) "Completed" else "Pending"
            println("[${task.id}] ${task.name} - $status")
        }
    }

    // Mark a task as completed
    fun completeTask(taskId: Int) {
        val task = tasks.find { it.id == taskId }
        if (task != null) {
            task.isCompleted = true
            saveTasks()
            println("Task marked as completed.")
        } else {
            println("Task not found.")
        }
    }

    // Save tasks to JSON file
    private fun saveTasks() {
        val json = Json { prettyPrint = true }
        val jsonString = json.encodeToString(tasks)
        File(filePath).writeText(jsonString)
    }

    // Load tasks from resources folder
    private fun loadTasks() {
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream(filePath)

        if (inputStream != null) {
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val json = Json { ignoreUnknownKeys = true }
            tasks.clear()
            tasks.addAll(json.decodeFromString(jsonString))
            nextId = tasks.maxOfOrNull { it.id }?.plus(1) ?: 1
        } else {
            println("No existing tasks found, starting with an empty list.")
        }
    }
}