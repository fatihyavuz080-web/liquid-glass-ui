package com.example.components.glass

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GlassConfig

/**
 * High-Gloss Liquid Glass Button.
 * Features realistic refraction, specular glare edge, and zero-stutter spring tactile feedback.
 */
@Composable
fun LiquidGlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    config: GlassConfig = GlassConfig(),
    icon: ImageVector? = Icons.Default.AutoAwesome,
    trailingIcon: ImageVector? = Icons.Default.ChevronRight,
    height: Dp = 54.dp,
    testTag: String = "liquid_glass_button"
) {
    Box(
        modifier = modifier
            .testTag(testTag)
            .height(height)
            .interactiveLiquidGlass(
                config = config,
                cornerRadius = (height.value / 2).dp, // Fluid pill shape
                onClick = onClick
            )
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.95f),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                letterSpacing = 0.4.sp
            )

            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/**
 * Compact fluid pill badge / button
 */
@Composable
fun CompactLiquidButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    config: GlassConfig = GlassConfig(),
    selected: Boolean = false,
    icon: ImageVector? = null,
    testTag: String = "compact_liquid_btn"
) {
    val activeConfig = if (selected) {
        config.copy(
            glossLevel = (config.glossLevel + 0.15f).coerceAtMost(1f),
            transparency = (config.transparency - 0.2f).coerceAtLeast(0.3f)
        )
    } else {
        config.copy(
            glossLevel = (config.glossLevel * 0.6f),
            transparency = (config.transparency + 0.1f).coerceAtMost(0.9f)
        )
    }

    Box(
        modifier = modifier
            .testTag(testTag)
            .height(38.dp)
            .interactiveLiquidGlass(
                config = activeConfig,
                cornerRadius = 19.dp,
                onClick = onClick
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected) activeConfig.activePreset.primaryColor else Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = if (selected) Color.White else Color.White.copy(alpha = 0.75f),
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}
