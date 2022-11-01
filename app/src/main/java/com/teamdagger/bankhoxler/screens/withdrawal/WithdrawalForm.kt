package com.teamdagger.bankhoxler.screens.withdrawal

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamdagger.bankhoxler.R

@Composable
fun WithdrawalForm(

) {
    var moneyAmount by rememberSaveable { mutableStateOf(0) }
    val image =  R.drawable.grindelwald
    var balance = 12400000000

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
        Text("Account Balance: $$balance")
        Spacer(modifier = Modifier.height(32.dp))
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
    }
}