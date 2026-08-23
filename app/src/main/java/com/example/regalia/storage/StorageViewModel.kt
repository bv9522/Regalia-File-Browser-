package com.example.regalia.storage

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regalia.model.LocationType
import com.example.regalia.model.StorageLocation
import kotlinx.coroutines.launch

class StorageViewModel(private val repository: StorageRepository) : ViewModel() {

    // ---------------------------
    // LOCATION STATE (Rail)
    // ---------------------------
    val selectedLocation = mutableStateOf(
        StorageLocation("Recent", "🕘", LocationType.RECENT)
    )

    // ---------------------------
    // FILE STATE (FULL SOURCE)
    // This is what SAF loads into
    // ---------------------------
    private val allFiles = mutableStateOf<List<StorageNode.File>>(emptyList())
    
    val currentChildren = mutableStateOf<List<StorageNode>>(emptyList())

    // ---------------------------
    // FILTERED FILES (GRID FEED)
    // ---------------------------
    val visibleFiles = mutableStateOf<List<StorageNode.File>>(emptyList())

    // ---------------------------
    // VIEWER STATE
    // ---------------------------
    val viewerOpen = mutableStateOf(false)
    val viewerIndex = mutableStateOf(0)
    
    private val viewerFiles = mutableStateOf<List<StorageNode.File>>(emptyList())

    // ---------------------------
    // LOCATION SELECT
    // ---------------------------
    fun onLocationSelected(location: StorageLocation) {
        selectedLocation.value = location
        applyFilter(location.type)
    }

    // ---------------------------
    // LOAD FILES FROM SAF
    // (you already have logic somewhere;
    // plug it in here)
    // ---------------------------
    fun setFiles(files: List<StorageNode.File>) {
        allFiles.value = files
        applyFilter(selectedLocation.value.type)
    }
    
    fun setRootUri(uri: Uri) {
        loadChildren(uri)
    }
    
    fun loadChildren(uri: Uri) {
        viewModelScope.launch {
            val nodes = repository.queryDirectory(uri)
            currentChildren.value = nodes
            allFiles.value = nodes.filterIsInstance<StorageNode.File>()
            applyFilter(selectedLocation.value.type)
        }
    }
    
    fun setFilter(filter: StorageFilter) {
        val type = when(filter) {
            StorageFilter.ALL -> LocationType.INTERNAL
            StorageFilter.IMAGES -> LocationType.IMAGES
            StorageFilter.VIDEO -> LocationType.VIDEO
            StorageFilter.AUDIO -> LocationType.AUDIO
            StorageFilter.DOCUMENTS -> LocationType.DOCUMENTS
            StorageFilter.DOWNLOADS -> LocationType.DOWNLOADS
            StorageFilter.RECENTS -> LocationType.RECENT
        }
        applyFilter(type)
    }

    // ---------------------------
    // FILTER ENGINE (CORE LOGIC)
    // ---------------------------
    private fun applyFilter(type: LocationType) {

        val filtered = when (type) {

            LocationType.RECENT -> {
                allFiles.value // placeholder for now
            }

            LocationType.IMAGES -> {
                allFiles.value.filter {
                    it.name.endsWith(".jpg", true) ||
                            it.name.endsWith(".png", true) ||
                            it.name.endsWith(".jpeg", true)
                }
            }

            LocationType.VIDEO -> {
                allFiles.value.filter {
                    it.name.endsWith(".mp4", true) ||
                            it.name.endsWith(".mkv", true)
                }
            }

            LocationType.AUDIO -> {
                allFiles.value.filter {
                    it.name.endsWith(".mp3", true) ||
                            it.name.endsWith(".wav", true)
                }
            }

            LocationType.DOCUMENTS -> {
                allFiles.value.filter {
                    it.name.endsWith(".pdf", true) ||
                            it.name.endsWith(".doc", true) ||
                            it.name.endsWith(".txt", true)
                }
            }

            LocationType.DOWNLOADS -> {
                allFiles.value
            }

            LocationType.INTERNAL, LocationType.SAF_FOLDER, LocationType.PROVIDER -> {
                allFiles.value
            }
        }

        visibleFiles.value = filtered
    }

    // ---------------------------
    // VIEWER CONTROL (OPEN)
    // ---------------------------
    fun openViewer(index: Int) {
        viewerIndex.value = index
        viewerOpen.value = true
    }
    
    fun openViewer(files: List<StorageNode.File>, index: Int) {
        viewerFiles.value = files
        viewerIndex.value = index
        viewerOpen.value = true
    }

    fun closeViewer() {
        viewerOpen.value = false
    }
}
