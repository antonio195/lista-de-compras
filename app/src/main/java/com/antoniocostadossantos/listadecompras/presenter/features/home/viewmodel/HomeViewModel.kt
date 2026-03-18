package com.antoniocostadossantos.listadecompras.presenter.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.listadecompras.core.extensions.formatPrice
import com.antoniocostadossantos.listadecompras.core.extensions.normalize
import com.antoniocostadossantos.listadecompras.domain.model.Product
import com.antoniocostadossantos.listadecompras.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
            HomeIntent.ShowAddProductBottomSheet -> showAddProductBottomSheet()
            HomeIntent.LoadProducts -> loadProducts()
            HomeIntent.HideProductOptions -> hideProductOptions()
            HomeIntent.ClearToast -> clearToastMessage()
            HomeIntent.ClearSearchBar -> clearSearchBar()
            is HomeIntent.SearchNewValue -> onChangeSearchBarValue(intent.newSearchValue)
            is HomeIntent.ShowToast -> showMessage(intent.message)
            is HomeIntent.DeleteProduct -> deleteProduct(intent.product)
            is HomeIntent.EditProduct -> editProduct(intent.product)
            is HomeIntent.ShowProductOptions -> showProductOptions(intent.product)
            is HomeIntent.SaveProduct -> saveNewProduct(
                id = intent.id,
                name = intent.name,
                price = intent.price,
                productCount = intent.productCount
            )

            is HomeIntent.IncreaseProductCount -> {
                val newTotalItems = intent.product.itemCount + 1
                val totalPrice = intent.product.unitPrice * newTotalItems

                val updated = intent.product.copy(
                    itemCount = newTotalItems,
                    totalPrice = totalPrice.formatPrice()
                )

                updateProduct(updated)
            }

            is HomeIntent.DecreaseProductCount -> {
                val newTotalItems = (intent.product.itemCount - 1).coerceAtLeast(0)
                val totalPrice = intent.product.unitPrice * newTotalItems

                val updated = intent.product.copy(
                    itemCount = newTotalItems,
                    totalPrice = totalPrice.formatPrice()
                )

                updateProduct(updated)
            }
        }
    }

    private fun onChangeSearchBarValue(newSearchValue: String) {
        viewModelScope.launch {
            val sortedProducts = _homeViewState.value.products.filter {
                it.name.lowercase().normalize().contains(newSearchValue)
            }
            _homeViewState.update {
                _homeViewState.value.copy(
                    searchBarValue = newSearchValue,
                    products = sortedProducts
                )
            }
        }
    }

    private fun clearSearchBar() {
        viewModelScope.launch {
            _homeViewState.update {
                _homeViewState.value.copy(
                    searchBarValue = ""
                )
            }
            loadProducts(false)
        }
    }

    private fun showMessage(message: String) {
        viewModelScope.launch {
            _homeViewState.update {
                _homeViewState.value.copy(
                    toastMessage = message
                )
            }
        }
    }

    private fun clearToastMessage() {
        viewModelScope.launch {
            _homeViewState.update {
                _homeViewState.value.copy(
                    toastMessage = null
                )
            }
        }
    }

    private fun editProduct(product: Product) {
        viewModelScope.launch {
            _homeViewState.update {
                _homeViewState.value.copy(
                    selectedProduct = product,
                    isProductOptions = false,
                    isAddProductBottomSheetOpen = true
                )
            }
        }
    }

    private fun deleteProduct(product: Product) {
        viewModelScope.launch {
            _homeViewState.update {
                _homeViewState.value.copy(
                    selectedProduct = null,
                    isProductOptions = false
                )
            }
            productRepository.deleteProduct(product)
        }
    }

    private fun updateProduct(product: Product) {
        viewModelScope.launch {
            productRepository.newProduct(product)
        }
    }

    private fun loadProducts(shouldShowLoading: Boolean = true) {
        _homeViewState.update {
            _homeViewState.value.copy(isLoading = shouldShowLoading)
        }
        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                val sortedProducts = products.sortedBy {
                    it.name
                }
                val totalPrice = products.sumOf { it.unitPrice * it.itemCount }
                _homeViewState.update {
                    _homeViewState.value.copy(
                        isLoading = false,
                        products = sortedProducts,
                        totalPrice = totalPrice.formatPrice()
                    )
                }
            }
        }
    }

    private fun saveNewProduct(
        id: Int?,
        name: String,
        price: Double,
        productCount: Int,
    ) {
        viewModelScope.launch {
            val totalPrice = price * productCount
            val newProduct = Product(
                id = id,
                name = name,
                unitPrice = price,
                totalPrice = totalPrice.formatPrice(),
                itemCount = productCount
            )
            productRepository.newProduct(newProduct)
            hideAddProductBottomSheet()
        }
    }

    private fun showProductOptions(product: Product) {
        _homeViewState.update {
            _homeViewState.value.copy(
                isProductOptions = true,
                selectedProduct = product
            )
        }
    }

    private fun hideProductOptions() {
        _homeViewState.update {
            _homeViewState.value.copy(isProductOptions = false)
        }
    }

    private fun showAddProductBottomSheet() {
        _homeViewState.update {
            _homeViewState.value.copy(isAddProductBottomSheetOpen = true)
        }
    }

    private fun hideAddProductBottomSheet() {
        _homeViewState.update {
            _homeViewState.value.copy(
                isAddProductBottomSheetOpen = false,
                selectedProduct = null
            )
        }
    }
}