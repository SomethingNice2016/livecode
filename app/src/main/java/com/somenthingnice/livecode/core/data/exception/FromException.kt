package com.somenthingnice.livecode.core.data.exception

import com.somenthingnice.livecode.core.data.entity.ApiResultError
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

fun Throwable.fromException(): ApiResultError {
    return when (this) {
        is HttpException -> fromHttpException()
        is SocketTimeoutException -> ApiResultError.TIMEOUT
        is CancellationException -> ApiResultError.CANCELED
        is UnknownHostException -> ApiResultError.NETWORK_ERROR
        else -> ApiResultError.GENERAL_ERROR
    }
}

fun HttpException.fromHttpException(): ApiResultError {
    return when (code()) {
        HttpCode.NOT_FOUND -> ApiResultError.NOT_FOUND
        HttpCode.UNAUTHORIZED,
        HttpCode.FORBIDDEN -> ApiResultError.UNAUTHORIZED
        HttpCode.TO_MANY_ATTEMPT -> ApiResultError.TO_MANY_ATTEMPT
        HttpCode.SERVER_ERROR -> ApiResultError.SERVER_ERROR
        else -> ApiResultError.BAD_REQUEST
    }
}