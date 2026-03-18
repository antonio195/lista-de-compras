package com.antoniocostadossantos.listadecompras.presenter.features.home.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoniocostadossantos.listadecompras.core.extensions.orFalse
import com.antoniocostadossantos.listadecompras.core.ui.components.ProductInfoDialog
import com.antoniocostadossantos.listadecompras.core.ui.components.ProductItem
import com.antoniocostadossantos.listadecompras.core.ui.components.ProductOptions
import com.antoniocostadossantos.listadecompras.core.ui.components.SearchBar
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
    val context = LocalContext.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                DefaultLoadingScreen()
            } else if (uiState.products.isNotEmpty()) {
                SearchBar(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    searchValue = uiState.searchBarValue,
                    onSearchValueChange = {
                        viewModel.handleIntent(HomeIntent.SearchNewValue(it))
                    },
                    onClearSearchValue = {
                        viewModel.handleIntent(HomeIntent.ClearSearchBar)
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = uiState.products,
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
                TotalValue(
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    totalPrice = uiState.totalPrice,
                    onAddButtonClick = {
                        viewModel.handleIntent(HomeIntent.ShowAddProductBottomSheet)
                    }
                )
            } else {
                SearchBar(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    searchValue = uiState.searchBarValue,
                    onSearchValueChange = {
                        viewModel.handleIntent(HomeIntent.SearchNewValue(it))
                    },
                    onClearSearchValue = {
                        viewModel.handleIntent(HomeIntent.ClearSearchBar)
                    }
                )
                DefaultEmptyListScreen()
            }
        }
    }

    if (uiState.isAddProductBottomSheetOpen) {
        ProductInfoDialog(
            onDismissDialog = {
                viewModel.handleIntent(HomeIntent.HideAddProductBottomSheet)
            },
            product = uiState.selectedProduct,
            showWarningMessage = {
                viewModel.handleIntent(HomeIntent.ShowToast(it))
            }
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
        uiState.selectedProduct?.let { product ->
            ProductOptions(
                product = product,
                onDismissDialog = {
                    viewModel.handleIntent(HomeIntent.HideProductOptions)
                },
                onDeleteItem = { product ->
                    viewModel.handleIntent(HomeIntent.DeleteProduct(product))
                },
                onEditItem = { product ->
                    viewModel.handleIntent(HomeIntent.EditProduct(product))
                },
                showToastMessage = {
                    viewModel.handleIntent(HomeIntent.ShowToast(it))
                }
            )
        }
    }

    if (uiState.toastMessage?.isNotEmpty().orFalse()) {
        Toast.makeText(context, uiState.toastMessage, Toast.LENGTH_SHORT).show()
        viewModel.handleIntent(HomeIntent.ClearToast)
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}