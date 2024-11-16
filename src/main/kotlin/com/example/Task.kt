package com.example2

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Int, val name: String, var isCompleted: Boolean = false)
