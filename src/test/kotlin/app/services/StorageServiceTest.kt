package app.services

import app.models.Task
import app.services.StorageService
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.*
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StorageServiceTest {

    private val testFileName = "test_tasks.json"
    private lateinit var storageService: StorageService
    private val testTasks = listOf(
        Task(id = 1, title = "Task 1", description = "First task", isCompleted = false),
        Task(id = 2, title = "Task 2", description = "Second task", isCompleted = true)
    )

    @BeforeEach
    fun setup() {
        storageService = StorageService(testFileName)
        // Ensure the test file is clean before each test
        File(testFileName).delete()
    }

    @AfterEach
    fun cleanup() {
        // Clean up after each test
        File(testFileName).delete()
    }

    @Test
    fun `saveTasks should save tasks to file`() {
        storageService.saveTasks(testTasks)

        val savedFile = File(testFileName)
        Assertions.assertTrue(savedFile.exists(), "File should exist after saving tasks")
        Assertions.assertTrue(savedFile.readText().isNotBlank(), "File should not be empty after saving tasks")
    }

    @Test
    fun `same Task ID should not save`(){
        // Create a list with duplicate IDs
        val tasksWithDuplicates = listOf(
            Task(id = 1, title = "Task 1", description = "First task", isCompleted = false),
            Task(id = 1, title = "Duplicate Task", description = "Duplicate ID task", isCompleted = true),
            Task(id = 2, title = "Task 2", description = "Second task", isCompleted = false)
        )

        val tasksWithoutDuplicates = listOf(
            Task(id = 1, title = "Task 1", description = "First task", isCompleted = false),
            Task(id = 2, title = "Task 2", description = "Second task", isCompleted = false)
        )

        storageService.saveTasks(tasksWithDuplicates)

        val loadedTasks = storageService.loadTasks()
        assertEquals(tasksWithoutDuplicates, loadedTasks, "Duplicate ids should not be saved")
    }

    @Test
    fun `loadTasks should load tasks from file`() {
        storageService.saveTasks(testTasks)

        val loadedTasks = storageService.loadTasks()
        assertEquals(testTasks, loadedTasks, "Loaded tasks should match the saved tasks")
    }

    @Test
    fun `loadTasks should return empty list when file does not exist`() {
        val loadedTasks = storageService.loadTasks()
        Assertions.assertTrue(loadedTasks.isEmpty(), "Loading tasks from a non-existent file should return an empty list")
    }

    @Test
    fun `loadTasks should handle exceptions gracefully`() {
        // Simulate a corrupted file scenario
        val corruptedFile = File(testFileName)
        corruptedFile.writeText("corrupted content")

        val loadedTasks = storageService.loadTasks()
        Assertions.assertTrue(loadedTasks.isEmpty(), "Loading tasks from a corrupted file should return an empty list")
    }
}
