import net.nergi.binaryshifter.BinaryShifter
import net.nergi.binaryshifter.Difficulty
import net.nergi.binaryshifter.isInteger
import net.nergi.binaryshifter.pass
import java.util.Scanner

val scn = Scanner(System.`in`)

@ExperimentalUnsignedTypes
fun main() {
    // Game
    val bshift = BinaryShifter()

    // Create game
    println("Select difficulty:\n(0) Easy\n(1) Normal\n(2) Hard")

    while (true) {
        val nxt = scn.nextLine().filter { !it.isWhitespace() }
        if (nxt.isInteger()) {
            when (nxt.toInt()) {
                0 -> {
                    bshift.startGame(Difficulty.EASY)
                    break
                }
                1 -> {
                    bshift.startGame(Difficulty.NORMAL)
                    break
                }
                2 -> {
                    bshift.startGame(Difficulty.HARD)
                    break
                }
                else -> pass
            }
        }

        println("\nInvalid difficulty. Please select a valid difficulty.")
    }

    do {
        println(bshift)
        try {
            val inp = scn.nextLine().filter { it !in "\r\n" }
            bshift.parseInput(inp)
        } catch (e: IllegalArgumentException) {
            println(e)
        }
    } while (!bshift.hasWon())

    println("Congratulations! You've won!")
}
