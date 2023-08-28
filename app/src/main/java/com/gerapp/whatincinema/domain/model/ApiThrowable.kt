package com.gerapp.whatincinema.domain.model

data class ApiThrowable(val msg: String, val code: Int) : Throwable(msg)
