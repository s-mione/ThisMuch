package com.smione.thismuch.ui.accessLogList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.smione.thismuch.R
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

@Composable
fun AccessLogListScreen(uiState: AccessLogListUiState, onDeleteAll: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onDeleteAll() }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.floating_button_delete_all_content_description)
                )
            }
        }
    ) { paddingValues ->
        AccessLogListContent(uiState = uiState, paddingValues = paddingValues)
    }
}

@Composable
private fun AccessLogListContent(uiState: AccessLogListUiState, paddingValues: PaddingValues) {
    if (uiState.isLoading) {
        Text(text = stringResource(R.string.list_loading))
    } else if (uiState.error != null) {
        Text(text = "${stringResource(R.string.list_error)}: ${uiState.error}")
    } else {
        ListAccessLogList(uiState)
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ListAccessLogList(uiState: AccessLogListUiState) {
    LazyColumn(Modifier.fillMaxWidth()) {
        stickyHeader {
            ListAccessLogHeaders(uiState.headers)
        }
        items(uiState.accessLogList) {
            ListAccessLogElementList(it)
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ListAccessLogHeaders(headers: List<String>, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth()) {
        headers.forEach {
            Text(it, Modifier.weight(1f))
        }
    }
}

@Composable
fun ListAccessLogElementList(accessLogElement: AccessLogListElement,
                             modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth()) {
        Text(text = accessLogElement.index.toString(), Modifier.weight(1f))
        Text(text = accessLogElement.timeOn.toString(), Modifier.weight(1f))
        Text(text = accessLogElement.timeOff.toString(), Modifier.weight(1f))
        Text(text = accessLogElement.totalTime.toString(), Modifier.weight(1f))
    }
}