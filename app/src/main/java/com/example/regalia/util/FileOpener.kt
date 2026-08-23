package com.example.regalia.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object FileOpener {

    fun open(
        context: Context,
        uri: Uri
    ) {

        val mimeType =
            context.contentResolver.getType(uri)
                ?: "*/*"

        val intent = Intent(Intent.ACTION_VIEW).apply {

            setDataAndType(
                uri,
                mimeType
            )

            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        val chooser =
            Intent.createChooser(
                intent,
                "Select app"
            )

        context.startActivity(chooser)
    }
}