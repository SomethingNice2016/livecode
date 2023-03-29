package com.somenthingnice.livecode.core.data.flow

import com.somenthingnice.livecode.core.data.common.isFailure
import com.somenthingnice.livecode.core.data.common.isLoading
import com.somenthingnice.livecode.core.data.common.isSuccess
import com.somenthingnice.livecode.core.data.entity.ApiResult
import com.somenthingnice.livecode.core.data.entity.ApiResultError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform


fun <A, B, C> combineTransaction(
    transaction0: Flow<ApiResult<A>>,
    transaction1: Flow<ApiResult<B>>,
    block: suspend (A, B) -> C
) = combineTransform(
    transaction0,
    transaction1
) { result0, result1 ->
    if (result0.isLoading() || result1.isLoading())
        emit(ApiResult.Loading)
    if (result0.isFailure() || result1.isFailure()) {
        emit(ApiResult.Failure(ApiResultError.GENERAL_ERROR, null))
        return@combineTransform
    }
    if (result0.isSuccess() && result1.isSuccess())
        emit(
            block(
                (result0 as ApiResult.Success<A>).data,
                (result1 as ApiResult.Success<B>).data
            )
        )
}

fun <A, B, C, D> combineTransaction(
    transaction0: Flow<ApiResult<A>>,
    transaction1: Flow<ApiResult<B>>,
    transaction2: Flow<ApiResult<C>>,
    block: suspend (A, B, C) -> D
) = combineTransform(
    transaction0,
    transaction1,
    transaction2
) { result0, result1, result2 ->
    if (result0.isLoading() || result1.isLoading() || result2.isLoading())
        emit(ApiResult.Loading)
    if (result0.isFailure() || result1.isFailure() || result2.isFailure()) {
        emit(ApiResult.Failure(ApiResultError.GENERAL_ERROR, null))
        return@combineTransform
    }
    if (result0.isSuccess() && result1.isSuccess())
        emit(
            block(
                (result0 as ApiResult.Success<A>).data,
                (result1 as ApiResult.Success<B>).data,
                (result2 as ApiResult.Success<C>).data
            )
        )
}

fun <A, B, C, D, E> combineTransaction(
    transaction0: Flow<ApiResult<A>>,
    transaction1: Flow<ApiResult<B>>,
    transaction2: Flow<ApiResult<C>>,
    transaction3: Flow<ApiResult<D>>,
    block: suspend (A, B, C, D) -> E
) = combineTransform(
    transaction0,
    transaction1,
    transaction2,
    transaction3
) { result0, result1, result2, result3 ->
    if (result0.isLoading() || result1.isLoading() || result2.isLoading() || result3.isLoading())
        emit(ApiResult.Loading)
    if (result0.isFailure() || result1.isFailure() || result2.isFailure() || result3.isFailure()) {
        emit(ApiResult.Failure(ApiResultError.GENERAL_ERROR, null))
        return@combineTransform
    }
    if (result0.isSuccess() && result1.isSuccess())
        emit(
            block(
                (result0 as ApiResult.Success<A>).data,
                (result1 as ApiResult.Success<B>).data,
                (result2 as ApiResult.Success<C>).data,
                (result3 as ApiResult.Success<D>).data
            )
        )
}