package com.example.regalia.model

import android.net.Uri

enum class MediaType {
    IMAGE,
    VIDEO,
    UNKNOWN
}

data class MediaItem(
    val uri: Uri,
    val name: String,
    val type: MediaType
)