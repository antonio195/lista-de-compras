package com.antoniocostadossantos.listadecompras.core.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoniocostadossantos.listadecompras.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductOptions(
    modifier: Modifier = Modifier,
    product: Product,
    onDismissDialog: () -> Unit,
    onDeleteItem: (Product) -> Unit,
    onEditItem: (Product) -> Unit,
) {
    val context = LocalContext.current

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismissDialog()
        },
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = product.name,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(
                    modifier = Modifier
                        .size(16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    OutlinedButton(
                        onClick = {
                            onEditItem(product)
                        }
                    ) {
                        Text(
                            text = "Editar",
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                    Button(
                        modifier = Modifier,
                        onClick = {
                            onDeleteItem(product)
                            Toast.makeText(
                                context,
                                "Produto deletado",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        shape = ButtonDefaults.outlinedShape,
                        colors = ButtonDefaults.outlinedButtonColors(),
                        border = ButtonDefaults.outlinedButtonBorder(true)
                    ) {
                        Text(
                            text = "Deletar",
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductOptionsPreview() {
    ProductOptions(
        onDismissDialog = {},
        onDeleteItem = {},
        onEditItem = {},
        product = Product(
            id = 0,
            name = "Cebola",
            unitPrice = 2.99,
            totalPrice = 2.99,
            totalItems = 2
        )
    )
}