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
        var currentString = "and 101b"
        currentMatch = reg.find(currentString)
        assertEquals(listOf(currentString, "and", "101", "b"), currentMatch?.groupValues)

        // Test 2: Valid input - No binary
        currentString = "and 101"
        currentMatch = reg.find(currentString)
        assertEquals(listOf(currentString, "and", "101", ""), currentMatch?.groupValues)

        // Test 3: Invalid input - No number
        currentString = "and b"
        currentMatch = reg.find(currentString)
        assertEquals(null, currentMatch?.groupValues)

        // Test 4: Invalid input - No operation
        currentString = "101b"
        currentMatch = reg.find(currentString)
        assertEquals(null, currentMatch?.groupValues)

        // Test 5: Invalid input - Just b
        currentString = "b"
        currentMatch = reg.find(currentString)
        assertEquals(null, currentMatch?.groupValues)
    }
}
