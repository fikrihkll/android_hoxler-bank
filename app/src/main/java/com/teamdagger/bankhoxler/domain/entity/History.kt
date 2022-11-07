package com.teamdagger.bankhoxler.domain.entity

data class History(
    val id: Int,
    val amount: Int,
    val desc: String,
    val isPaid: Boolean = false,
    val isLoadingPayment: Boolean = false
)
