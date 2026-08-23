package com.example.regalia.model

import android.net.Uri

data class StorageLocation(
    val name: String,
    val icon: String,
    val type: LocationType,
    val uri: Uri? = null
)

enum class LocationType {
    RECENT,
    IMAGES,
    VIDEO,
    AUDIO,
    DOCUMENTS,
    DOWNLOADS,
    INTERNAL,
    SAF_FOLDER,
    PROVIDER
}