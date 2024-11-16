package com.example

import TaskManager

fun main() {
    val taskManager = TaskManager("src/main/resources/tasks.json")
    var isRunning = true

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
                print("Enter task name: ")
                val taskName = readLine().orEmpty()
                taskManager.addTask(taskName)
            }
            2 -> {
                print("Enter task ID to remove: ")
                val taskId = readLine()?.toIntOrNull()
                if (taskId != null) {
                    taskManager.removeTask(taskId)
                } else {
                    println("Invalid task ID.")
                }
            }
            3 -> taskManager.listTasks()
            4 -> {
                print("Enter task ID to complete: ")
                val taskId = readLine()?.toIntOrNull()
                if (taskId != null) {
                    taskManager.completeTask(taskId)
                } else {
                    println("Invalid task ID.")
                }
            }
            5 -> {
                println("Exiting...")
                isRunning = false
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}
