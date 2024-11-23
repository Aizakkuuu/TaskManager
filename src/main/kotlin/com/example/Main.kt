package com.example

import com.example.services.UpdateTaskService
import com.example.services.TaskService

val filePath = "src/main/resources/tasks.json"
val updateTaskService = UpdateTaskService(filePath)
val taskService = TaskService(updateTaskService)
var isRunning = true

fun main() {

    while (isRunning) {
        println("\nTask Manager")
        println("1. Add a task")
        println("2. Remove a task")
        println("3. List tasks")
        println("4. Complete a task")
        println("5. Exit")
        print("Choose an option: \n")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                print("")
                print("Enter task name: ")
                val taskName = readLine().orEmpty()
                taskService.addTask(taskName)
                println("Task added successfully.")
            }
            2 -> {
                print("")
                listTasks()
                print("Enter task ID to remove: ")
                val taskId = readLine()?.toIntOrNull()
                if (taskId != null && taskService.removeTask(taskId)) {
                    println("Task removed successfully.")
                } else {
                    println("Task not found.")
                }
            }
            3 -> {
                println("")
                listTasks()
            }
            4 -> {
                print("")
                listTasks()
                print("Enter task ID to complete: ")
                val taskId = readLine()?.toIntOrNull()
                if (taskId != null && taskService.completeTask(taskId)) {
                    println("Task marked as completed.")
                } else {
                    println("Task not found.")
                }
            }
            5 -> {
                print("")
                println("Exiting...")
                isRunning = false
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}

//list the tasks stored
fun listTasks(){
    val tasks = taskService.listTasks()
    if (tasks.isEmpty()) {
        println("No tasks to display.")
    } else {
        tasks.forEach { task ->
            val status = if (task.isCompleted) "Completed" else "Pending"
            println("[${task.id}] ${task.name} - $status")
        }
    }
}
