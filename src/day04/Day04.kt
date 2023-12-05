package day04

import readInput
import kotlin.math.pow

fun main() {

    fun createMapFromGames(input: List<String>): Map<Int, Pair<List<Int>, List<Int>>> {
        val map = mutableMapOf<Int, Pair<List<Int>, List<Int>>>()

        for (i in input.indices) {
            val game = input[i].split(":")
            val numbers = game[1].split(" | ")
            val winningNumbers = numbers[0].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val calledNumbers = numbers[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            map[i] = Pair(winningNumbers, calledNumbers)
        }
        return map
    }

    fun getNumberOfPointsEarned(games: Map<Int, Pair<List<Int>, List<Int>>>): Int {
        var totalPoint = 0.0
        for (i in games.keys) {
            val game = games[i]!!
            val matchCount = (game.first.toSet() intersect game.second.toSet()).size
            if (matchCount > 0) {
                totalPoint += 2.0.pow(matchCount.toDouble() - 1)
            }
        }

        return totalPoint.toInt()
    }

    fun part1(input: List<String>): Int {
        val games = createMapFromGames(input)
        return getNumberOfPointsEarned(games)
    }

    fun getCopyCountOfEachGameWillCreate(games: Map<Int, Pair<List<Int>, List<Int>>>): Int {
        val played = MutableList(games.size) { 0 }
        for (i in games.keys) {
            played[i] += 1
            val game = games[i]!!
            val matchCount = (game.first.toSet() intersect game.second.toSet()).size

            for (k in 0 until matchCount) {
                played[i + k + 1] += played[i]
            }
        }
        return played.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        val games = createMapFromGames(input)
        return getCopyCountOfEachGameWillCreate(games)
    }

    val input = readInput("./day04/Day04_input")
    println(part1(input))
    println(part2(input))
}
