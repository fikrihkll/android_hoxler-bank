package com.teamdagger.bankhoxler.screens.account_history

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamdagger.bankhoxler.domain.entity.AccountOwner
import com.teamdagger.bankhoxler.domain.entity.History
import com.teamdagger.bankhoxler.domain.history.HistoryEvent
import com.teamdagger.bankhoxler.domain.withdrawal.WithdrawalEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HistoryViewModel
@Inject
constructor(): ViewModel() {

    private var _listHistory = mutableStateListOf<History>()
    val listHistory get() = _listHistory

    private var _accountBalance : MutableStateFlow<AccountOwner?> = MutableStateFlow(null)
    val accountBalance get() = _accountBalance

    fun onEvent(event: HistoryEvent) {
        when(event) {
            is HistoryEvent.GetAccountData -> {
                getAccountData()
            }
            is HistoryEvent.GetList -> {
                addList()
            }
            is HistoryEvent.PayBill -> {
                viewModelScope.launch(Dispatchers.IO) {
                    processPayment(event.data)
                }
            }
        }
    }

    private fun addList() {
        viewModelScope.launch {
            delay(1500)
            if (_listHistory.isEmpty()) {
                _listHistory.addAll(
                    listOf(
                        History(1, 48234,"Flight London - Italy"),
                        History(2, 39532,"Flight Italy - London"),
                        History(3, 240034,"Shopping Italy"),
                        History(4, 4231000,"Hotel Italy"),
                    )
                )
            } else {
                _listHistory.addAll(
                    listOf(
                        History(generateRandomId(),98234,"Food Italy ${Random(1021).nextInt()}"),
                    )
                )
            }
        }
    }

    private fun getAccountData() {
        viewModelScope.launch {
            delay(1500)
            _accountBalance.emit(
                AccountOwner(
                    name = "Fikri Haikal",
                    balance = 1240000000,
                    dateRegistered = "27 Nov 2023"
                )
            )
        }
    }

    private suspend fun processPayment(data: History) {
        val index = _listHistory.indexOfFirst { it.id == data.id }
        if (index >= 0) {
            _listHistory[index] = _listHistory[index].copy(isLoadingPayment = true)
            delay(3000)
            _listHistory[index] = _listHistory[index].copy(isLoadingPayment = false, isPaid = true)
        }
    }

    private fun generateRandomId(): Int {
        val id = Random(10210).nextInt()
        val isSimilar = _listHistory.any { it.id == id }
        return if (!isSimilar) id else generateRandomId()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}