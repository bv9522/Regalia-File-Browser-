package com.example.regalia.storage

import android.content.ContentResolver
import android.net.Uri
import android.provider.DocumentsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StorageRepository(private val contentResolver: ContentResolver) {

    suspend fun queryDirectory(uri: Uri): List<StorageNode> = withContext(Dispatchers.IO) {
        val result = mutableListOf<StorageNode>()

        val docId = DocumentsContract.getTreeDocumentId(uri)
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, docId)

        contentResolver.query(
            childrenUri,
            arrayOf(
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE
            ),
            null,
            null,
            null
        )?.use { cursor ->

            val idIndex = cursor.getColumnIndex(
                DocumentsContract.Document.COLUMN_DOCUMENT_ID
            )
            val nameIndex = cursor.getColumnIndex(
                DocumentsContract.Document.COLUMN_DISPLAY_NAME
            )
            val mimeIndex = cursor.getColumnIndex(
                DocumentsContract.Document.COLUMN_MIME_TYPE
            )

            while (cursor.moveToNext()) {
                val docIdStr = cursor.getString(idIndex)
                val name = cursor.getString(nameIndex)
                val mime = cursor.getString(mimeIndex)

                val childUri = DocumentsContract.buildDocumentUriUsingTree(uri, docIdStr)

                if (mime == DocumentsContract.Document.MIME_TYPE_DIR) {
                    result.add(StorageNode.Folder(childUri, name))
                } else {
                    result.add(StorageNode.File(childUri, name, mime))
                }
            }
        }

        result
    }
}
