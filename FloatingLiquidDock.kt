package com.example.components.glass

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GlassConfig

data class DockItem(
    val id: String,
    val icon: ImageVector,
    val title: String
)

/**
 * Modern Floating Fluid Glass Dock.
 * Features realistic optics, glossy pill shape, and smooth zero-lag active transitions.
 */
@Composable
fun FloatingLiquidDock(
    selectedId: String,
    onItemSelected: (String) -> Unit,
    config: GlassConfig,
    modifier: Modifier = Modifier
) {
    val items = remember {
        listOf(
            DockItem("glass_btn", Icons.Default.SmartButton, "Buton"),
            DockItem("glass_card", Icons.Default.Layers, "Kart"),
            DockItem("glass_dock", Icons.Default.ViewCarousel, "Dock"),
            DockItem("glass_sphere", Icons.Default.RadioButtonChecked, "Küre")
        )
    }

    Box(
        modifier = modifier
            .testTag("floating_liquid_dock")
            .height(64.dp)
            .liquidGlassSurface(
                config = config,
                cornerRadius = 32.dp,
                borderWidth = 1.5.dp
            )
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items.forEach { item ->
                val isSelected = item.id == selectedId
                val itemBgColor by animateColorAsState(
                    targetValue = if (isSelected) config.activePreset.primaryColor.copy(alpha = 0.28f) else Color.Transparent,
                    animationSpec = tween(220),
                    label = "dockItemBg"
                )

                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(CircleShape)
                        .then(
                            if (isSelected) {
                                Modifier.liquidGlassSurface(
                                    config = config.copy(glossLevel = 1.0f, transparency = 0.5f),
                                    cornerRadius = 24.dp,
                                    borderWidth = 1.dp
                                )
                            } else Modifier
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onItemSelected(item.id) }
                        )
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.65f),
                            modifier = Modifier.size(19.dp)
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item.title,
                                color = Color.White,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
