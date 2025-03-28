package com.smione.thismuch.ui.accessLogList

import com.smione.thismuch.model.element.AccessLogListElement

data class AccessLogListUiState(
    val isLoading: Boolean = false,
    val headers: List<String> = emptyList(),
    val accessLogList: List<AccessLogListElement> = emptyList(),
    val error: String? = null
)