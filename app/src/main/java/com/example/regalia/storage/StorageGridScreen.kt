package com.example.regalia.storage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun StorageGridScreen(
    files: List<StorageNode.File>,
    onFileClick: (Int) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        items(files) { file ->

            val index = files.indexOf(file)

            AsyncImage(
                model = file.uri,
                contentDescription = file.name,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable {
                        onFileClick(index)
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}