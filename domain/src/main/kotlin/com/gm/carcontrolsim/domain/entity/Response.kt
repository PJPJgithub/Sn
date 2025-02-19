/*
 * Copyright (C) GM Global Technology Operations LLC 2024.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.gm.carcontrolsim.domain.entity

sealed class Response<out T : Any> {
    data class Success<out T : Any>(val value: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
}

inline fun <reified T : Any> Response<T>.onSuccess(
    executor: (value: T) -> Unit
): Response<T> {
    if (this is Response.Success) executor(value)
    return this
}

inline fun <reified T : Any> Response<T>.onError(
    executor: (message: String?) -> Unit
): Response<T> {
    if (this is Response.Error) executor(message)
    return this
}
