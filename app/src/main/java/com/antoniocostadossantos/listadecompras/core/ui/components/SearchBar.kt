package com.antoniocostadossantos.listadecompras.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onClearSearchValue: () -> Unit,
) {
    var localValue by remember { mutableStateOf(searchValue) }

    LaunchedEffect(localValue) {
        delay(200)
        if (localValue != searchValue) {
            if (localValue.isNotEmpty()) {
                onSearchValueChange(localValue)
            } else {
                onClearSearchValue()
            }
        }
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (localValue.isNotEmpty()) {
                IconButton(
                    onClick = {
                        localValue = ""
                        onClearSearchValue()
                    }
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = null
                    )
                }
            }
        },
        value = localValue,
        label = {
            Text("Pesquise os produtos listados")
        },
        onValueChange = {
            localValue = it
        },
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun SearchBarColorsPreview() {
    SearchBar(
        searchValue = "",
        onSearchValueChange = {},
        onClearSearchValue = {}
    )
}