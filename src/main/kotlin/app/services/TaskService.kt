package app.services

import app.models.Task

class TaskService(private val storageService: StorageService) {
    private val tasks = storageService.loadTasks().toMutableList()
    private var nextId = tasks.maxOfOrNull { it.id }?.plus(1) ?: 1

    fun createTask(title: String, description: String = ""): Task {
        val newTask = Task(
            id = nextId,
            title = title,
            description = description
        )
        tasks.add(newTask)
        nextId++ // Increment for next task
        saveChanges()
        return newTask
    }

    fun listTasks(showCompleted: Boolean = false): List<Task> {
        return if (showCompleted) tasks else tasks.filter { !it.isCompleted }
    }

    fun completeTask(id: Int): Task? {
        val task = tasks.find { it.id == id } ?: return null

        task.isCompleted = true

        saveChanges()
        return task
    }

    fun deleteTask(id: Int): Boolean {
        val task = tasks.find { it.id == id }
        return if (task != null) {
            tasks.remove(task)
            saveChanges()
            true
        } else false
    }

    private fun saveChanges() {
        storageService.saveTasks(tasks)
    }
}