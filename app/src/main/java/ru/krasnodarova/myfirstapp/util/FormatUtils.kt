package ru.krasnodarova.myfirstapp.util

import java.text.DecimalFormat
public fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> {
            val millions = count / 1_000_000.0
            if (millions % 1.0 == 0.0) { // Целое значение миллиона
                "${millions.toInt()}M"
            } else {
                DecimalFormat("#.#").format(millions) + "M" // Один десятичный знак после запятой
            }
        }
        count >= 10_000 -> {
            // Отображаем числа больше 10 тысяч как X K без дробей
            "${count / 1000}K"
        }
        count >= 1_000 -> {
            val thousands = count / 1000.0
            if (thousands % 1.0 == 0.0) {
                "${thousands.toInt()}K"
            } else {
                DecimalFormat("#.#").format(thousands) + "K" // Округление до одного знака после запятой
            }
        }
        else -> count.toString() // Для чисел меньше тысячи выводим оригинальное значение
    }
}





