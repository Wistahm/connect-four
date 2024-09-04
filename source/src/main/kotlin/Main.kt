import kotlin.system.exitProcess

var firstPlayer = Player("", 0, ' ')
var secondPlayer = Player("", 0, ' ')
var board = Board(0, 0)
var gamesCount = ""

fun main() {

    // Program's head title
    println("Connect Four")
    getUserDate()

}

fun getUserDate() {

    // Generally for this game we need two players
    // and each player have their individual name, score and disk type.
    // So we have to initialize them here.
    println("First player's name:")
    val firstPlayerName = readln()
    println("Second player's name:")
    val secondPlayerName = readln()

    firstPlayer = Player(name = firstPlayerName, diskType = 'o')
    secondPlayer = Player(name = secondPlayerName, diskType = '*')

    // After initializing the players, it's time to create a board
    // for our game. A 2D board that is created by sequence of characters
    // such as empty spaces and '║' fot its shape. There are variety possibles
    // for size of game's board which depends on user's input.
    var row: Int
    var column: Int
    while (true) {
        println(
            "Set the bo dimensions (Rows x Columns)" + "\n" +
                    "Press enter for default (6 x 7)"
        )
        val dimension = readln().trim()

        // There's only one valid input that user should put for the bo dimension
        // which is like [6 x 7 / 6x7] (x capital doesn't matter). So we need a regular expression
        // to match the user input with it and check if the user has done a clear job, otherwise
        // program will ask for the input again since we are in 'while' loop.
        val dimensionRegex = Regex("""\d+\s*[xX]\s*\d+""")
        if (dimension.isEmpty()) {
            // If user pressed Enter, the board will set default 6 x 7
            row = 6
            column = 7
            break
        } else if (dimensionRegex.matches(dimension) && dimension.isNotEmpty()) {
            row = dimension.first().toString().toInt()
            column = dimension.last().toString().toInt()

            // Now we have to check if the row and col entered correctly,
            // as a rule of thumb, row and column both should not be less than 5
            // or more than 9 and if user entered input incorrectly the program will ask again.
            if (row !in 5..9) {
                println("Board rows should be from 5 to 9")
                continue
            } else if (column !in 5..9) {
                println("Board columns should be from 5 to 9")
                continue
            } else {
                // If everything entered correctly then terminate the loop
                break
            }
        } else {
            println("Invalid input")
            continue
        }

    }
    // Initialize the game board
    board = Board(row, column)

    // Ask how many games want to play
    while (true) {
        println("Do you want to play single or multiple games?")
        println("For a single game input 1 or press Enter")
        println("Input a number of games: ")

        try {
            gamesCount = readln()
            if (gamesCount.isEmpty() || gamesCount.toInt() == 1) {
                println("$firstPlayerName VS $secondPlayerName")
                println("$row X $column board")
                println("Single game")
                board.printBoard()

                singleGamePlay()
                break
            } else if (gamesCount.toInt() > 1) {
                println("$firstPlayerName VS $secondPlayerName")
                println("$row X $column board")
                println("Total $gamesCount games")
                println("Game #1")
                board.printBoard()

                multipleGamesPlay()
                break
            } else if (gamesCount.toInt() <= 0) {
                println("Invalid input")
                continue
            }
        } catch (x: Exception) {
            println("Invalid input")
            continue
        }
    }

}

fun singleGamePlay() {

    var playerTurn = 0

    while (true) {
        if (playerTurn % 2 == 0) {
            // The first player starts the game
            if (settingDiskToBoard(firstPlayer.name, firstPlayer.diskType) != 0) {
                continue
            }

        } else {
            // Now it is second player's turn to play
            if (settingDiskToBoard(secondPlayer.name, secondPlayer.diskType) != 0) {
                continue
            }
        }

        board.printBoard()
        playerTurn++

        val winner = board.checkWinner()
        when (winner) {
            'o' -> {
                println("Player ${firstPlayer.name} won")
                println("Game over!")
                exitProcess(0)
            }
            '*' -> {
                println("Player ${secondPlayer.name} won")
                println("Game over!")
                exitProcess(0)
            }
        }
    }
}

var firstPlayerScore = firstPlayer.score
var secondPlayerScore = secondPlayer.score
fun multipleGamesPlay() {

    var games = 1
    var playerTurn = 0

    while (games <= gamesCount.toInt()) {
        if (playerTurn % 2 == 0) {
            // The first player starts the game
            if (settingDiskToBoard(firstPlayer.name, firstPlayer.diskType) != 0) {
                continue
            }

        } else {
            // Now it is second player's turn to play
            if (settingDiskToBoard(secondPlayer.name, secondPlayer.diskType) != 0) {
                continue
            }
        }

        board.printBoard()
        playerTurn++

        val winner = board.checkWinner()
        when (winner) {
            'o' -> {
                println("Player ${firstPlayer.name} won")
                firstPlayerScore += 2
                games++
                println("Score")
                println(
                    "${firstPlayer.name}: $firstPlayerScore " +
                            "${secondPlayer.name}: $secondPlayerScore"
                )
                board.resetBoard()
                if (games > gamesCount.toInt())
                    break
                println("Game #$games")
                board.printBoard()

            }
            '*' -> {
                println("Player ${secondPlayer.name} won")
                secondPlayerScore += 2
                games++
                println("Score")
                println(
                    "${firstPlayer.name}: $firstPlayerScore " +
                            "${secondPlayer.name}: $secondPlayerScore"
                )
                board.resetBoard()
                if (games > gamesCount.toInt())
                    break
                println("Game #$games")
                board.printBoard()
            }
            '#' -> {
                println("It is a draw")
                firstPlayerScore += 1
                secondPlayerScore += 1
                games++
                println("Score")
                println(
                    "${firstPlayer.name}: $firstPlayerScore " +
                            "${secondPlayer.name}: $secondPlayerScore"
                )
                board.resetBoard()
                if (games > gamesCount.toInt())
                    break
                println("Game #$games")
                board.printBoard()
            }
        }
    }

    println("Game over!")
}

fun settingDiskToBoard(playerName: String, disk: Char): Int {

    // The first player starts the game
    println("${playerName}'s turn: ")
    val playerDiskInput = readln()
    if (playerDiskInput == "end") {
        println("Game over!")
        exitProcess(0)
    } else if (playerDiskInput in "0".."9" && !playerDiskInput.matches(""".*[a-zA-Z]+.*""".toRegex())) {
        val diskToInt = playerDiskInput.toInt() * 2 - 1
        if (diskToInt !in 1..board.column * 2) {
            println("The column number is out of range (1 - ${board.column})")
        } else if (board.isColumnFull(diskToInt)) {
            println("Column ${playerDiskInput.toInt()} is full")
        } else {
            board.setDisk(diskToInt, disk)
            return 0
        }
    } else {
        println("Incorrect column number")
    }

    return -1

}