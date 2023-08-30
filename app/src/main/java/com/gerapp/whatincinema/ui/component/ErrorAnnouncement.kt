package com.gerapp.whatincinema.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gerapp.whatincinema.R.string
import com.gerapp.whatincinema.ui.theme.WhatInCinemaTheme
import com.gerapp.whatincinema.ui.theme.dialogText
import com.gerapp.whatincinema.ui.theme.dialogTitle
import com.gerapp.whatincinema.ui.utils.LocalDim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorAnnouncement(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    title: String?,
    msg: String?,
) {
    Surface(
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.dialogTitle,
                    modifier = modifier.align(Alignment.CenterHorizontally),
                )
            }
            if (!msg.isNullOrEmpty()) {
                Text(
                    text = msg,
                    style = MaterialTheme.typography.dialogText,
                    modifier = modifier.align(Alignment.CenterHorizontally),
                )
            }
            Spacer(modifier = modifier.size(LocalDim.current.spaceMedium))
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(text = stringResource(id = string.dialog_try_again))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorAnnouncementPreview() {
    WhatInCinemaTheme {
        ErrorAnnouncement(
            onConfirm = {},
            title = "Sample Title",
            msg = "Sample Message",
        )
    }
}
