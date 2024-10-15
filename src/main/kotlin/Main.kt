fun main() {
    val game = Game()
    println("Welcome, please press a number from 0 to 10 to simulate a roll")
    println("...")
    while (!game.frames.last().frameFinished()) {
        val roll: Int
        try {
            roll = readln().toInt()
        } catch (e: Exception) {
            continue
        }
        game.roll(roll)
        printScoreboard(game)
    }
    println("\nGame has finished with following final Scoreboard:")
    printScoreboard(game)
}

private fun printScoreboard(game: Game) {
    for (frame in game.frames) {
        if (frame.rollOne == null)
            break
        val lastFrameScore = if (frame is LastBowlingFrame) "|${frame.rollThree}" else ""
        print("(${frame.rollOne}|${frame.rollTwo}$lastFrameScore) -> ${frame.totalPoints} | ")
    }
}