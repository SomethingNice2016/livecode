package com.somenthingnice.livecode.core.data.flow

import com.somenthingnice.livecode.core.data.entity.ApiResult
import com.somenthingnice.livecode.core.data.entity.ApiResultError
import com.somenthingnice.livecode.core.data.exception.fromException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach

fun <T> Flow<ApiResult<T>>.onSuccess(block: suspend (T) -> Unit) = onEach { result ->
    when (result) {
        is ApiResult.Failure -> Unit
        is ApiResult.Loading -> Unit
        is ApiResult.Success -> block(result.data)
    }
}.catch { exception ->
    emit(ApiResult.Failure(exception.fromException(), exception))
}

fun <T> Flow<ApiResult<T>>.onFailure(
    block: suspend (ApiResultError, Throwable?) -> Unit
) = onEach { result ->
    when (result) {
        is ApiResult.Failure -> block(result.status, result.cause)
        is ApiResult.Loading -> Unit
        is ApiResult.Success -> Unit
    }
}.catch { exception ->
    emit(ApiResult.Failure(exception.fromException(), exception))
}


