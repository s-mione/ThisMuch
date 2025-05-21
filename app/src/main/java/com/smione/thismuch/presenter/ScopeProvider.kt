package com.smione.thismuch.presenter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

interface ScopeProvider {
    fun supervisorJobProvider(coroutineDispatcher: CoroutineDispatcher): CoroutineScope
}

class RuntimeScopeProvider : ScopeProvider {
    override fun supervisorJobProvider(coroutineDispatcher: CoroutineDispatcher) =
        CoroutineScope(SupervisorJob() + coroutineDispatcher)
}