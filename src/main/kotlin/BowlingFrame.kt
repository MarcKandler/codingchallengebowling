interface BowlingFrame {
    val frameNumber: Int
    var rollOne: Int?
    var rollTwo: Int?
    var bonus: Int
    var state: BowlingFrameState
    var previousFrame: BowlingFrame?
    var totalPoints: Int

    fun roll(numberOfPins: Int)
    fun frameFinished(): Boolean
    fun applyBonus(firstNextRoll: Int, secondNextRoll: Int)
    fun updateTotalPoints()
}