import BowlingFrameState.*

data class LastBowlingFrame(
    override val frameNumber: Int,
    override var rollOne: Int? = null,
    override var rollTwo: Int? = null,
    override var bonus: Int = 0,
    override var state: BowlingFrameState = UNPLAYED,
    override var previousFrame: BowlingFrame? = null,
    override var totalPoints: Int = 0,
    var rollThree: Int? = null,
) : BowlingFrame {

    override fun roll(numberOfPins: Int) {
        if (numberOfPins < 0 || numberOfPins > 10) {
            throw IllegalArgumentException("Number of pins ($numberOfPins) rolled is invalid")
        }
        if (rollOne == null) {
            rollOne = numberOfPins
            state = IN_PROGRESS
        } else if (rollTwo == null) {
            rollTwo = numberOfPins
        } else if (state in arrayOf(SPARE, STRIKE) && rollThree == null) {
            rollThree = numberOfPins
        } else
            throw IllegalArgumentException("Illegal State: RollOne: $rollOne, RollTwo: $rollTwo, RollThree: $rollThree, current roll: $numberOfPins., state: $state")
        updateFrameState()
        applyBonusToPreviousFrames()
        updateTotalPoints()
    }

    private fun updateFrameState() {
        if (rollOne == 10)
            state = STRIKE
        else if (rollTwo?.let { rollOne?.plus(it) } == 10) {
            state = SPARE
        } else if (rollThree != null) {
            state = PLAYED
        }
    }

    override fun frameFinished(): Boolean =
        rollThree != null || rollOne != null && rollTwo != null && rollOne!!.plus(rollTwo!!) < 10

    override fun applyBonus(firstNextRoll: Int, secondNextRoll: Int) {
        TODO("Not yet implemented")
    }

    private fun applyBonusToPreviousFrames() {
        if (previousFrame?.state == STRIKE) {
            previousFrame?.previousFrame?.applyBonus(previousFrame!!.rollOne!!, rollOne!!)
        }
        if (state == STRIKE) {
            previousFrame?.applyBonus(rollOne ?: 0, rollThree ?: 0)
        } else {
            previousFrame?.applyBonus(rollOne ?: 0, rollTwo ?: 0)
        }
    }

    override fun updateTotalPoints() {
        totalPoints = previousFrame.let { it?.totalPoints ?: 0 }.plus(rollOne!!).plus(rollTwo ?: 0).plus(rollThree ?: 0)
            .plus(bonus)
    }
}
