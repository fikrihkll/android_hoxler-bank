package com.teamdagger.bankhoxler.screens.registration_form

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teamdagger.bankhoxler.domain.registration.UIEvent
import com.teamdagger.bankhoxler.domain.registration.ValidationEvent

@Composable
fun RegistrationForm(
    registrationFormViewModel: RegistrationFormViewModel = viewModel()
) {
    val state = registrationFormViewModel.uiState.value
    val context = LocalContext.current
    val localFocus = LocalFocusManager.current

    LaunchedEffect(key1 = context) {
        registrationFormViewModel.validationEvent.collect {
            when(it) {
                is ValidationEvent.Success -> {
                    Toast
                        .makeText(context, "Input ok", Toast.LENGTH_SHORT)
                        .show()
                }
                is ValidationEvent.Error -> {
                    Toast
                        .makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HoxlerInputField(
            label = "Account Number",
            isError = state.hasAccountError,
            imeAction = ImeAction.Next,
            onTextChanged = {
                registrationFormViewModel.onEvent(UIEvent.AccountChanged(it))
            }, onNext = {
                localFocus.moveFocus(FocusDirection.Down)
            }, onDone = {

            }
        )
        HoxlerInputField(
            label = "Confirmation Account",
            isError = state.hasConfirmAccountError,
            imeAction = ImeAction.Next,
            onTextChanged = {
                registrationFormViewModel.onEvent(UIEvent.ConfirmAccountChanged(it))
            }, onNext = {
                localFocus.moveFocus(FocusDirection.Down)
            }, onDone = {

            }
        )
        HoxlerInputField(
            label = "Bank Code",
            isError = state.hasCodeError,
            imeAction = ImeAction.Next,
            onTextChanged = {
                registrationFormViewModel.onEvent(UIEvent.CodeChanged(it))
            }, onNext = {
                localFocus.moveFocus(FocusDirection.Down)
            }, onDone = {

            }
        )
        HoxlerInputField(
            label = "Owner Name",
            isError = state.hasNameError,
            imeAction = ImeAction.Done,
            onTextChanged = {
                registrationFormViewModel.onEvent(UIEvent.NameChanged(it))
            }, onNext = {

            }, onDone = {
                localFocus.clearFocus()
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                registrationFormViewModel.onEvent(
                    UIEvent.Submit
                )
            }
        ) {
            Text(
                "Submit"
            )
        }
    }
}