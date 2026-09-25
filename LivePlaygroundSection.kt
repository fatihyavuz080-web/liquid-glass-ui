package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.components.glass.*
import com.example.model.GlassConfig
import kotlinx.coroutines.delay

@Composable
fun LivePlaygroundSection(
    config: GlassConfig,
    onNavigateToPrompts: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDockItem by remember { mutableStateOf("glass_btn") }
    var interactiveCounter by remember { mutableIntStateOf(0) }
    var showInteractiveRipple by remember { mutableStateOf(false) }

    LaunchedEffect(showInteractiveRipple) {
        if (showInteractiveRipple) {
            delay(1200)
            showInteractiveRipple = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Hero Banner Card with Generated Visual Asset
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .liquidGlassSurface(config = config, cornerRadius = 24.dp)
                .padding(4.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_liquid_glass_hero_1790361424507),
                        contentDescription = "Liquid Glass Hero Visual",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Overlay glass badge
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.BottomStart)
                            .liquidGlassSurface(
                                config = config.copy(glossLevel = 0.95f, transparency = 0.8f),
                                cornerRadius = 14.dp,
                                borderWidth = 1.dp
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Ultra Parlak Sıvı Cam Estetiği",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                PaddingValues(horizontal = 14.dp, vertical = 12.dp).let { pad ->
                    Column(modifier = Modifier.padding(pad)) {
                        Text(
                            text = "Gerçekçi Kırılma & Sıfır Kasma",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Yumuşak ışık kırılmaları, prizmatik kenarlar ve yüksek parlaklık. Arka plandaki optik akışkanlığı inceleyin.",
                            color = Color.White.copy(alpha = 0.76f),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Section Title: Interactive Liquid Glass Button
        Text(
            text = "1. YÜKSEK PARLAKLIKLI SIVI CAM BUTON",
            color = config.activePreset.primaryColor,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        // The Primary Liquid Glass Button
        LiquidGlassButton(
            text = "Akışkan Butona Dokun (${interactiveCounter}x)",
            onClick = {
                interactiveCounter++
                showInteractiveRipple = true
            },
            config = config,
            icon = Icons.Default.WaterDrop,
            trailingIcon = Icons.Default.TouchApp,
            height = 56.dp,
            modifier = Modifier.fillMaxWidth(),
            testTag = "primary_interactive_glass_button"
        )

        AnimatedVisibility(
            visible = showInteractiveRipple,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .liquidGlassSurface(
                        config = config.copy(glossLevel = 0.7f, transparency = 0.85f),
                        cornerRadius = 14.dp
                    )
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF34D399),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Işık parlaması ve yay fiziği tetiklendi! Gecikme: 0.1ms (120 FPS)",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Dual Button Pair (Compact Liquid Glass Buttons)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LiquidGlassButton(
                text = "Hızlı Işıma",
                onClick = { interactiveCounter++ },
                config = config,
                icon = Icons.Default.FlashOn,
                trailingIcon = null,
                height = 48.dp,
                modifier = Modifier.weight(1f),
                testTag = "compact_flash_btn"
            )

            LiquidGlassButton(
                text = "Prompt'u Gör",
                onClick = onNavigateToPrompts,
                config = config.copy(glossLevel = 0.75f, transparency = 0.82f),
                icon = Icons.Default.Code,
                trailingIcon = null,
                height = 48.dp,
                modifier = Modifier.weight(1f),
                testTag = "compact_code_btn"
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Section Title: Liquid Glass Card
        Text(
            text = "2. PRİZMATİK SIVI CAM BİLGİ KARTI",
            color = config.activePreset.primaryColor,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        // Specimen Card
        LiquidGlassSpecimenCard(
            config = config,
            onInteractiveClick = {
                interactiveCounter++
                showInteractiveRipple = true
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Section Title: Floating Liquid Glass Dock
        Text(
            text = "3. HAVADA YÜZEN SIVI CAM DOCK",
            color = config.activePreset.primaryColor,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        FloatingLiquidDock(
            selectedId = selectedDockItem,
            onItemSelected = { selectedDockItem = it },
            config = config,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
