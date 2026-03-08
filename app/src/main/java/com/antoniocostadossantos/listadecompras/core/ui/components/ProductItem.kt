package com.antoniocostadossantos.listadecompras.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoniocostadossantos.listadecompras.domain.model.Product

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    increaseCount: () -> Unit,
    decreaseCount: () -> Unit,
    onLongClickListener: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    onLongClickListener()
                }
            ),
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = product.name,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                ),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .size(2.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Spacer(
                modifier = Modifier
                    .size(4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                ) {

                    Text("Unitário - R$ ${product.unitPrice}")
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                    Text("Quantidade: ${product.itemCount}")
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                    Text("Total - R$ ${product.totalPrice}")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier
                            .background(Color.Gray, shape = CircleShape),
                        onClick = {
                            decreaseCount()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                    IconButton(
                        modifier = Modifier
                            .background(Color.Gray, shape = CircleShape),
                        onClick = {
                            increaseCount()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowUp,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductItemPreview() {
    ProductItem(
        product = Product(
            id = 0,
            name = "Cebola",
            unitPrice = 10.22,
            totalPrice = 10.22,
            itemCount = 23
        ),
        increaseCount = {},
        decreaseCount = {},
        onLongClickListener = {}
    )
}