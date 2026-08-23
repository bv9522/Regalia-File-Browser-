package com.example.regalia.model

import android.net.Uri


data class FileNode(

    val name: String,

    val uri: Uri,

    val isDirectory: Boolean,

    val mimeType: String?,

    val size: Long?,

    val lastModified: Long?

)