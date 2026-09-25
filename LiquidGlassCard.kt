package com.example.components.glass

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GlassConfig

/**
 * Optical Liquid Glass Card Container.
 * Multi-layer frosted transparency, specular highlight edges, and zero-overhead GPU rendering.
 */
@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    config: GlassConfig = GlassConfig(),
    cornerRadius: Dp = config.fluidCurvature.dp,
    padding: PaddingValues = PaddingValues(20.dp),
    testTag: String = "liquid_glass_card",
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .testTag(testTag)
            .liquidGlassSurface(
                config = config,
                cornerRadius = cornerRadius
            )
            .padding(padding)
    ) {
        Column {
            content()
        }
    }
}

/**
 * Live Metrics / Specular Status preview card for the playground
 */
@Composable
fun LiquidGlassSpecimenCard(
    config: GlassConfig,
    modifier: Modifier = Modifier,
    onInteractiveClick: () -> Unit
) {
    LiquidGlassCard(
        config = config,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .liquidGlassSurface(config = config, cornerRadius = 19.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = "Liquid Specimen",
                        tint = config.activePreset.primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Akışkan Sıvı Cam Kart",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    Text(
                        text = config.activePreset.title,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }

            // Zero lag 60+ FPS badge
            Box(
                modifier = Modifier
                    .liquidGlassSurface(
                        config = config.copy(glossLevel = 0.6f, transparency = 0.85f),
                        cornerRadius = 14.dp,
                        borderWidth = 1.dp
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = "FPS",
                        tint = Color(0xFF34D399),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "120 FPS",
                        color = Color(0xFF34D399),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Yüksek optik parlaklık ve akışkan kenar kırılması. Donma veya kasma olmaksızın donanım hızlandırmalı Shader-Brush katmanı ile işlenir.",
            color = Color.White.copy(alpha = 0.82f),
            fontSize = 13.5.sp,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Mini status indicators inside glass
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlassMetricChip(
                label = "Parlaklık (Gloss)",
                value = "${(config.glossLevel * 100).toInt()}%",
                config = config,
                modifier = Modifier.weight(1f)
            )
            GlassMetricChip(
                label = "Şeffaflık",
                value = "${(config.transparency * 100).toInt()}%",
                config = config,
                modifier = Modifier.weight(1f)
            )
            GlassMetricChip(
                label = "Kırılma (IOR)",
                value = "${(1.0f + config.refractionStrength * 0.52f).toString().take(4)}",
                config = config,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LiquidGlassButton(
            text = "Akışkan Tepkiyi Test Et",
            onClick = onInteractiveClick,
            config = config,
            modifier = Modifier.fillMaxWidth(),
            testTag = "specimen_interactive_btn"
        )
    }
}

@Composable
private fun GlassMetricChip(
    label: String,
    value: String,
    config: GlassConfig,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .liquidGlassSurface(
                config = config.copy(glossLevel = 0.5f, transparency = 0.9f),
                cornerRadius = 12.dp,
                borderWidth = 1.dp
            )
            .padding(vertical = 8.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 10.sp
            )
        }
    }
}
