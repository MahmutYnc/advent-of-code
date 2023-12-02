package day02

import readInput

fun main() {

    // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    // Game 1 -> 1 indices of game 1
    // ; -> indices of game subset of game 1
    // convert to Map<Int, Map<String, Int>>
    fun convertInputToMap(input: List<String>): Map<Int, List<Map<String, Int>>> {
        val map = mutableMapOf<Int, List<Map<String, Int>>>()

        for (i in input.indices) {
            val game = input[i].split(":")[1]
            val gameList = mutableListOf<Map<String, Int>>()

            val splitGameIntoParts = game.split(";")

            for (j in splitGameIntoParts.indices) {
                val part = splitGameIntoParts[j]
                val splitPart = part.split(",")
                val partMap = mutableMapOf<String, Int>()

                for (k in splitPart.indices) {
                    val word = splitPart[k]
                    val splitWord = word.split(" ")

                    partMap[splitWord[2]] = splitWord[1].toInt()
                }

                gameList.add(partMap)
            }

            map[i] = gameList
        }

        return map
    }

    fun conditionsMetForMax(game: Map<String, Int>, conditionColor: String, conditionValue: Int): Boolean {
        return game[conditionColor]?.let { it <= conditionValue } ?: true
    }

    fun multiplyMaxValues(maxValues: Map<String, Int>): Int {
        return maxValues["blue"]!! * maxValues["red"]!! * maxValues["green"]!!
    }

    fun findMaximumValueForEachColorMultiplyAndSum(input: List<String>): Int {
        val gameMap = convertInputToMap(input)

        val maxValues = mutableMapOf<String, Int>()
        var powerOfGameMap = 0
        for (i in gameMap.keys) {
            val game = gameMap[i]!!

            maxValues["blue"] = 1
            maxValues["red"] = 1
            maxValues["green"] = 1

            // find max of blue color in all subsets
            for (j in game.indices) {
                maxValues["blue"] = maxOf(maxValues["blue"] ?: 1, game[j]["blue"] ?: 1)
                maxValues["red"] = maxOf(maxValues["red"] ?: 1, game[j]["red"] ?: 1)
                maxValues["green"] = maxOf(maxValues["green"] ?: 1, game[j]["green"] ?: 1)
            }

            powerOfGameMap += multiplyMaxValues(maxValues)
        }

        return powerOfGameMap
    }

    fun getSumOfPassingGameIndices(input: List<String>): Int {
        val gameMap = convertInputToMap(input)

        var sum = 0
        for (i in gameMap.keys) {
            val game = gameMap[i]!!

            val blue = game.all { conditionsMetForMax(it, "blue", 14) }
            val red = game.all { conditionsMetForMax(it, "red", 12) }
            val green = game.all { conditionsMetForMax(it, "green", 13) }
            if (blue && red && green) {
                sum += i + 1
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        // part 1 condition values: blue: 14, red: 12, green: 13
        return getSumOfPassingGameIndices(input)
    }

    fun part2(input: List<String>): Int {
        return findMaximumValueForEachColorMultiplyAndSum(input)
    }

    val input = readInput("./day02/Day02_input")

    println(part1(input))
    println(part2(input))
}