package com.antoniocostadossantos.listadecompras.presenter.features.home.viewmodel

import com.antoniocostadossantos.listadecompras.domain.model.Product

data class HomeUiViewState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val isAddProductBottomSheetOpen: Boolean = false,
    val isProductOptions: Boolean = false,
    val totalPrice: Double = 0.0,
    val selectedProduct: Product? = null,
    val toastMessage: String? = null,
)