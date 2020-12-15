package net.nergi.binaryshifter

import java.util.Random

// Rotate bits.
// Positive: left
@ExperimentalUnsignedTypes
fun UInt.rotate(width: Int, amt: Int): UInt {
    var s = String.format("%${width}s", this.toString(2)).replace(' ', '0')
    return when {
        amt > 0 -> {
            for (i in 0 until amt) {
                val c = s[0]
                s = s.drop(1) + c
            }
            s.toUInt(2)
        }
        amt < 0 -> {
            for (i in 0 until -amt) {
                val c = s.last()
                s = c + s.dropLast(1)
            }
            s.toUInt(2)
        }
        else -> this
    }
}

@ExperimentalUnsignedTypes
object BinaryShifter {
    const val inpRegString: String = "(rotl|rotr|shl|shr) ([0-9]+)"

    private val random: Random = Random()
    private val inpReg: Regex = Regex(inpRegString)

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
    @Throws(IllegalArgumentException::class, RuntimeException::class)
    fun parseInput(str: String) {
        val bitCnt = when (difficulty) {
            Difficulty.UNDEF -> 0
            Difficulty.EASY -> 8
            Difficulty.NORMAL -> 16
            Difficulty.HARD -> 32
        }

        val lower = str.toLowerCase()
        if (lower == "exit") {
            throw RuntimeException("User exited.")
        }

        if (lower == "not") {
            currentValue = currentValue.inv() and difficulty.i
            return
        }

        val matchResult = inpReg.find(lower)
        if (matchResult != null) {
            val (_, op, num) = matchResult.groupValues
            val number = num.toInt()

            currentValue = when (op) {
                "not" -> currentValue.inv()
                "rotl" -> currentValue.rotate(bitCnt, number)
                "rotr" -> currentValue.rotate(bitCnt, -number)
                "shl" -> currentValue shl number
                "shr" -> currentValue shr number
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

    override fun toString(): String {
        val sb = StringBuffer()
        val bitCnt = when (difficulty) {
            Difficulty.UNDEF -> 0
            Difficulty.EASY -> 8
            Difficulty.NORMAL -> 16
            Difficulty.HARD -> 32
        }

        val cv = String.format("%${bitCnt}s", currentValue.toString(2)).replace(' ', '0')
        val gv = String.format("%${bitCnt}s", goalValue.toString(2)).replace(' ', '0')

        sb.append("Current Value: $cv\n")
        sb.append("   Goal Value: $gv\n")
        sb.append("-- INPUT BELOW | rotl n / rotr n / shl n / shr n / not / exit --")

        return sb.toString()
    }
}
