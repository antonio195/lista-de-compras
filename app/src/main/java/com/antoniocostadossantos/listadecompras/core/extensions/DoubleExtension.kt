package com.antoniocostadossantos.listadecompras.core.extensions

import java.util.Locale

fun Double.formatPrice(): String {
    return String.format(Locale.US, "%.2f", this)
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}