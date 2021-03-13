package com.techkingsley.remote.exceptions


class ServerException(private val exception: String) : Exception() {

    override val message: String
        get() = exception
}