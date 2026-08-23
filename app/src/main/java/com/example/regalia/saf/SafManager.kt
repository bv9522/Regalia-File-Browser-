package com.example.regalia.saf

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.example.regalia.model.FileNode
class SafManager(private val context: Context) {

    companion object {
        const val PREFS = "saf_prefs"
        const val KEY_TREE_URI = "tree_uri"
    }

    fun openTreePicker(): Intent {
        return Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                        Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            )
        }
    }

    fun saveTreeUri(uri: Uri) {
        context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(KEY_TREE_URI, uri.toString())
            .apply()
    }

    fun getSavedTreeUri(): Uri? {
        val uri =
            context.getSharedPreferences(
                PREFS,
                Context.MODE_PRIVATE
            )
                .getString(KEY_TREE_URI, null)

        return uri?.let { Uri.parse(it) }
    }

    fun takePermission(uri: Uri) {
        val flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        context.contentResolver
            .takePersistableUriPermission(uri, flags)
    }

    fun listFiles(treeUri: Uri): List<DocumentFile> {

        val folder =
            DocumentFile.fromTreeUri(
                context,
                treeUri
            )

        return folder
            ?.listFiles()
            ?.toList()
            ?: emptyList()
    }

    fun getFileNodes(uri: Uri): List<FileNode> {

        val folder =
            DocumentFile.fromTreeUri(
                context,
                uri
            )

        return folder
            ?.listFiles()
            ?.map {

                FileNode(

                    name = it.name ?: "Unknown",

                    uri = it.uri,

                    isDirectory = it.isDirectory,

                    mimeType = it.type,

                    size = it.length(),

                    lastModified = it.lastModified()

                )

            }
            ?: emptyList()
    }
}
