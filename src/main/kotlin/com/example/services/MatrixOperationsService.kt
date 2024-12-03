package matrix

object MatrixOperationsService {
    fun multiplyMatrices(matrixA: Array<DoubleArray>, matrixB: Array<DoubleArray>): Array<DoubleArray> {
        val rowsA = matrixA.size
        val colsA = matrixA[0].size
        val colsB = matrixB[0].size
        val result = Array(rowsA) { DoubleArray(colsB) }

        for (i in 0 until rowsA) {
            for (j in 0 until colsB) {
                for (k in 0 until colsA) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j]
                }
            }
        }
        return result
    }

    fun gaussJordanElimination(matrix: Array<DoubleArray>) {
        val rowCount = matrix.size
        val colCount = matrix[0].size

        var lead = 0
        for (r in 0 until rowCount) {
            if (lead >= colCount) return
            var i = r

            // Find pivot
            while (matrix[i][lead] == 0.0) {
                i++
                if (i == rowCount) {
                    i = r
                    lead++
                    if (lead == colCount) return
                }
            }
            // Swap rows to move pivot into position
            matrix[i] = matrix[r].also { matrix[r] = matrix[i] }

            // Normalize the pivot row
            val pivot = matrix[r][lead]
            for (j in 0 until colCount) {
                matrix[r][j] /= pivot
            }

            // Eliminate all other rows in the column
            for (k in 0 until rowCount) {
                if (k != r) {
                    val factor = matrix[k][lead]
                    for (j in 0 until colCount) {
                        matrix[k][j] -= factor * matrix[r][j]
                    }
                }
            }
            lead++
        }
    }
}
