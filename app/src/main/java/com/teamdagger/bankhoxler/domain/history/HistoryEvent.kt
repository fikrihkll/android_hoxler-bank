package com.teamdagger.bankhoxler.domain.history

sealed class HistoryEvent {
    object GetList: HistoryEvent()
    object GetAccountData: HistoryEvent()
}