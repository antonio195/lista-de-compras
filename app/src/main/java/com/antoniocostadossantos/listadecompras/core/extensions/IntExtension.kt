package com.antoniocostadossantos.listadecompras.core.extensions

fun Int?.orZero(): Int {
    return this ?: 0
}