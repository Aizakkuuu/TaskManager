package com.example.services

import com.example2.Task
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class UpdateTaskService(private val filePath: String) {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    fun addTask(task: Task) {
        val tasks = loadTasks() + task
        saveTasks(tasks)
    }

    fun removeTask(taskId: Int): Boolean {
        val tasks = loadTasks().toMutableList()
        val taskToRemove = tasks.find { it.id == taskId }
        if (taskToRemove != null) {
            tasks.remove(taskToRemove)
            saveTasks(tasks)
            return true
        }
        return false
    }

    fun updateTask(updatedTask: Task): Boolean {
        val tasks = loadTasks().toMutableList()
        val index = tasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            tasks[index] = updatedTask
            saveTasks(tasks)
            return true
        }
        return false
    }

    fun getTasks(): List<Task> = loadTasks()

    private fun loadTasks(): List<Task> {
        val file = File(filePath)
        return if (file.exists()) {
            val jsonString = file.readText()
            json.decodeFromString(jsonString)
        } else {
            emptyList()
        }
    }

    fun saveTasks(tasks: List<Task>) {
        val jsonString = json.encodeToString(tasks)
        File(filePath).writeText(jsonString)
    }
}
