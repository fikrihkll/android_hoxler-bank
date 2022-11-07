package com.teamdagger.bankhoxler.domain.history

import com.teamdagger.bankhoxler.domain.entity.History

sealed class HistoryEvent {
    object GetList: HistoryEvent()
    object GetAccountData: HistoryEvent()
    data class PayBill(val data: History): HistoryEvent()
}