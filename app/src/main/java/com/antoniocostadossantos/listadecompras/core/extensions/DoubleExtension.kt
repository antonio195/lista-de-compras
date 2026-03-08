package com.antoniocostadossantos.listadecompras.core.extensions

import java.util.Locale

fun Double.formatPrice(): Double {
    return try {
        String.format(Locale.US, "%.2f", this).toDouble()
    } catch (_: Exception) {
        0.0
    }
}