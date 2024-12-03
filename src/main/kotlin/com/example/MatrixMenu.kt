package com.example

import java.util.Scanner
import matrix.MatrixOperations
import utils.Utils

fun main() {
    val scanner = Scanner(System.`in`)
    while (true) {
        println("\nMenu:")
        println("1. Matrix Multiplication")
        println("2. Gauss-Jordan Elimination")
        println("3. Exit")
        print("Enter your choice: ")

        when (scanner.nextInt()) {
            1 -> {
                println("Matrix Multiplication:")
                val (matrixA, matrixB) = Utils.inputMatrices(scanner)
                val result = MatrixOperationsService.multiplyMatrices(matrixA, matrixB)
                println("Result of Matrix Multiplication:")
                MatrixUtilityService.printMatrix(result)
            }
            2 -> {
                println("Gauss-Jordan Elimination:")
                println("Enter the augmented matrix:")
                val matrix = Utils.inputMatrix(scanner)
                println("Original Matrix:")
                Utils.printMatrix(matrix)
                MatrixOperationsService.gaussJordanElimination(matrix)
                println("Matrix in Row Echelon Form:")
                MatrixUtilityService.printMatrix(matrix)
            }
            3 -> {
                println("Exiting program. Goodbye!")
                break
            }
            else -> println("Invalid choice. Please try again.")
        }
    }
}
