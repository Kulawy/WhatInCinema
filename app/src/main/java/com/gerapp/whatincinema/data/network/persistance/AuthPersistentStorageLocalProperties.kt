package com.gerapp.whatincinema.data.network.persistance

import com.gerapp.whatincinema.BuildConfig
import javax.inject.Inject

class AuthPersistentStorageLocalProperties @Inject constructor() :
    AuthPersistentStorage {
    override val accessToken: String = BuildConfig.AUTH_TOKEN
}
