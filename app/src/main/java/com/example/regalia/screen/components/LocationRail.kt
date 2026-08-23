package com.example.regalia.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.regalia.model.LocationType
import com.example.regalia.model.StorageLocation

@Composable
fun LocationRail(
    selected: StorageLocation? = null,
    onLocationSelected: (StorageLocation) -> Unit
) {

    val locations = listOf(
        StorageLocation("Recent", "🕘", LocationType.RECENT),
        StorageLocation("Images", "🖼", LocationType.IMAGES),
        StorageLocation("Video", "🎬", LocationType.VIDEO),
        StorageLocation("Audio", "🎵", LocationType.AUDIO),
        StorageLocation("Documents", "📄", LocationType.DOCUMENTS),
        StorageLocation("Downloads", "⬇", LocationType.DOWNLOADS)
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(90.dp)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        locations.forEach { location ->

            val isSelected = selected?.type == location.type

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (isSelected)
                            Color.White.copy(alpha = 0.15f)
                        else
                            Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onLocationSelected(location)
                    }
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = location.icon,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = location.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) Color.White else Color.Gray
                )
            }
        }
    }
}