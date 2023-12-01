package day01

import readInput

fun main() {

    fun getCalibrationValueForEachLine(input: String): Int? {
        // Find first digit
        val firstDigit = input.find { it.isDigit() }?.toString() ?: return null

        // Find last digit by checking the string from end
        val lastDigit = input.reversed().find { it.isDigit() }?.toString() ?: return null

        // Combine first and last digit to get the calibration value
        return "$firstDigit$lastDigit".toInt()
    }

    fun part1(input: List<String>): Int {
        return input
            .mapNotNull { getCalibrationValueForEachLine(it) }
            .sum()
    }

    fun convertLetterDigitToInt(input: String): String {
        val digitWords = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        if (input.all { it.isDigit() }) {
            return input
        }

        val inputChunks = mutableListOf<String>()
        var chunk = ""

        // Break the input apart into chunks at each number
        for (i in input.indices) {
            if (input[i].isLetter()) {
                chunk += input[i]
                digitWords.forEach { (key, value) ->
                    chunk = chunk.replace(key, value)
                    if (key == chunk) {
                        inputChunks += chunk.replace(key, value)
                        chunk = input[i].toString()
                    }
                }
            } else{
                inputChunks += chunk
                inputChunks += input[i].toString()
                break
            }
        }

        val chunkResult = inputChunks.joinToString()

        val firstDigit = chunkResult.find { it.isDigit() }

        chunk = ""
        val inputChunks2 = mutableListOf<String>()
        val reversedInput = input.reversed()
        // Break the input apart into chunks at each number
        for (i in reversedInput.indices) {
            if (reversedInput[i].isLetter()) {
                chunk += reversedInput[i]
                digitWords.forEach { (key, value) ->
                    chunk = chunk.replace(key.reversed(), value)
                    if (key == chunk) {
                        inputChunks2 += chunk.replace(key.reversed(), value)
                        chunk = reversedInput[i].toString()
                    }
                }
            } else {
                inputChunks2 += chunk
                inputChunks2 += reversedInput[i].toString()
                break
            }
        }

        val chunkResult2 = inputChunks2.joinToString()

        val lastDigit = chunkResult2.find { it.isDigit() }
        return "${firstDigit.toString()}${lastDigit.toString()}"
    }

    fun part2(input: List<String>): Int {
        return input
            .mapNotNull { getCalibrationValueForEachLine(convertLetterDigitToInt(it)) }
            .sum()
    }

    val input = readInput("./day01/Day01_input")

    println(part1(input))
    println(part2(input))
}
