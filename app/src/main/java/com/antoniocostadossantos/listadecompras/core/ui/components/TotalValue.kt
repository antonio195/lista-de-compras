package com.antoniocostadossantos.listadecompras.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TotalValue(
    modifier: Modifier = Modifier,
    totalPrice: Double,
    onAddButtonClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Total R$ $totalPrice",
                style = TextStyle(
                    fontSize = 22.sp
                )
            )

            IconButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                onClick = onAddButtonClick
            ) {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.DarkGray),
                    painter = rememberVectorPainter(Icons.Outlined.Add),
                    contentDescription = "Add Note",
                )
            }
        }
    }
}