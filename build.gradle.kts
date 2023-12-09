plugins {
    kotlin("jvm") version "1.9.20"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }

    task("generateNextDay") {
        println("projectDir: $projectDir")
        doLast {
            val prevDayNum = File("$projectDir/src").listFiles { file ->
                file.isDirectory && file.name.startsWith("day")
            }?.maxOfOrNull {
                it.name.removePrefix("day").toInt()
            } ?: 0
            println(prevDayNum)
            val newDayNum = String.format("%02d", prevDayNum + 1)
            mkdir("$projectDir/src/day$newDayNum")
            File("$projectDir/src/day$newDayNum", "Day$newDayNum.kt").writeText(
                """package day$newDayNum

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("src/Day$newDayNum/_input.txt")
    println(part1(input))
    println(part2(input))
}
"""
            )
            val inputFileName = "Day$newDayNum"+"_input.txt"
            File("$projectDir/src/day$newDayNum", inputFileName).writeText("")
        }
    }
}