package day06

import readInput

fun main() {
    fun getMultiplyOfPassingGameIndices(timeList: List<Int>, distanceList: List<Int>): Int {
        var multiplyOfRecordMakerHolds = 1
        for (i in distanceList.indices) {
            var winningWays = 0
            for (j in 1 until timeList[i]) {
                val remainingTime = timeList[i] - j
                if (remainingTime * j > distanceList[i]) {
                    winningWays += 1
                }
            }
            multiplyOfRecordMakerHolds *= winningWays
        }

        return multiplyOfRecordMakerHolds
    }


    fun part1(input: List<String>): Int {
        val timeList =
            input[0].split(":")[1].split(" ")
                .filter { it.isNotBlank() && it.isNotEmpty() }.map { it.toInt() }.toList()
        val distanceList =
            input[1].split(":")[1].split(" ")
                .filter { it.isNotBlank() && it.isNotEmpty() }.map { it.toInt() }.toList()

        return getMultiplyOfPassingGameIndices(timeList, distanceList)
    }

    fun part2(input: List<String>): Long {
        val time = input[0].split(":")[1].filterNot { it.isWhitespace() }.toLong()
        val distance = input[1].split(":")[1].filterNot { it.isWhitespace() }.toLong()

        var notWinningWays = 0
        for (j in 1 until time) {
            notWinningWays += 1
            val remainingTime = time - j
            if (remainingTime * j > distance) {
                break
            }
        }

        return time + 1 - ((notWinningWays * 2))
    }

    val input = readInput("./day06/Day06_input")
    println(part1(input))
    println(part2(input))
}
