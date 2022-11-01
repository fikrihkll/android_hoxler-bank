package com.teamdagger.bankhoxler.domain.history

import com.teamdagger.bankhoxler.domain.entity.AccountOwner

sealed class AccountDataHistoryState {

    data class AccountDataLoaded(val data: AccountOwner): AccountDataHistoryState()
    object AccountDataLoading: AccountDataHistoryState()
    data class AccountDataError(val message: String): AccountDataHistoryState()

}
