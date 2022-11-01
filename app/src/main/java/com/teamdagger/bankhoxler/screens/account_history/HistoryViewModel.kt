package com.teamdagger.bankhoxler.screens.account_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamdagger.bankhoxler.domain.entity.AccountOwner
import com.teamdagger.bankhoxler.domain.entity.History
import com.teamdagger.bankhoxler.domain.history.HistoryEvent
import com.teamdagger.bankhoxler.domain.withdrawal.WithdrawalEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HistoryViewModel : ViewModel() {

    private var _listHistory = MutableStateFlow<List<History>>(emptyList())
    val listHistory get() = _listHistory.asStateFlow()

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
        }
    }

    private fun addList() {
        viewModelScope.launch {
            delay(1500)
            if (_listHistory.value.isEmpty()) {
                _listHistory.emit(
                    listOf(
                        History(48234,"Flight London - Italy"),
                        History(39532,"Flight Italy - London"),
                        History(240034,"Shopping Italy"),
                        History(4231000,"Hotel Italy"),
                    )
                )
            } else {
                _listHistory.emit(
                    listOf(
                        *_listHistory.value.toTypedArray(),
                        History(420923,"Food Italy ${Random(1021).nextInt()}"),
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
}