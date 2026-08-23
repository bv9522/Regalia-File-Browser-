package com.example.regalia.storage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.regalia.util.FileOpener

@Composable
fun StorageGridHost(
    viewModel: StorageViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    StorageGridScreen(
        files = viewModel.visibleFiles.value,

        onFileClick = { index ->

            val file =
                viewModel.visibleFiles.value[index]

            FileOpener.open(
                context,
                file.uri
            )
        }
    )
}