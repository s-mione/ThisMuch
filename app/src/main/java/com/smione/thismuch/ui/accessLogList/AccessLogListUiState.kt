package com.smione.thismuch.ui.accessLogList

import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

data class AccessLogListUiState(
    val isLoading: Boolean = false,
    val headers: List<String> = emptyList(),
    val accessLogList: List<AccessLogListElement> = emptyList(),
    val error: String? = null
)