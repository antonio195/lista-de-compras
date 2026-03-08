package com.antoniocostadossantos.listadecompras.core.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoniocostadossantos.listadecompras.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfoDialog(
    modifier: Modifier = Modifier,
    product: Product?,
    onDismissDialog: () -> Unit,
    onSaveButtonClick: (
        productId: Int?,
        productName: String,
        productPrice: Double,
        productCount: Int
    ) -> Unit,
) {

    val context = LocalContext.current

    BasicAlertDialog(
        onDismissRequest = onDismissDialog
    ) {
        Card {
            Column(
                modifier = modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val defaultPrice = if (product?.unitPrice == null) {
                    ""
                } else {
                    "${product.unitPrice}"
                }
                val defaultCount = if (product?.itemCount == null) {
                    ""
                } else {
                    "${product.itemCount}"
                }
                var productName by remember { mutableStateOf(product?.name.orEmpty()) }
                var productPrice by remember { mutableStateOf(defaultPrice) }
                var productCount by remember { mutableStateOf(defaultCount) }

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Adicionar novo produto",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 22.sp,
                    )
                )

                Spacer(
                    modifier = Modifier
                        .size(size = 16.dp)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Nome do produto") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                Spacer(
                    modifier = Modifier
                        .size(size = 16.dp)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Preço do produto") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(
                    modifier = Modifier
                        .size(size = 16.dp)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = productCount,
                    onValueChange = { productCount = it },
                    label = { Text("Quantidade do produto") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(
                    modifier = Modifier
                        .size(size = 16.dp)
                )

                OutlinedButton(
                    onClick = {
                        val newProductPrice = if (productPrice.isEmpty()) {
                            0.0
                        } else {
                            productPrice.toDouble()
                        }

                        val newProductCount = if (productCount.isEmpty()) {
                            0
                        } else {
                            productCount.toInt()
                        }
                        if (productName.isNotEmpty()) {
                            onSaveButtonClick(
                                product?.id,
                                productName,
                                newProductPrice,
                                newProductCount
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Insira o nome do produto",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Salvar",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp,
                        )
                    )
                }
            }
        }
    }
}