package net.nergi.binaryshifter

import java.util.Random

@ExperimentalUnsignedTypes
class BinaryShifter {
    companion object {
        private val random: Random = Random()
        private val inpReg: Regex = Regex("(and|or|xor|not|shl|shr) ([0-9]+)(b?)")
    }

    private var gameStarted = false

    private var difficulty = Difficulty.UNDEF

    private var goalValue: UInt = 0u

    private var currentValue: UInt = 0u

    fun startGame(diff: Difficulty) {
        if (gameStarted) {
            return
        }

        difficulty = diff

        do {
            for (i in 0 until 32) {
                val b1 = random.nextInt(2).toUInt()
                val b2 = random.nextInt(2).toUInt()

                goalValue = goalValue or (b1 shl i)
                currentValue = currentValue or (b2 shl i)
            }

            goalValue = goalValue and difficulty.i
            currentValue = currentValue and difficulty.i
        } while (goalValue == currentValue)

        gameStarted = true
    }

    // Inputs are assumed to be in the form
    //
    // [OP] [NUMERIC-STRING of length <= difficulty length]
    fun parseInput(str: String) {

    }

    fun hasWon(): Boolean {
        val b = currentValue == goalValue
        if (b) {
            gameStarted = false
        }

        return b
    }
}