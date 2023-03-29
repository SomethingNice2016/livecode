package com.somenthingnice.livecode.core.common.time

class TimeUtilsImpl : TimeUtils {

    override fun getCurrentTime() = System.currentTimeMillis()
}