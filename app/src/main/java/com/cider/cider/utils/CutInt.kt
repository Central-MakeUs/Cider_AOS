package com.cider.cider.utils

import java.util.regex.Pattern

fun cutInt(input: String): Int {
    val pattern = Pattern.compile("LV (\\d+)")
    val matcher = pattern.matcher(input)

    return if (matcher.find()) {
        matcher.group(1).toInt()
    } else {
        0
    }
}