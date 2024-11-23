package com.example.services

import com.example2.Task

class TaskService(private val updateTaskService: UpdateTaskService) {
    private var nextId = initializeNextId()

    private fun initializeNextId(): Int {
        val tasks = updateTaskService.getTasks()
        return tasks.maxOfOrNull { it.id }?.plus(1) ?: 1
    }

    fun addTask(taskName: String) {
        val task = Task(nextId++, taskName)
        updateTaskService.addTask(task)
        println("Task added successfully.")
    }

    fun removeTask(taskId: Int): Boolean {
        val tasks = updateTaskService.getTasks().toMutableList()
        val taskToRemove = tasks.find { it.id == taskId }
        if (taskToRemove != null) {
            tasks.remove(taskToRemove)
            updateTaskService.saveTasks(tasks)
            // Recalculate nextId based on remaining tasks
            nextId = tasks.maxOfOrNull { it.id }?.plus(1) ?: 1
            println("Task removed successfully.")
            return true
        }
        println("Task not found.")
        return false
    }

    fun listTasks(): List<Task> {
        return updateTaskService.getTasks().also { tasks ->
            if (tasks.isEmpty()) {
                println("No tasks to display.")
            } else {
                tasks.forEach { task ->
                    val status = if (task.isCompleted) "Completed" else "Pending"
                    println("[${task.id}] ${task.name} - $status")
                }
            }
        }
    }

    fun completeTask(taskId: Int): Boolean {
        val tasks = updateTaskService.getTasks()
        val task = tasks.find { it.id == taskId }
        if (task != null) {
            val completedTask = task.copy(isCompleted = true)
            return if (updateTaskService.updateTask(completedTask)) {
                println("Task marked as completed.")
                true
            } else {
                println("Failed to update task.")
                false
            }
        }
        println("Task not found.")
        return false
    }
}