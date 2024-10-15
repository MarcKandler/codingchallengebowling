import BowlingFrameState.*

data class NormalBowlingFrame(
    override val frameNumber: Int,
    override var rollOne: Int? = null,
    override var rollTwo: Int? = null,
    override var bonus: Int = 0,
    override var state: BowlingFrameState = UNPLAYED,
    override var previousFrame: BowlingFrame? = null,
    override var totalPoints: Int = 0,
) : BowlingFrame {

    override fun roll(numberOfPins: Int) {
        if (numberOfPins < 0 || numberOfPins > 10) {
            throw IllegalArgumentException("Number of pins ($numberOfPins) rolled is invalid")
        }
        state = IN_PROGRESS
        if (rollOne == null) {
            rollOne = numberOfPins
        } else if (rollTwo == null && rollOne!! + numberOfPins <= 10) {
            rollTwo = numberOfPins
        } else throw IllegalArgumentException("Illegal State: RollOne: $rollOne, RollTwo: $rollTwo, current roll: $numberOfPins.")
        updateFrameState()
        applyBonusToPreviousFrames()
        updateTotalPoints()
    }

    private fun applyBonusToPreviousFrames() {
        if (previousFrame?.state == STRIKE) {
            previousFrame?.previousFrame?.applyBonus(previousFrame!!.rollOne!!, rollOne!!)
        }
        previousFrame?.applyBonus(rollOne ?: 0, rollTwo ?: 0)
    }

    override fun applyBonus(firstNextRoll: Int, secondNextRoll: Int) {
        when (state) {
            SPARE -> {
                bonus = firstNextRoll
            }

            STRIKE -> {
                bonus = firstNextRoll + secondNextRoll
            }

            else -> {
                println("Bonus not applicable for frame $frameNumber.")
            }
        }
        updateTotalPoints()
    }

    override fun updateTotalPoints() {
        totalPoints = previousFrame.let { it?.totalPoints ?: 0 }.plus(rollOne!!).plus(rollTwo ?: 0).plus(bonus)
    }

    private fun updateFrameState() {
        if (rollOne == 10)
            state = STRIKE
        else if (rollTwo?.let { rollOne?.plus(it) } == 10) {
            state = SPARE
        } else if (rollOne != null && rollTwo != null && rollOne!! + rollTwo!! < 10) {
            state = PLAYED
        }
    }

    override fun frameFinished() = state in arrayOf(SPARE, STRIKE, PLAYED)
}
