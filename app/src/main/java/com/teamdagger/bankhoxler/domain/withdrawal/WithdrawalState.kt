package com.teamdagger.bankhoxler.domain.withdrawal

sealed class WithdrawalState {

    object WithdrawalSuccess : WithdrawalState()
    data class WithdrawalError(val message: String) : WithdrawalState()

}