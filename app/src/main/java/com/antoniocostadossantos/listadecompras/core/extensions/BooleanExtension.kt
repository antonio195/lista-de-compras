package com.antoniocostadossantos.listadecompras.core.extensions

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}