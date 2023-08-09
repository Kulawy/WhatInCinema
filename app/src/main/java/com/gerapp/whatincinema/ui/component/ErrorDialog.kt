package com.gerapp.whatincinema.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gerapp.whatincinema.R
import com.gerapp.whatincinema.ui.theme.dialogText
import com.gerapp.whatincinema.ui.theme.dialogTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String?,
    msg: String?,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(modifier = modifier.padding(16.dp)) {
                if (!title.isNullOrEmpty()) {
                    Text(text = title, style = MaterialTheme.typography.dialogTitle)
                }
                if (!msg.isNullOrEmpty()) {
                    Text(text = msg, style = MaterialTheme.typography.dialogText)
                }
                Button(onClick = onConfirm) {
                    Text(text = stringResource(id = R.string.dialog_confirmation))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorDialogPreview() {
    ErrorDialog(
        onDismissRequest = {},
        onConfirm = {},
        title = "Sample Title",
        msg = "Sample Message",
    )
}
