package com.example.regalia

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.regalia.screen.RegaliaScreen
import com.example.regalia.storage.StorageRepository
import com.example.regalia.storage.StorageViewModel
import com.example.regalia.ui.theme.RegaliaTheme

class MainActivity : ComponentActivity() {

    private lateinit var storageViewModel: StorageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = StorageRepository(contentResolver)
        storageViewModel = StorageViewModel(repository)

        val openTreeLauncher =
            registerForActivityResult(
                ActivityResultContracts.OpenDocumentTree()
            ) { uri: Uri? ->
                uri?.let {
                    contentResolver.takePersistableUriPermission(
                        it,
                        android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                    storageViewModel.setRootUri(it)
                }
            }

        setContent {
            RegaliaTheme {
                RegaliaScreen(
                    viewModel = storageViewModel,
                    onOpenStoragePicker = {
                        openTreeLauncher.launch(null)
                    }
                )
            }
        }
    }
}