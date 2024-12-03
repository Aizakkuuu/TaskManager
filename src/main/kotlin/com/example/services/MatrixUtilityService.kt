package utils

import java.util.Scanner

object MatrixUtilityService {
    fun inputMatrices(scanner: Scanner): Pair<Array<DoubleArray>, Array<DoubleArray>> {
        println("Enter the dimensions of Matrix A (rows cols): ")
        val rowsA = scanner.nextInt()
        val colsA = scanner.nextInt()
        println("Enter Matrix A:")
        val matrixA = inputMatrix(scanner, rowsA, colsA)

        println("Enter the dimensions of Matrix B (rows cols): ")
        val rowsB = scanner.nextInt()
        val colsB = scanner.nextInt()

        if (colsA != rowsB) {
            throw IllegalArgumentException("Matrix A columns must match Matrix B rows for multiplication.")
        }

        println("Enter Matrix B:")
        val matrixB = inputMatrix(scanner, rowsB, colsB)

        return Pair(matrixA, matrixB)
    }

    fun inputMatrix(scanner: Scanner, rows: Int = 0, cols: Int = 0): Array<DoubleArray> {
        val matrixRows = if (rows > 0) rows else {
            print("Enter number of rows: ")
            scanner.nextInt()
        }
        val matrixCols = if (cols > 0) cols else {
            print("Enter number of columns: ")
            scanner.nextInt()
        }
        val matrix = Array(matrixRows) { DoubleArray(matrixCols) }
        println("Enter matrix elements row by row:")
        for (i in 0 until matrixRows) {
            for (j in 0 until matrixCols) {
                matrix[i][j] = scanner.nextDouble()
            }
        }
        return matrix
    }

    fun printMatrix(matrix: Array<DoubleArray>) {
        for (row in matrix) {
            println(row.joinToString(" ") { "%6.2f".format(it) })
        }
    }
}
