import net.nergi.binaryshifter.BinaryShifter
import net.nergi.binaryshifter.Difficulty
import net.nergi.binaryshifter.isInteger
import net.nergi.binaryshifter.pass
import java.lang.RuntimeException
import java.util.Scanner

val scn = Scanner(System.`in`)

@ExperimentalUnsignedTypes
fun main() {
    // Create game
    println("Select difficulty or exit:\n(0) Easy\n(1) Normal\n(2) Hard")

    while (true) {
        val nxt = scn.nextLine().filter { !it.isWhitespace() }
        if (nxt.isInteger()) {
            when (nxt.toInt()) {
                0 -> {
                    BinaryShifter.startGame(Difficulty.EASY)
                    break
                }
                1 -> {
                    BinaryShifter.startGame(Difficulty.NORMAL)
                    break
                }
                2 -> {
                    BinaryShifter.startGame(Difficulty.HARD)
                    break
                }
                else -> pass
            }
        } else {
            if (nxt.toLowerCase() == "exit") {
                println("\nYou have exited the game.")
                return
            }
        }

        println("\nInvalid difficulty. Please select a valid difficulty.")
    }

    print('\n')

    do {
        println(BinaryShifter)
        try {
            val inp = scn.nextLine().filter { it !in "\r\n" }
            BinaryShifter.parseInput(inp)
        } catch (e: IllegalArgumentException) {
            println(e)
        } catch (e: RuntimeException) {
            break
        }
        print('\n')
    } while (!BinaryShifter.hasWon())

    if (BinaryShifter.hasWon()) {
        println("$BinaryShifter\n\nCongratulations! You've won!")
    } else {
        println("\n$BinaryShifter\n\nYou have exited the game.")
    }
}
