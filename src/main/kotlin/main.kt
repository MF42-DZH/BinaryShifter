import net.nergi.binaryshifter.BinaryShifter
import net.nergi.binaryshifter.Difficulty
import net.nergi.binaryshifter.isInteger
import net.nergi.binaryshifter.pass
import java.util.Scanner

val scn = Scanner(System.`in`)

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    // Game
    val bshift = BinaryShifter()

    // Create game
    println("Select difficulty:\n(0) Easy\n(1) Normal\n(2) Hard")

    while (true) {
        val nxt = scn.next().filter { !it.isWhitespace() }
        if (nxt.isInteger()) {
            when (nxt.toInt()) {
                0 -> bshift.startGame(Difficulty.EASY)
                1 -> bshift.startGame(Difficulty.NORMAL)
                2 -> bshift.startGame(Difficulty.HARD)
                else -> pass
            }
        }

        println("\nInvalid difficulty. Please select a correct difficulty.")
    }
}
