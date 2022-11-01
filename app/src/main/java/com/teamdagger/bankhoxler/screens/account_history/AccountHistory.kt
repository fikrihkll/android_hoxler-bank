package com.teamdagger.bankhoxler.screens.account_history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teamdagger.bankhoxler.domain.history.HistoryEvent

@Composable
fun AccountInfoView(
    viewModel: HistoryViewModel = viewModel()
) {
    val data by viewModel.accountBalance.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.onEvent(HistoryEvent.GetAccountData)
        viewModel.onEvent(HistoryEvent.GetList)
    }

    if (data == null) {
        CircularProgressIndicator()
    }
    data?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Text("Name: ")
                Spacer(modifier = Modifier.width(8.dp))
                Text(it.name)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("Balance: ")
                Spacer(modifier = Modifier.width(8.dp))
                Text("\$${it.balance}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("Date Registered: ")
                Spacer(modifier = Modifier.width(8.dp))
                Text(it.dateRegistered)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(HistoryEvent.GetList)
                    },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "some", tint = MaterialTheme.colors.primary)
                }
            }

        }
    }

}

@Composable
fun HistoryListView(
    viewModel: HistoryViewModel = viewModel()
) {
    val listData by viewModel.listHistory.collectAsState()

    if (listData.isEmpty()) {
        CircularProgressIndicator()
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(count = listData.size) {
            HistoryItemView(
                amount = listData[it].amount,
                desc = listData[it].desc
            )
        }
    }
}

@Composable
fun HistoryItemView(
    amount: Int,
    desc: String
) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(
            corner = CornerSize(12.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Filled.MailOutline, contentDescription = "some", tint = MaterialTheme.colors.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("$$amount")
                Text(desc)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "some", tint = MaterialTheme.colors.secondaryVariant)
            }
        }
    }
}

@Composable
fun HistoryCount(
    viewModel: HistoryViewModel = viewModel()
) {
    val listData by viewModel.listHistory.collectAsState()
    Text(listData.size.toString())
}