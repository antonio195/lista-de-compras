package com.antoniocostadossantos.listadecompras.presenter.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoniocostadossantos.listadecompras.core.components.ProductInfoDialog
import com.antoniocostadossantos.listadecompras.core.components.ProductItem
import com.antoniocostadossantos.listadecompras.core.components.ProductOptions
import com.antoniocostadossantos.listadecompras.presenter.features.home.viewmodel.HomeIntent
import com.antoniocostadossantos.listadecompras.presenter.features.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState by viewModel.homeViewState.collectAsStateWithLifecycle()

    if (uiState.isAddProductBottomSheetOpen) {
        ProductInfoDialog(
            onDismissDialog = {
                viewModel.handleIntent(HomeIntent.HideAddProductBottomSheet)
            },
            product = uiState.selectedProduct
        ) { id, productName, productPrice, productCount ->
            viewModel.handleIntent(
                HomeIntent.SaveProduct(
                    id = id,
                    name = productName,
                    price = productPrice,
                    productCount = productCount
                )
            )
        }
    }

    if (uiState.isProductOptions) {
        uiState.selectedProduct?.let {
            ProductOptions(
                product = it,
                onDismissDialog = {
                    viewModel.handleIntent(HomeIntent.HideProductOptions)
                },
                onDeleteItem = { product ->
                    viewModel.handleIntent(HomeIntent.DeleteProduct(product))
                },
                onEditItem = { product ->
                    viewModel.handleIntent(HomeIntent.EditProduct(product))
                }
            )
        }
    }

    Scaffold(
        bottomBar = {
            Card() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = "Total R$ ${uiState.totalPrice}",
                        style = TextStyle(
                            fontSize = 22.sp
                        )
                    )

                    IconButton(
                        onClick = {
                            viewModel.handleIntent(HomeIntent.ShowAddProductBottomSheet)
                        }
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
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.products.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        uiState.products,
                        key = { it.id!! }
                    ) { product ->
                        ProductItem(
                            product = product,
                            increaseCount = {
                                viewModel.handleIntent(HomeIntent.IncreaseProductCount(product))
                            },
                            decreaseCount = {
                                viewModel.handleIntent(HomeIntent.DecreaseProductCount(product))
                            },
                            onLongClickListener = {
                                viewModel.handleIntent(HomeIntent.ShowProductOptions(product))
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("A lista de compras está vazia...")
                }
            }
        }
    }
}