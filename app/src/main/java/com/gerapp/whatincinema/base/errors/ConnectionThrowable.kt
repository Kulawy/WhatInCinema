package com.gerapp.whatincinema.base.errors

data class ConnectionThrowable(val msg: String) : Throwable(msg)
