package com.antoniocostadossantos.listadecompras.core.extensions

import java.text.Normalizer

fun String.normalize(): String {
    return Normalizer
        .normalize(this, Normalizer.Form.NFD)
        .replace(Regex("\\p{Mn}+"), "")
}