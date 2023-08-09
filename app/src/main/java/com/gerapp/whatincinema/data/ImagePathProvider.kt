package com.gerapp.whatincinema.data

object ImagePathProvider {

    fun provideOriginalQualityPosterPath(imagePathSuffix: String) =
        "${DataConstants.IMAGES_ORIGINAL_URL.dropLast(1)}$imagePathSuffix"

    fun provideLowQualityPosterPath(imagePathSuffix: String) =
        "${DataConstants.IMAGES_URL.dropLast(1)}$imagePathSuffix"
}
