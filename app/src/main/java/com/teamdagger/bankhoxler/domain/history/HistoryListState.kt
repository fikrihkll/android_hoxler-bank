package com.teamdagger.bankhoxler.domain.history

import com.teamdagger.bankhoxler.domain.entity.History

sealed class HistoryListState {

    data class ListLoaded(val list: List<History>): HistoryListState()
    object ListLoading: HistoryListState()
    data class ListError(val message: String): HistoryListState()

}