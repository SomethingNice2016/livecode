package com.somenthingnice.livecode.core.data.entity

import com.somenthingnice.livecode.core.data.exception.fromException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

sealed class ApiResult<out T> {

    data class Success<out T>(
        val data: T,
    ) : ApiResult<T>()

    data class Failure(
        val status: ApiResultError,
        val cause: Throwable?
    ) : ApiResult<Nothing>()

    object Loading : ApiResult<Nothing>()

    companion object

}


fun <IN, OUT> wrapApiCall(
    map: (IN) -> OUT,
    call: suspend () -> IN
): Flow<ApiResult<OUT>> = flow {
    emit(ApiResult.Loading)
    val result = call.invoke()
    emit(ApiResult.Success(map(result)))
}.catch { exception ->
    ApiResult.Failure(exception.fromException(), exception)
}
