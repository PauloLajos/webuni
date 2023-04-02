package com.example.tictactoe.model

object TicTacToeModel {
    public val EMPTY: Short = 0
    public val CIRCLE: Short = 1
    public val CROSS: Short = 2

    public val CONTINUE: Short = -1
    public val DRAW: Short = -2

    private val model = arrayOf(
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY)
    )

    private var nextPlayer = CIRCLE

    fun resetModel() {
        for (i in 0..2) {
            for (j in 0..2) {
                model[i][j] = EMPTY
            }
        }
        nextPlayer = CIRCLE
    }

    fun getFieldContent(x: Int, y: Int) = model[x][y]

    fun setFieldContent(x: Int, y: Int, content: Short) {
        model[x][y] = content
    }

    fun getNextPlayer() = nextPlayer

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
    }

    // Házi feladat
    fun getWinner(gamer: Short): Short {
        val win = arrayOf(
            shortArrayOf(0, 1, 2), // Check first row
            shortArrayOf(3, 4, 5), // Check second row
            shortArrayOf(6, 7, 8), // Check third row
            shortArrayOf(0, 3, 6), // Check first column
            shortArrayOf(1, 4, 7), // Check second column
            shortArrayOf(2, 5, 8), // Check third column
            shortArrayOf(0, 4, 8), // Check first diagonal
            shortArrayOf(2, 4, 6)  // Check second diagonal
        )
        val matrix = shortArrayOf(model[0][0], model[0][1], model[0][2], model[1][0], model[1][1], model[1][2], model[2][0], model[2][1], model[2][2] )
        var empty = false
        for (i in 0..7) {
            var m0 = matrix[ win[i][0].toInt() ]
            var m1 = matrix[ win[i][1].toInt() ]
            var m2 = matrix[ win[i][2].toInt() ]
            if ( m0 == gamer && m1 == gamer && m2 == gamer) {
                return gamer
            } else {
                if(m0 == EMPTY || m1 == EMPTY || m2 == EMPTY ) empty = true
            }
        }
        return if (empty) CONTINUE else DRAW
    }
}