package com.antoniocostadossantos.listadecompras.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "unitPrice")
    val unitPrice: Double,

    @ColumnInfo(name = "totalPrice")
    val totalPrice: Double,

    @ColumnInfo(name = "totalItems")
    val totalItems: Int = 0
)