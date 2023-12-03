package day03

import readInput

fun main() {

    val specialChars = "/[-@!\$%^&*()_+-|~=`{}\\[\\]:\";'<->?,#]/gm"

    fun convertInputInto2DCharArray(input: List<String>): Array<CharArray> {
        val array = Array(input.size) { CharArray(input[0].length) }
        for (i in input.indices) {
            for (j in input[i].indices) {
                array[i][j] = input[i][j]
            }
        }
        return array
    }

    fun findNumbersAndIndexMap(input: List<String>): Map<Pair<Int, Int>, String> {
        val result = mutableMapOf<Pair<Int, Int>, String>()

        for (i in input.indices) {
            var j = 0
            while (j < input[i].length) {
                if (j < input[i].length - 2 && input[i][j].isDigit() && input[i][j + 1].isDigit() && input[i][j + 2].isDigit()) {
                    result[Pair(i, j)] = input[i].substring(j, j + 3)
                    j += 3
                } else if (j < input[i].length - 1 && input[i][j].isDigit() && input[i][j + 1].isDigit()) {
                    result[Pair(i, j)] = input[i].substring(j, j + 2)
                    j += 2
                } else if (input[i][j].isDigit()) {
                    result[Pair(i, j)] = input[i][j].toString()
                    j++
                } else {
                    j++
                }
            }
        }
        return result
    }

    fun checkCharIsSpecialChar(char: Char): Boolean {
        return specialChars.contains(char)
    }

    fun getAdjacentChars(
        value: String,
        lineIndexOfNumber: Int,
        columnIndexOfNumber: Int,
        charArray: Array<CharArray>
    ): MutableMap<Pair<Int, Int>, Char> {
        val valueDigitSize = value.length
        val adjacentCharMap = mutableMapOf<Pair<Int, Int>, Char>()

        for (k in 0 until valueDigitSize + 1) {
            if (lineIndexOfNumber - 1 >= 0 && !charArray[lineIndexOfNumber - 1][columnIndexOfNumber].isDigit()) {
                adjacentCharMap[Pair(lineIndexOfNumber - 1, columnIndexOfNumber)] =
                    charArray[lineIndexOfNumber - 1][columnIndexOfNumber]
            }
            if (lineIndexOfNumber - 1 >= 0 && columnIndexOfNumber - 1 >= 0 &&
                !charArray[lineIndexOfNumber - 1][columnIndexOfNumber - 1].isDigit()
            ) {
                adjacentCharMap[Pair(lineIndexOfNumber - 1, columnIndexOfNumber - 1)] =
                    charArray[lineIndexOfNumber - 1][columnIndexOfNumber - 1]
            }

            // left of the number's first digit on below line if it exists
            if (lineIndexOfNumber - 1 >= 0 && columnIndexOfNumber + k < charArray[lineIndexOfNumber].size &&
                !charArray[lineIndexOfNumber - 1][columnIndexOfNumber + k].isDigit()
            ) {
                adjacentCharMap[Pair(lineIndexOfNumber - 1, columnIndexOfNumber + k)] =
                    charArray[lineIndexOfNumber - 1][columnIndexOfNumber + k]
            }

            // left of the number's first digit
            if (columnIndexOfNumber - 1 >= 0 && !charArray[lineIndexOfNumber][columnIndexOfNumber - 1].isDigit()) {
                adjacentCharMap[Pair(lineIndexOfNumber, columnIndexOfNumber - 1)] =
                    charArray[lineIndexOfNumber][columnIndexOfNumber - 1]
            }

            // right of the number's last digit
            if (columnIndexOfNumber + k < charArray[lineIndexOfNumber].size &&
                !charArray[lineIndexOfNumber][columnIndexOfNumber + k].isDigit()
            ) {
                adjacentCharMap[Pair(lineIndexOfNumber, columnIndexOfNumber + k)] =
                    charArray[lineIndexOfNumber][columnIndexOfNumber + k]
            }

            // left above corner if it exists
            if (lineIndexOfNumber + 1 < charArray.size && columnIndexOfNumber - 1 >= 0 &&
                !charArray[lineIndexOfNumber + 1][columnIndexOfNumber - 1].isDigit()
            ) {
                adjacentCharMap[Pair(lineIndexOfNumber + 1, columnIndexOfNumber - 1)] =
                    charArray[lineIndexOfNumber + 1][columnIndexOfNumber - 1]
            }

            // right of the number's last digit on above line if it exists
            if (lineIndexOfNumber + 1 < charArray.size && columnIndexOfNumber + k < charArray[lineIndexOfNumber].size &&
                !charArray[lineIndexOfNumber + 1][columnIndexOfNumber + k].isDigit()
            ) {
                adjacentCharMap[Pair(lineIndexOfNumber + 1, columnIndexOfNumber + k)] =
                    charArray[lineIndexOfNumber + 1][columnIndexOfNumber + k]
            }
        }
        return adjacentCharMap
    }

    fun findNumbersWithAdjacentSpecialCharsAndSum(input: List<String>): Int {
        var sum = 0

        val numbersAndIndexMap = findNumbersAndIndexMap(input)
        val charArray = convertInputInto2DCharArray(input)
        numbersAndIndexMap.forEach { (key, value) ->
            val (i, j) = key

            val adjacentOfGivenNumber = getAdjacentChars(value, i, j, charArray)
            if (adjacentOfGivenNumber.values.any { checkCharIsSpecialChar(it) }) {
                sum += value.toInt()
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        return findNumbersWithAdjacentSpecialCharsAndSum(input)
    }

    fun findGears(
        charArray: Array<CharArray>,
        numbersAndIndexMap: Map<Pair<Int, Int>, String>
    ): Map<Pair<Int, Int>, MutableList<Int>> {
        val gears = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

        numbersAndIndexMap.forEach { (key, value) ->
            val (i, j) = key
            val adjacentOfGivenNumber = getAdjacentChars(value, i, j, charArray)
            val isStarInAdjacent = adjacentOfGivenNumber.entries
                .filter { it.value == '*' }
                .map { it.key }

            if (isStarInAdjacent.isNotEmpty()) {
                for (k in isStarInAdjacent) {
                    if (!gears.containsKey(k)) {
                        gears[k] = mutableListOf()
                    }
                    gears[k]!!.add(value.toInt())
                }
            }
        }

        return gears.filter { it.value.size == 2 }.toMap()
    }

    fun calculateGearRatio(gears: MutableList<Int>): Int {
        return gears[0] * gears[1]
    }

    fun part2(input: List<String>): Int {
        val charArray = convertInputInto2DCharArray(input)
        val numbersAndIndexMap = findNumbersAndIndexMap(input)
        val gears = findGears(charArray, numbersAndIndexMap)

        return gears.entries.sumOf { calculateGearRatio(it.value) }
    }

    val input = readInput("./day03/Day03_input")

    println(part1(input))
    println(part2(input))
}
