package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.components.glass.CompactLiquidButton
import com.example.components.glass.LiquidGlassCard
import com.example.components.glass.liquidGlassSurface
import com.example.model.GlassConfig
import com.example.model.GlassThemePreset

@Composable
fun TuningPhysicsSection(
    config: GlassConfig,
    onConfigChange: (GlassConfig) -> Unit,
    onResetConfig: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Preset Selector Row
        Text(
            text = "OPTİK TEMALAR & ÖN AYARLAR",
            color = config.activePreset.primaryColor,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlassThemePreset.entries.forEach { preset ->
                val isSelected = config.activePreset == preset
                CompactLiquidButton(
                    text = preset.title.substringBefore(" ("),
                    onClick = {
                        onConfigChange(
                            config.copy(
                                activePreset = preset,
                                glossLevel = preset.defaultGloss,
                                transparency = preset.defaultTransparency,
                                refractionStrength = preset.defaultRefraction
                            )
                        )
                    },
                    selected = isSelected,
                    config = config,
                    icon = if (isSelected) Icons.Default.Check else null
                )
            }
        }

        // Live Mini Specimen
        LiquidGlassCard(
            config = config,
            modifier = Modifier.fillMaxWidth(),
            padding = PaddingValues(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Anlık Optik Önizleme",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = config.activePreset.description,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .liquidGlassSurface(config = config, cornerRadius = 22.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ShutterSpeed,
                        contentDescription = null,
                        tint = config.activePreset.glowColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        // Slider Controls
        Text(
            text = "FİZİKSEL VE OPTİK PARAMETRELER",
            color = config.activePreset.primaryColor,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        // 1. Parlaklık (Gloss Intensity)
        GlassSliderCard(
            title = "Yüksek Parlaklık (Gloss Specular)",
            valueLabel = "${(config.glossLevel * 100).toInt()}%",
            description = "Yüzeydeki beyaz Fresnel ışık yansımasının ve ayna ışımasının yoğunluğu",
            value = config.glossLevel,
            valueRange = 0.2f..1.0f,
            onValueChange = { onConfigChange(config.copy(glossLevel = it)) },
            config = config
        )

        // 2. Gerçekçi Şeffaflık (Transparency)
        GlassSliderCard(
            title = "Gerçekçi Şeffaflık (Translucency)",
            valueLabel = "${(config.transparency * 100).toInt()}%",
            description = "Camın arkasındaki nesnelerin ve sıvı arka plan ışıklarının görünürlük derecesi",
            value = config.transparency,
            valueRange = 0.3f..0.95f,
            onValueChange = { onConfigChange(config.copy(transparency = it)) },
            config = config
        )

        // 3. Işık Kırılması (Refraction & Caustics)
        GlassSliderCard(
            title = "Işık Kırılması (Refraction Index)",
            valueLabel = "${(1.0f + config.refractionStrength * 0.52f).toString().take(4)} IOR",
            description = "İç kostik ışık saçılımı ve kenarlardaki optik bükülme gücü",
            value = config.refractionStrength,
            valueRange = 0.1f..1.0f,
            onValueChange = { onConfigChange(config.copy(refractionStrength = it)) },
            config = config
        )

        // 4. Akışkan Kenar Eğriliği (Fluid Curvature)
        GlassSliderCard(
            title = "Pürüzsüz Akışkan Kenar (Curvature)",
            valueLabel = "${config.fluidCurvature.toInt()} dp",
            description = "Keskin hatları yok edip sıvı cıva-cam hissi veren yumuşak eğrilik yarıçapı",
            value = config.fluidCurvature,
            valueRange = 12f..40f,
            onValueChange = { onConfigChange(config.copy(fluidCurvature = it)) },
            config = config
        )

        // 5. Işık Geliş Açısı (Light Angle)
        GlassSliderCard(
            title = "Işık Kaynağı Açısı (Specular Direction)",
            valueLabel = "${config.lightAngle.toInt()}°",
            description = "Aydınlatma kaynağının gelişi (360° döner Fresnel parlama rotası)",
            value = config.lightAngle,
            valueRange = 0f..360f,
            onValueChange = { onConfigChange(config.copy(lightAngle = it)) },
            config = config
        )

        // Reset Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onResetConfig,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Varsayılana Sıfırla", fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun GlassSliderCard(
    title: String,
    valueLabel: String,
    description: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    config: GlassConfig
) {
    LiquidGlassCard(
        config = config.copy(glossLevel = 0.6f, transparency = 0.85f),
        padding = PaddingValues(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Text(
                text = valueLabel,
                color = config.activePreset.primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = description,
            color = Color.White.copy(alpha = 0.65f),
            fontSize = 11.5.sp,
            lineHeight = 15.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = config.activePreset.primaryColor,
                inactiveTrackColor = Color.White.copy(alpha = 0.15f)
            )
        )
    }
}
