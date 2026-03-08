package com.antoniocostadossantos.listadecompras.presenter.features.home.viewmodel

import com.antoniocostadossantos.listadecompras.domain.model.Product

sealed class HomeIntent {
    object LoadProducts : HomeIntent()
    data object ShowAddProductBottomSheet : HomeIntent()
    data object HideAddProductBottomSheet : HomeIntent()
    data class ShowProductOptions(val product: Product) : HomeIntent()
    data object HideProductOptions : HomeIntent()
    data class ShowToast(val message: String) : HomeIntent()
    data object ClearToast : HomeIntent()
    data class DeleteProduct(val product: Product) : HomeIntent()
    data class EditProduct(val product: Product) : HomeIntent()
    data class IncreaseProductCount(val product: Product) : HomeIntent()
    data class DecreaseProductCount(val product: Product) : HomeIntent()
    data class SaveProduct(
        val id: Int?,
        val name: String,
        val price: Double,
        val productCount: Int,
    ) : HomeIntent()
}