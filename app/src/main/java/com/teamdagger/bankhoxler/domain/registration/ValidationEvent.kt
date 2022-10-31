package com.teamdagger.bankhoxler.domain.registration

sealed class ValidationEvent {
    object Success: ValidationEvent()
    data class Error(val message: String): ValidationEvent()
}