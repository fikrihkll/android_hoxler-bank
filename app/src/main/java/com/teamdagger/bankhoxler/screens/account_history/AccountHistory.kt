package com.teamdagger.bankhoxler.screens.account_history

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teamdagger.bankhoxler.domain.entity.History
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
    Log.w("FKR-RENDER", "RENDERED LIST")
    val listData = viewModel.listHistory

    if (listData.isEmpty()) {
        CircularProgressIndicator()
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(count = listData.size, key = { index -> listData[index].id  }) {
            HistoryItemView(
                history = listData[it],
            )
        }
        item {
            HistoryCount()
        }
    }
}

@Composable
fun HistoryItemView(
    history: History,
    viewModel: HistoryViewModel = viewModel()
) {
    Log.w("FKR-RENDER", "RENDERED LIST ITEM ONLY ${history.amount}")
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(
            corner = CornerSize(12.dp)
        ),
        modifier = Modifier
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (history.isLoadingPayment) {
                    CircularProgressIndicator()
                } else {
                    Icon(imageVector = Icons.Filled.MailOutline, contentDescription = "some", tint = MaterialTheme.colors.primary)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "$${history.amount}",
                        style = TextStyle(
                            color = if (history.isPaid) Color.Green else Color.Black
                        )
                    )
                    Text(history.desc)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { viewModel.onEvent(HistoryEvent.PayBill(history)) },
                ) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "some", tint = MaterialTheme.colors.secondaryVariant)
                }
            }

            if (isExpanded) {
                Text("Expanded")
            }
        }
    }
}

@Composable
fun HistoryCount(
    viewModel: HistoryViewModel = viewModel()
) {
    val listData = viewModel.listHistory
    Text(listData.size.toString())
}