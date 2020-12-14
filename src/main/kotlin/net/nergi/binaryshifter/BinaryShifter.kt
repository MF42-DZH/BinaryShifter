package net.nergi.binaryshifter

import java.util.Random

@ExperimentalUnsignedTypes
class BinaryShifter {
    companion object {
        const val inpRegString: String = "(and|or|xor|not|shl|shr) ([0-9]+)"

        private val random: Random = Random()
        private val inpReg: Regex = Regex(inpRegString)
    }

    private var gameStarted = false

    private var difficulty = Difficulty.UNDEF

    private var goalValue: UInt = 0u

    private var currentValue: UInt = 0u

    @Throws(IllegalArgumentException::class)
    fun startGame(diff: Difficulty) {
        if (gameStarted) {
            return
        }

        if (diff == Difficulty.UNDEF) {
            throw IllegalArgumentException("Invalid difficulty! [$diff]")
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
        } while (goalValue == currentValue || goalValue == 0u || currentValue == 0u)

        gameStarted = true
    }

    // Inputs are assumed to be in the form
    // [OP] [NUMERIC-STRING / BINARY-STRING of length <= difficulty length][b]
    @Throws(IllegalArgumentException::class)
    fun parseInput(str: String) {
        val lower = str.toLowerCase()
        val matchResult = inpReg.find(lower)
        if (matchResult != null) {
            val (op, num) = matchResult.groupValues
            val number = num.toUInt()

            currentValue = when (op) {
                "and" -> currentValue and number
                "or" -> currentValue or number
                "xor" -> currentValue xor number
                "not" -> currentValue.inv()
                "shl" -> currentValue shl number.toInt()
                "shr" -> currentValue shr number.toInt()
                else -> throw IllegalArgumentException("I have no idea how you got here. [$lower]")
            }

            // Capping the number
            currentValue = currentValue and difficulty.i
        } else {
            throw IllegalArgumentException("Argument is incorrectly formatted! [$lower]")
        }
    }

    fun hasWon(): Boolean {
        val b = currentValue == goalValue
        if (b) {
            gameStarted = false
        }

        return b
    }
}
