package com.antoniocostadossantos.listadecompras.presenter.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.listadecompras.core.extensions.formatPrice
import com.antoniocostadossantos.listadecompras.domain.model.Product
import com.antoniocostadossantos.listadecompras.domain.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _homeViewState = MutableStateFlow(HomeUiViewState())
    val homeViewState: StateFlow<HomeUiViewState> = _homeViewState

    init {
        handleIntent(HomeIntent.LoadProducts)
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.HideAddProductBottomSheet -> hideAddProductBottomSheet()
            HomeIntent.LoadProducts -> loadProducts()
            HomeIntent.HideProductOptions -> hideProductOptions()
            is HomeIntent.ShowProductOptions -> showProductOptions(intent.product)
            is HomeIntent.SaveProduct -> saveNewProduct(
                id = intent.id,
                name = intent.name,
                price = intent.price,
                productCount = intent.productCount
            )

            HomeIntent.ShowAddProductBottomSheet -> showAddProductBottomSheet()
            is HomeIntent.IncreaseProductCount -> {
                val newTotalItems = intent.product.totalItems + 1
                val totalPrice = intent.product.unitPrice * newTotalItems

                val updated = intent.product.copy(
                    totalItems = newTotalItems,
                    totalPrice = totalPrice.formatPrice().toDouble()
                )

                updateProduct(updated)
            }

            is HomeIntent.DecreaseProductCount -> {
                val newTotalItems = (intent.product.totalItems - 1).coerceAtLeast(0)
                val totalPrice = intent.product.unitPrice * newTotalItems

                val updated = intent.product.copy(
                    totalItems = newTotalItems,
                    totalPrice = totalPrice.formatPrice().toDouble()
                )

                updateProduct(updated)
            }

            is HomeIntent.DeleteProduct -> deleteProduct(intent.product)
            is HomeIntent.EditProduct -> editProduct(intent.product)
        }
    }

    private fun editProduct(product: Product){
        viewModelScope.launch(IO) {
            _homeViewState.value = _homeViewState.value.copy(
                selectedProduct = product,
                isProductOptions = false,
                isAddProductBottomSheetOpen = true
            )
        }
    }

    private fun deleteProduct(product: Product) {
        viewModelScope.launch(IO) {
            _homeViewState.value = _homeViewState.value.copy(
                selectedProduct = null,
                isProductOptions = false
            )
            productRepository.deleteProduct(product)
        }
    }

    private fun updateProduct(product: Product) {
        viewModelScope.launch(IO) {
            productRepository.newProduct(product)
        }
    }

    private fun loadProducts() {
        _homeViewState.value = _homeViewState.value.copy(isLoading = true)
        viewModelScope.launch(IO) {
            productRepository.getAllProducts().collect { products ->
                val totalPrice = products.sumOf { it.unitPrice * it.totalItems }
                _homeViewState.value = _homeViewState.value.copy(
                    isLoading = false,
                    products = products,
                    totalPrice = totalPrice.formatPrice().toDouble()
                )
            }
        }
    }

    private fun saveNewProduct(
        id: Int?,
        name: String,
        price: Double,
        productCount: Int,
    ) {
        viewModelScope.launch(IO) {
            val totalPrice = price * productCount
            val newProduct = Product(
                id = id,
                name = name,
                unitPrice = price,
                totalPrice = totalPrice,
                totalItems = productCount
            )
            productRepository.newProduct(newProduct)
            hideAddProductBottomSheet()
        }
    }

    private fun showProductOptions(product: Product) {
        _homeViewState.value = _homeViewState.value.copy(
            isProductOptions = true,
            selectedProduct = product
        )
    }

    private fun hideProductOptions() {
        _homeViewState.value = _homeViewState.value.copy(isProductOptions = false)
    }

    private fun showAddProductBottomSheet() {
        _homeViewState.value = _homeViewState.value.copy(isAddProductBottomSheetOpen = true)
    }

    private fun hideAddProductBottomSheet() {
        _homeViewState.value = _homeViewState.value.copy(
            isAddProductBottomSheetOpen = false,
            selectedProduct = null
        )
    }
}