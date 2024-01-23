package com.example.myapplication.enums

enum class Difficulty(
    val range: IntRange,
    val wrongAnswerMultiplier: IntRange,
    val operations: List<Pair<String, (Int, Int) -> Int>>,
    val timer : Long
) {
    EASY(
        range = 1..10,
        wrongAnswerMultiplier = 1..2,
        operations = listOf(
            "+" to { a, b -> a + b }),
        timer = 120_000
    ),

    MEDIUM(
        range = 1..50,
        wrongAnswerMultiplier = 2..5,
        operations = listOf(
            "+" to { a, b -> a + b },
            "-" to { a, b -> a - b }
        ),
        timer = 300_000
    ),

    HARD(
        range = 1..100,
        wrongAnswerMultiplier = 5..10,
        operations = listOf(
            "+" to { a, b -> a + b },
            "-" to { a, b -> a - b },
            "*" to { a, b -> a * b },
            "/" to { a, b -> if (b != 0) a / b else 0 }
        ),
        timer = 600_000
    ),

    EMPTY(
        range = 0..0,
        wrongAnswerMultiplier = 0..0,
        operations = listOf("empty" to {a, b -> a - b}),
        timer = 0
    )
}