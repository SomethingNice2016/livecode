package com.somenthingnice.livecode.core.data.common

import com.somenthingnice.livecode.core.data.entity.ApiResult
import com.somenthingnice.livecode.core.data.entity.ApiResultError

fun <T> ApiResult<T>.isSuccess() = this is ApiResult.Success<T>

fun <T> ApiResult<T>.isFailure() = this is ApiResult.Failure

fun <T> ApiResult<T>.isLoading() = this is ApiResult.Loading

suspend fun <T> ApiResult<T>.onSuccess(block: suspend (T) -> Unit): ApiResult<T> {
    when (this) {
        is ApiResult.Failure -> Unit
        is ApiResult.Loading -> Unit
        is ApiResult.Success -> block(data)
    }
    return this
}

suspend fun <T> ApiResult<T>.onFailure(block: suspend (ApiResultError, Throwable?) -> Unit): ApiResult<T> {
    when (this) {
        is ApiResult.Failure -> block(status, cause)
        is ApiResult.Loading -> Unit
        is ApiResult.Success -> Unit
    }
    return this
}

suspend fun <T> ApiResult<T>.onLoading(block: suspend () -> Unit): ApiResult<T> {
    when (this) {
        is ApiResult.Failure -> Unit
        is ApiResult.Loading -> block()
        is ApiResult.Success -> Unit
    }
    return this
}