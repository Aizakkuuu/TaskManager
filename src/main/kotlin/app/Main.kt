package app

import app.services.StorageService
import app.services.TaskService
import app.views.TaskCLI

fun main() {
    val storageService = StorageService()
    val taskService = TaskService(storageService)
    val taskCLI = TaskCLI(taskService)

    taskCLI.start()
}