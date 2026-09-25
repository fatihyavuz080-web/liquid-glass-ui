package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import com.example.components.glass.FluidMeshBackdrop
import com.example.components.glass.liquidGlassSurface
import com.example.model.GlassConfig
import com.example.model.GlassThemePreset
import com.example.ui.screens.LivePlaygroundSection
import com.example.ui.screens.PerformanceGuideSection
import com.example.ui.screens.PromptStudioSection
import com.example.ui.screens.TuningPhysicsSection

enum class AppTab(
    val title: String,
    val icon: ImageVector,
    val testTag: String
) {
    PLAYGROUND("Önizleme", Icons.Default.Visibility, "tab_playground"),
    TUNING("Ayarlar", Icons.Default.Tune, "tab_tuning"),
    PROMPTS("Promptlar", Icons.Default.AutoAwesome, "tab_prompts"),
    PERFORMANCE("0-Lag", Icons.Default.Speed, "tab_performance")
}

@Composable
fun LiquidGlassScreen(
    modifier: Modifier = Modifier
) {
    var config by remember { mutableStateOf(GlassConfig()) }
    var currentTab by remember { mutableStateOf(AppTab.PLAYGROUND) }

    FluidMeshBackdrop(
        activePreset = config.activePreset,
        animated = true
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                LiquidGlassTopBar(
                    config = config,
                    onPresetCycle = {
                        val allPresets = GlassThemePreset.entries
                        val nextIndex = (allPresets.indexOf(config.activePreset) + 1) % allPresets.size
                        val nextPreset = allPresets[nextIndex]
                        config = config.copy(
                            activePreset = nextPreset,
                            glossLevel = nextPreset.defaultGloss,
                            transparency = nextPreset.defaultTransparency,
                            refractionStrength = nextPreset.defaultRefraction
                        )
                    }
                )
            },
            bottomBar = {
                LiquidGlassBottomNav(
                    currentTab = currentTab,
                    onTabSelected = { currentTab = it },
                    config = config
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                when (currentTab) {
                    AppTab.PLAYGROUND -> LivePlaygroundSection(
                        config = config,
                        onNavigateToPrompts = { currentTab = AppTab.PROMPTS }
                    )
                    AppTab.TUNING -> TuningPhysicsSection(
                        config = config,
                        onConfigChange = { config = it },
                        onResetConfig = { config = GlassConfig() }
                    )
                    AppTab.PROMPTS -> PromptStudioSection(
                        config = config
                    )
                    AppTab.PERFORMANCE -> PerformanceGuideSection(
                        config = config
                    )
                }
            }
        }
    }
}

@Composable
private fun LiquidGlassTopBar(
    config: GlassConfig,
    onPresetCycle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .liquidGlassSurface(
                config = config.copy(glossLevel = 0.85f, transparency = 0.82f),
                cornerRadius = 20.dp,
                borderWidth = 1.2.dp
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(config.activePreset.primaryColor.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = "Logo",
                        tint = config.activePreset.primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "LiquidGlass",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Akışkan Cam & Prompt Stüdyosu",
                        color = Color.White.copy(alpha = 0.65f),
                        fontSize = 11.sp
                    )
                }
            }

            // Quick Theme Preset Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onPresetCycle)
                    .background(Color.White.copy(alpha = 0.10f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(config.activePreset.primaryColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = config.activePreset.title.substringBefore(" ("),
                        color = Color.White,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun LiquidGlassBottomNav(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit,
    config: GlassConfig
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .liquidGlassSurface(
                config = config.copy(glossLevel = 0.95f, transparency = 0.78f),
                cornerRadius = 28.dp,
                borderWidth = 1.5.dp
            )
            .padding(vertical = 4.dp, horizontal = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppTab.entries.forEach { tab ->
                val isSelected = currentTab == tab
                Box(
                    modifier = Modifier
                        .testTag(tab.testTag)
                        .height(48.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .then(
                            if (isSelected) {
                                Modifier.liquidGlassSurface(
                                    config = config.copy(glossLevel = 1.0f, transparency = 0.60f),
                                    cornerRadius = 20.dp,
                                    borderWidth = 1.dp
                                )
                            } else Modifier
                        )
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) config.activePreset.primaryColor else Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(18.dp)
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = tab.title,
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
