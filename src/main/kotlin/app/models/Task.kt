package app.models

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    var title: String,
    var description: String = "",
    var isCompleted: Boolean = false
)