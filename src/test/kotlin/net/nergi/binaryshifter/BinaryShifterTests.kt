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
        var currentString = "and 101"
        currentMatch = reg.find(currentString)
        assertEquals(listOf(currentString, "and", "101"), currentMatch?.groupValues)

        // Test 2: Valid input - Different operation, different number
        currentString = "or 255"
        currentMatch = reg.find(currentString)
        assertEquals(listOf(currentString, "or", "255"), currentMatch?.groupValues)

        // Test 3: Invalid input - No number
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
}
