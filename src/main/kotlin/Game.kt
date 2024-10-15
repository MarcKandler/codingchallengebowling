class Game(
    var currentFrame: Int = 0,
    val maxFrames: Int = 10,
) {
    var frames: MutableList<BowlingFrame> = ArrayList()

    init {
        var prevFrame: BowlingFrame? = null
        for (i in 1..<maxFrames) {
            val frameToAdd = NormalBowlingFrame(i, previousFrame = prevFrame)
            frames.add(frameToAdd)
            prevFrame = frameToAdd
        }
        frames.add(LastBowlingFrame(maxFrames, previousFrame = prevFrame))
    }

    fun roll(numberOfPins: Int) {
        val framePlayed = frames[currentFrame]
        try {
            framePlayed.roll(numberOfPins)
        } catch (e: Exception) {
            println(e.message)
        }

        updateCurrentFrameNumber(framePlayed)
    }

    private fun updateCurrentFrameNumber(framePlayed: BowlingFrame) {
        if (framePlayed.frameNumber < maxFrames && framePlayed.frameFinished()) {
            currentFrame += 1
        }
    }

    fun printScore() {
        for (frame in frames) {
            print("(${frame.rollOne}|${frame.rollTwo}) +${frame.bonus} -> ${frame.totalPoints} | ")
        }
    }
}