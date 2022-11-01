package com.teamdagger.bankhoxler.screens.withdrawal

import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamdagger.bankhoxler.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teamdagger.bankhoxler.domain.withdrawal.WithdrawalEvent
import com.teamdagger.bankhoxler.domain.withdrawal.WithdrawalState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WithdrawalForm(
    viewModel: WithdrawViewModel = viewModel()
) {
    var moneyAmount by rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current

    Log.w("FKR-RENDER", "JUST RENDERED")

    LaunchedEffect(key1 = context) {
        Log.w("FKR-RENDER", "LAUNCHED EFFECT")
        viewModel.withdrawalState.collect {
            when (it) {
                is WithdrawalState.WithdrawalSuccess -> {
                    Toast
                        .makeText(context, "Your money is coming", Toast.LENGTH_LONG)
                        .show()
                }
                is WithdrawalState.WithdrawalError -> {
                    Toast
                        .makeText(context, "Failure: ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (moneyAmount > 0) {
                        moneyAmount -= 100
                    }
                },
            ) {
                Text("[-]")
            }
            Surface(
                modifier = Modifier
                    .padding(all = 24.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(
                            corner = CornerSize(12.dp),
                        ),
                    )
                    .background(
                        color = Color.Cyan,
                        shape = RoundedCornerShape(corner = CornerSize(12.dp))
                    )
            ) {
                Text(
                    "$$moneyAmount"
                )
            }
            Button(
                onClick = {
                    moneyAmount += 100
                },
            ) {
                Text("[+]")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(WithdrawalEvent.ProcessWithdrawal(moneyAmount))
            }
        ) {
            Text("Process")
        }
    }
}

@Composable
fun BalanceInfo(
    viewModel: WithdrawViewModel = viewModel()
) {
    val image =  R.drawable.grindelwald
    val balance = viewModel.accountBalance.collectAsState()
    val context = LocalContext.current

    Log.w("FKR-RENDER", "JUST RENDERED BALANCE")

    LaunchedEffect(key1 = context) {
        Log.w("FKR-RENDER", "JUST RENDERED BALANCE")
        viewModel.onEvent(WithdrawalEvent.GetAccountData)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = "Activity Image",
            painter = painterResource(id = image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(86.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (balance.value != null) {
            balance.value?.let {
                Text("$${it.balance}")
            }
        } else {
            Text("...")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}