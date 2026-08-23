package com.example.regalia.storage

import android.net.Uri

sealed class StorageNode {
    data class Folder(
        val uri: Uri,
        val name: String,
        val childrenLoaded: Boolean = false
    ) : StorageNode()

    data class File(
        val uri: Uri,
        val name: String,
        val mimeType: String?
    ) : StorageNode()
}