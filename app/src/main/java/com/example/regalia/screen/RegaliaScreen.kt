package com.example.regalia.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import com.example.regalia.screen.components.LocationRail
import com.example.regalia.storage.*
import com.example.regalia.viewer.MediaViewerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegaliaScreen(
    viewModel: StorageViewModel,
    onOpenStoragePicker: () -> Unit
) {

    var railExpanded by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Regalia") },
                navigationIcon = {
                    IconButton(onClick = { railExpanded = !railExpanded }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle rail"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onOpenStoragePicker) {
                        Text("Open Storage")
                    }
                }
            )
        }
    ) { padding ->

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // -------------------------
            // LOCATION RAIL
            // -------------------------
            if (railExpanded) {
                LocationRail(
                    selected = viewModel.selectedLocation.value,
                    onLocationSelected = viewModel::onLocationSelected
                )
            }

            // -------------------------
            // GRID AREA (NEW SYSTEM)
            // -------------------------
            StorageGridHost(
                viewModel = viewModel,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }

        // -------------------------
        // VIEWER OVERLAY (GLOBAL LAYER)
        // -------------------------
        if (viewModel.viewerOpen.value) {
            MediaViewerScreen(
                files = viewModel.visibleFiles.value,
                startIndex = viewModel.viewerIndex.value,
                onClose = viewModel::closeViewer
            )
        }
    }
}