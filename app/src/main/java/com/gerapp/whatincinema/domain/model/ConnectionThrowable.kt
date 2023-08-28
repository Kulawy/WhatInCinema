package com.gerapp.whatincinema.domain.model

data class ConnectionThrowable(val msg: String) : Throwable(msg)
