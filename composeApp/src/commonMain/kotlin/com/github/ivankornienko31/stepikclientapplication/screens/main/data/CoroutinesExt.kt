package com.github.ivankornienko31.stepikclientapplication.screens.main.data

import kotlin.coroutines.cancellation.CancellationException

inline fun <T> runCatchingCancellable(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c // ВАЖНО: Пропускаем сигнал отмены дальше!
    } catch (e: Exception) {
        Result.failure(e)
    }
}