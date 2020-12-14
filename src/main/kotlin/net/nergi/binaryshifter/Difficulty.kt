package net.nergi.binaryshifter

@ExperimentalUnsignedTypes
enum class Difficulty(val i: UInt) {
    UNDEF(0u), EASY(255u), NORMAL(65535u), HARD(UInt.MAX_VALUE);

    override fun toString(): String {
        return when (this) {
            UNDEF -> "UNDEF"
            EASY -> "EASY"
            NORMAL -> "NORMAL"
            HARD -> "HARD"
        }
    }
}