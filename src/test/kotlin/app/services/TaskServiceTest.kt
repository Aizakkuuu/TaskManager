package app.services

import app.models.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertNull

class TaskServiceTest {
    private lateinit var mockStorageService: StorageService
    private lateinit var taskService: TaskService

    private val initialTasks = mutableListOf(
        Task(id = 1, title = "Task 1", description = "Description 1", isCompleted = false),
        Task(id = 2, title = "Task 2", description = "Description 2", isCompleted = true)
    )

    @BeforeEach
    fun setup() {
        // Create a mock StorageService that returns our initial tasks
        mockStorageService = mockk(relaxed = true) {
            every { loadTasks() } returns initialTasks
        }

        // Create TaskService with the mock StorageService
        taskService = TaskService(mockStorageService)
    }

    @Test
    fun `createTask should add a new task with a unique ID`() {
        val newTask = taskService.createTask("New Task", "New Description")

        // Verify task properties
        assertEquals(3, newTask.id, "New task ID should be 3")
        assertEquals("New Task", newTask.title, "New task title should match")
        assertEquals("New Description", newTask.description, "New task description should match")
        assertFalse(newTask.isCompleted, "New task should not be completed initially")

        // Verify storage service was called to save tasks
        verify(exactly = 1) { mockStorageService.saveTasks(any()) }
    }

    @Test
    fun `listTasks should return all tasks by default`() {
        val tasks = taskService.listTasks()

        // Verify only incomplete tasks are returned
        assertEquals(1, tasks.size, "Should return only incomplete tasks")
        assertEquals(initialTasks.first(), tasks.first(), "Returned task should match the incomplete task")
    }

    @Test
    fun `listTasks with showCompleted true should return all tasks`() {
        val tasks = taskService.listTasks(showCompleted = true)

        // Verify all tasks are returned
        assertEquals(2, tasks.size, "Should return all tasks")
    }

    @Test
    fun `completeTask should mark the task as completed`() {
        val completedTask = taskService.completeTask(1)

        // Verify task completion
        assertNotNull(completedTask, "Completed task should not be null")
        assertEquals(1, completedTask?.id, "Completed task ID should be 1")
        assertTrue(completedTask?.isCompleted ?: false, "Task should be marked as completed")

        // Verify storage service was called to save tasks
        verify(exactly = 1) { mockStorageService.saveTasks(any()) }
    }

    @Test
    fun `completeTask with non-existent ID should return null`() {
        val result = taskService.completeTask(999)

        // Verify no task is completed
        assertNull(result, "Non-existent task should return null")
        verify(exactly = 0) { mockStorageService.saveTasks(any()) }
    }

    @Test
    fun `deleteTask should remove the task with the given ID`() {
        val result = taskService.deleteTask(1)

        // Verify task deletion
        assertTrue(result, "Delete task should return true")
        verify(exactly = 1) { mockStorageService.saveTasks(any()) }
    }

    @Test
    fun `deleteTask with non-existent ID should return false`() {
        val result = taskService.deleteTask(999)

        // Verify no deletion occurs
        assertFalse(result, "Delete task should return false for non-existent ID")
        verify(exactly = 0) { mockStorageService.saveTasks(any()) }
    }

    // Helper function to simulate a null check (added for completeness)
    private fun assertNotNull(value: Any?, message: String) {
        assert(value != null) { message }
    }
}