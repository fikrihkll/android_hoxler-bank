package com.teamdagger.bankhoxler.domain.withdrawal

sealed class WithdrawalEvent {

    data class ProcessWithdrawal(val amount: Int) : WithdrawalEvent()
    object GetAccountData : WithdrawalEvent()
}
