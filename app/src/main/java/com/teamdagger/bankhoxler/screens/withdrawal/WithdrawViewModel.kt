package com.teamdagger.bankhoxler.screens.withdrawal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamdagger.bankhoxler.domain.entity.AccountOwner
import com.teamdagger.bankhoxler.domain.withdrawal.WithdrawalEvent
import com.teamdagger.bankhoxler.domain.withdrawal.WithdrawalState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WithdrawViewModel : ViewModel() {

    private var _accountBalance : MutableStateFlow<AccountOwner?> = MutableStateFlow(null)
    val accountBalance get() = _accountBalance

    val withdrawalState = MutableSharedFlow<WithdrawalState>()

    fun onEvent(event: WithdrawalEvent) {
        when(event) {
            is WithdrawalEvent.ProcessWithdrawal -> {
                processWithdrawal(amount = event.amount)
            }
            is WithdrawalEvent.GetAccountData -> {
                getAccountData()
            }
        }
    }

    private fun getAccountData() {
        viewModelScope.launch {
            delay(500)
            _accountBalance.emit(
                AccountOwner(
                    name = "Fikri Haikal",
                    balance = 1240000000,
                    dateRegistered = "27 Nov 2023"
                )
            )
        }
    }

    private fun processWithdrawal(amount: Int) {
        viewModelScope.launch {
            if (_accountBalance.value != null) {
                _accountBalance.value?.let {
                    if (it.balance >= amount) {
                        _accountBalance.emit(
                            it.copy(
                                balance = it.balance - amount
                            )
                        )
                        withdrawalState.emit(WithdrawalState.WithdrawalSuccess)
                    } else {
                        withdrawalState.emit(WithdrawalState.WithdrawalError("Insufficient Balance"))
                    }
                }
            } else {
                withdrawalState.emit(WithdrawalState.WithdrawalError("Unknown Error"))
            }
        }
    }

}