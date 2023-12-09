package day05

import readInput
import kotlin.math.abs

fun main() {
    fun getSeeds(seedInput: String) =
        seedInput.split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.toLong() }.toList()

    fun getMapOfMaps(input: List<String>): MutableMap<Long, MutableMap<Long, MutableList<Long>>> {
        val mapOfMaps = mutableMapOf<Long, MutableMap<Long, MutableList<Long>>>()
        var mapIndex = 0L
        var keyIndex = 0L
        for (i in 2 until input.size) {
            if (input[i].isNotBlank() && input[i][0].isLetter()) {
                mapIndex += 1
                keyIndex = 0
                mapOfMaps[mapIndex] = mutableMapOf()
            } else if (input[i].isNotBlank()) {
                val listToAdd = input[i].split(" ").filter { it.isNotBlank() }.map { it.toLong() }
                mapOfMaps[mapIndex]?.getOrPut(keyIndex) { mutableListOf() }?.addAll(listToAdd)
                keyIndex += 1
            }
        }
        return mapOfMaps
    }

    fun getMinLocation(
        seeds: List<Long>,
        mapOfMaps: MutableMap<Long, MutableMap<Long, MutableList<Long>>>,
    ): Long {
        var minLong1 = Long.MAX_VALUE
        for (i in seeds.indices) {
            var value = seeds[i]
            for (map in mapOfMaps) {
                mapLoop@ for ((_, list) in map.value) {
                    val rangeSize = list[2]
                    val firstValue = list[1]
                    val secondValue = list[0]
                    if (value in firstValue..<firstValue + rangeSize) {
                        val increaseRate = abs(value - firstValue)
                        value = secondValue + increaseRate
                        break@mapLoop
                    }
                }
                if (map.key == 7L) {
                    minLong1 = minOf(value, minLong1)
                }
            }
        }
        return minLong1
    }

    fun part1(input: List<String>): Long {
        val seeds = getSeeds(input[0])
        val mapOfMaps = getMapOfMaps(input)

        return getMinLocation(seeds, mapOfMaps)
    }

    fun getAndUpdateSeeds(line: String, mapOfMaps: MutableMap<Long, MutableMap<Long, MutableList<Long>>>): Long {
        val seeds = getSeeds(line)
        val mutableSeedList = mutableListOf<Long>()
        var minLocation = Long.MAX_VALUE
        for (i in seeds.indices step 2) {
            mutableSeedList.clear()
            var secIndex = 0L
            // Check if the index does not go out of bound
            if (i + 1 < seeds.size) {
                while (secIndex < seeds[i + 1]) {
                    mutableSeedList.add(seeds[i] + secIndex)
                    secIndex += 1

                    if (mutableSeedList.size == 10000) {
                        minLocation = minOf(minLocation, getMinLocation(mutableSeedList, mapOfMaps) )
                        mutableSeedList.clear()
                    }
                }
                minLocation = minOf(minLocation, getMinLocation(mutableSeedList, mapOfMaps) )
            }
        }
        return minLocation
    }

    fun part2(input: List<String>): Long {
        val mapOfMaps = getMapOfMaps(input)
        return getAndUpdateSeeds(input[0], mapOfMaps)
    }

    val input = readInput("./day05/Day05_input")
    println(part1(input))
    println(part2(input))
}
