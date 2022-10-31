package com.teamdagger.bankhoxler.screens.registration_form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamdagger.bankhoxler.domain.registration.UIEvent
import com.teamdagger.bankhoxler.domain.registration.UIState
import com.teamdagger.bankhoxler.domain.registration.ValidationEvent
import com.teamdagger.bankhoxler.domain.registration.Validator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class RegistrationFormViewModel: ViewModel() {

    private var _uiState = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState

    val validationEvent = MutableSharedFlow<ValidationEvent>()

    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.AccountChanged -> {
                _uiState.value = _uiState.value.copy(
                    accountNumber = event.account
                )
            }
            is UIEvent.ConfirmAccountChanged -> {
                _uiState.value = _uiState.value.copy(
                    confirmAccountNumber = event.confirmAccount
                )
            }
            is UIEvent.CodeChanged -> {
                _uiState.value = _uiState.value.copy(
                    code = event.code
                )
            }
            is UIEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(
                    ownerName = event.name
                )
            }
            is UIEvent.Submit -> {
                validateInputs()
            }
        }
    }

    private fun validateInputs() {
        val accountResult = Validator.validateAccountNumber(_uiState.value.accountNumber)
        val confirmAccountResult = Validator.validateConfirmAccountNumber(_uiState.value.accountNumber, _uiState.value.confirmAccountNumber)
        val codeResult = Validator.validateBankCode(_uiState.value.code)
        val nameResult = Validator.validateOwnerName(_uiState.value.ownerName)

        _uiState.value = _uiState.value.copy(
            hasAccountError = !accountResult.status,
            hasConfirmAccountError = !confirmAccountResult.status,
            hasCodeError = !codeResult.status,
            hasNameError = !nameResult.status
        )
        val hasError = listOf(
            accountResult,
            confirmAccountResult,
            codeResult,
            nameResult
        ).any { !it.status }

        viewModelScope.launch {
            if (!hasError) {
                validationEvent.emit(ValidationEvent.Success)
            } else {
                validationEvent.emit(
                    ValidationEvent.Error(
                        message = "Every fields must be filled"
                    )
                )
            }
        }
    }

}