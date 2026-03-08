package com.antoniocostadossantos.listadecompras.presenter.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoniocostadossantos.listadecompras.core.ui.components.ProductInfoDialog
import com.antoniocostadossantos.listadecompras.core.ui.components.ProductItem
import com.antoniocostadossantos.listadecompras.core.ui.components.ProductOptions
import com.antoniocostadossantos.listadecompras.core.ui.components.TotalValue
import com.antoniocostadossantos.listadecompras.core.ui.screens.DefaultEmptyListScreen
import com.antoniocostadossantos.listadecompras.core.ui.screens.DefaultLoadingScreen
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

    Scaffold(
        bottomBar = {
            if (uiState.isLoading.not()) {
                TotalValue(
                    totalPrice = uiState.totalPrice,
                    onAddButtonClick = {
                        viewModel.handleIntent(HomeIntent.ShowAddProductBottomSheet)
                    }
                )
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                DefaultLoadingScreen()
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
                DefaultEmptyListScreen()
            }
        }
    }

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
}