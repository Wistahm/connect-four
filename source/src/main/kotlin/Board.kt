class Board(val row: Int, val column: Int) {

    private var bo = MutableList(0) {MutableList(0) {' '} }

    init {
        createBoard(row, column)
    }

    private fun createBoard(row: Int, column: Int): MutableList<MutableList<Char>> {
        bo = MutableList(row) { MutableList(column * 2 + 1) { ' ' } }

        for (i in 0 until bo.size) {
            for (j in 0 until bo[i].size) {
                if (j % 2 == 0)
                    bo[i][j] = '║'
                else
                    bo[i][j] = ' '
            }
        }
        return bo
    }

    fun printBoard() {
        var columnCount = 0
        for (i in 0 until column) {
            columnCount++
            print(" $columnCount")
        }
        println()
        for (i in 0 until bo.size) {
            for (j in 0 until bo[i].size) {
                print(bo[i][j])
            }
            println()
        }
        print("╚═")
        for (i in 1 until column) {
            print("╩═")
        }
        println("╝")
    }

    fun setDisk(x: Int, diskType: Char) {
        for (i in bo.size - 1 downTo 0) {
            if (bo[i][x] == ' ') {
                bo[i][x] = diskType
                break
            }
        }

    }

    fun resetBoard() {
        createBoard(row, column)
    }

    fun isColumnFull(col: Int): Boolean {
        // This function checks that is the given column 'col' number full or not, and
        // returns a Boolean value
        for (row in bo) {
            if (row[col] == ' ')
                return false
        }
        return true
    }

    fun checkWinner(): Char {
        // Because of the variety possibles of board size, we will have to
        // iterate over a specific time based on the board's <column>.
        var y = 0
        when (column) {
            5 -> y = 2
            6 -> y = 4
            7 -> y = 6
            8 -> y = 8
            9 -> y = 10
        }

        // Check the horizontal situation
        for (i in 0 until row) {
            for (j in 0..y step 2) {
                if (bo[i][j + 1] != ' '
                    && bo[i][j + 3] != ' '
                    && bo[i][j + 5] != ' '
                    && bo[i][j + 7] != ' '
                    && bo[i][j + 1] == bo[i][j + 3]
                    && bo[i][j + 3] == bo[i][j + 5]
                    && bo[i][j + 5] == bo[i][j + 7]
                ) {
                    return bo[i][j + 1]
                }
            }
        }

        // Because of the variety possibles of board size, we will have to
        // iterate over a specific time based on the board's <row>.
        var g = 0
        when (row) {
            5 -> g = 2
            6 -> g = 3
            7 -> g = 4
            8 -> g = 5
            9 -> g = 6
        }

        // Check the vertical situation
        for (i in 1 until column * 2 + 1 step 2) {
            for (j in 0 until g) {
                if (bo[j][i] != ' '
                    && bo[j + 1][i] != ' '
                    && bo[j + 2][i] != ' '
                    && bo[j + 3][i] != ' '
                    && (bo[j][i] == bo[j + 1][i]
                            && bo[j + 1][i] == bo[j + 2][i]
                            && bo[j + 2][i] == bo[j + 3][i])
                ) {
                    return bo[j][i]
                }
            }
        }

        //
        var z = 0
        when (column) {
            5 -> z = 5
            6 -> z = 6
            7 -> z = 8
            8 -> z = 10
            9 -> z = 12
        }
        // Check the diagonal situation [left-up to right-down]
        for (i in 0 until g) {
            for (j in 1 until z step 2) {
                if (bo[i][j] != ' '
                    && bo[i + 1][j + 2] != ' '
                    && bo[i + 2][j + 4] != ' '
                    && bo[i + 3][j + 6] != ' '
                    && bo[i][j] == bo[i + 1][j + 2]
                    && bo[i + 1][j + 2] == bo[i + 2][j + 4]
                    && bo[i + 2][j + 4] == bo[i + 3][j + 6]
                ) {
                    return bo[i][j]
                }
            }
        }


        // Check the diagonal situation [left-down to right-up]
        for (i in 0 until g) {
            for (j in 7 until bo[i].size step 2) {
                if (bo[i][j] != ' '
                    && bo[i + 1][j - 2] != ' '
                    && bo[i + 2][j - 4] != ' '
                    && bo[i + 3][j - 6] != ' '
                    && bo[i][j] == bo[i + 1][j - 2]
                    && bo[i + 1][j - 2] == bo[i + 2][j - 4]
                    && bo[i + 2][j - 4] == bo[i + 3][j - 6]
                ) {
                    return bo[i][j]
                }
            }
        }

        // Check the draw position
        var x = 1
        var i = 0
        while (x < column * 2 + 1) {
            if (isColumnFull(x)) {
                i++
            }
            if (i == column) {
                return '#'
            }

            x += 2
        }

        return ' '
    }

}