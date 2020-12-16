package net.nergi.binaryshifter

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@ExperimentalUnsignedTypes
class BinaryShifterTests {
    @Test
    fun `test if text is parsed correctly`() {
        val reg = Regex(BinaryShifter.inpRegString)
        var currentMatch: MatchResult?

        // Test 1: Valid input - Full match
        var currentString = "rotl 101"
        currentMatch = reg.find(currentString)
        assertEquals(listOf(currentString, "rotl", "101"), currentMatch?.groupValues)

        // Test 2: Valid input - Different operation, different number
        currentString = "shr 255"
        currentMatch = reg.find(currentString)
        assertEquals(listOf(currentString, "shr", "255"), currentMatch?.groupValues)

        // Test 3: Invalid input - Invalid operation
        currentString = "and"
        currentMatch = reg.find(currentString)
        assertEquals(null, currentMatch?.groupValues)

        // Test 4: Invalid input - No operation
        currentString = "101"
        currentMatch = reg.find(currentString)
        assertEquals(null, currentMatch?.groupValues)

        // Test 5: Invalid input - Nothing at all
        currentString = ""
        currentMatch = reg.find(currentString)
        assertEquals(null, currentMatch?.groupValues)
    }

    @Test
    fun `test rotate function`() {
        assertEquals(2u, 1u.rotate(32, 1))
        assertEquals(1u, 2u.rotate(32, -1))
        assertEquals(1u, 128u.rotate(8, 1))
        assertEquals(128u, 1u.rotate(8, -1))
    }

    @Test
    fun `test difficulty strings`() {
        assertEquals("UNDEF", Difficulty.UNDEF.toString())
        assertEquals("EASY", Difficulty.EASY.toString())
        assertEquals("NORMAL", Difficulty.NORMAL.toString())
        assertEquals("HARD", Difficulty.HARD.toString())
    }

    @Test
    fun `test difficulty numbers`() {
        assertEquals(0u, Difficulty.UNDEF.i)
        assertEquals(255u, Difficulty.EASY.i)
        assertEquals(65535u, Difficulty.NORMAL.i)
        assertEquals(UInt.MAX_VALUE, Difficulty.HARD.i)
    }
}
