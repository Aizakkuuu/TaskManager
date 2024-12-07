package app.views

import app.services.TaskService

class TaskCLI(private val taskService: TaskService) {
    fun start() {
        while (true) {
            printMenu()
            when (readLine()?.trim()) {
                "1" -> createTask()
                "2" -> listTasks()
                "3" -> completeTask()
                "4" -> deleteTask()
                "5" -> break
                else -> println("Invalid option. Please try again.")
            }
        }
    }

    private fun printMenu() {
        println("\n--- Task Manager ---")
        println("1. Create Task")
        println("2. List Tasks")
        println("3. Complete Task")
        println("4. Delete Task")
        println("5. Exit")
        print("Choose an option: ")
    }

    private fun createTask() {
        println("\n")
        print("Enter task title: ")
        val title = readLine() ?: return

        print("Enter task description (optional): ")
        val description = readLine() ?: ""

        val task = taskService.createTask(title, description)
        println("Task created: ${task.title} (ID: ${task.id})")
    }

    private fun listTasks() {
        println("\n--- All Tasks ---")
        val tasks = taskService.listTasks(showCompleted = true)

        if (tasks.isEmpty()) {
            println("No tasks found.")
        } else {
            tasks.forEach { task ->
                val status = if (task.isCompleted) "[Completed]" else "[Pending]"
                println("ID: ${task.id}. ${task.title} $status")
            }
        }
        println("\nPress Enter to continue...")
        readLine()
    }

    private fun completeTask() {
        println("\n")
        print("Enter task ID to mark as complete: ")
        val id = readLine()?.toIntOrNull() ?: return

        val completedTask = taskService.completeTask(id)
        if (completedTask != null) {
            println("Task marked as completed: ${completedTask.title}")
        } else {
            println("Task not found.")
        }
    }

    private fun deleteTask() {
        println("\n")
        print("Enter task ID to delete: ")
        val id = readLine()?.toIntOrNull() ?: return

        val deleted = taskService.deleteTask(id)
        if (deleted) {
            println("Task deleted successfully!")
        } else {
            println("Task not found.")
        }
    }
}