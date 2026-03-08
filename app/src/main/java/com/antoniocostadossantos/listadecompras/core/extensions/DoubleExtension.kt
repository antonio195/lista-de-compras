package com.antoniocostadossantos.listadecompras.core.extensions

import java.util.Locale

fun Double.formatPrice(): String {
    return String.format(Locale.US, "%.2f", this)
}