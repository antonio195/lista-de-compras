package com.antoniocostadossantos.listadecompras.core.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DefaultEmptyListScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("A lista de compras está vazia...")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DefaultEmptyListScreenPreview() {
    DefaultEmptyListScreen()
}