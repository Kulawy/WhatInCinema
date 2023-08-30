package com.gerapp.whatincinema.base.errors

data class ApiThrowable(val msg: String, val code: Int) : Throwable(msg)
