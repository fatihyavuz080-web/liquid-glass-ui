package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.components.glass.LiquidGlassCard
import com.example.components.glass.LiquidGlassButton
import com.example.components.glass.liquidGlassSurface
import com.example.model.GlassConfig
import com.example.model.PromptLibrary
import com.example.model.VisualPromptItem
import kotlinx.coroutines.delay

@Composable
fun PromptStudioSection(
    config: GlassConfig,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    var copiedPromptId by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf("Tümü") }

    LaunchedEffect(copiedPromptId) {
        if (copiedPromptId != null) {
            delay(2200)
            copiedPromptId = null
        }
    }

    val categories = listOf("Tümü", "Buton", "Kart", "Navigasyon", "İkon")
    val filteredItems = remember(selectedCategory) {
        if (selectedCategory == "Tümü") PromptLibrary.items
        else PromptLibrary.items.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dynamic Live Prompt Generator Card
        DynamicCustomPromptCard(
            config = config,
            onCopy = { promptText ->
                clipboardManager.setText(AnnotatedString(promptText))
                copiedPromptId = "custom_dynamic"
            },
            isCopied = copiedPromptId == "custom_dynamic"
        )

        // Category Filter Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            if (isSelected) config.activePreset.primaryColor.copy(alpha = 0.35f)
                            else Color.White.copy(alpha = 0.08f)
                        )
                        .clickable { selectedCategory = cat },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        // Prompt Cards List
        filteredItems.forEach { item ->
            VisualPromptCard(
                item = item,
                config = config,
                isCopied = copiedPromptId == item.id,
                onCopy = {
                    val fullCopy = "${item.englishPrompt} ${item.parameters}"
                    clipboardManager.setText(AnnotatedString(fullCopy))
                    copiedPromptId = item.id
                }
            )
        }

        // Negative Prompt Guidance Card
        LiquidGlassCard(
            config = config.copy(glossLevel = 0.6f, transparency = 0.85f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Block,
                    contentDescription = null,
                    tint = Color(0xFFF87171),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Önerilen Negatif Prompt (Cam Kalitesi İçin)",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Plastik veya mat görünümü, bulanık ve çamurlu pikselleri önlemek için görsel üretim aracına mutlaka ekleyin:",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(10.dp)
            ) {
                Text(
                    text = PromptLibrary.negativePromptRecommended,
                    color = Color(0xFFFCA5A5),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DynamicCustomPromptCard(
    config: GlassConfig,
    onCopy: (String) -> Unit,
    isCopied: Boolean
) {
    val glossDesc = when {
        config.glossLevel > 0.8f -> "ultra intense specular gloss reflections"
        config.glossLevel > 0.5f -> "soft elegant specular highlights"
        else -> "subtle frosted glass highlights"
    }

    val dynamicPrompt = remember(config) {
        "A hyper-realistic modern liquid glass UI element, molten fluid organic curvature with ${config.fluidCurvature.toInt()}px rounded bevels, " +
        "crystal clear optical transparency (${(config.transparency * 100).toInt()}% translucency), " +
        "$glossDesc, soft caustic light refractions with ${config.activePreset.primaryColor.hashCode().toString(16).takeLast(6)} glowing internal dispersion, " +
        "illuminated at ${config.lightAngle.toInt()} degrees angle, floating over dark obsidian background, octane render, 8k --ar 16:9 --style raw --v 6.0"
    }

    LiquidGlassCard(
        config = config,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = config.activePreset.primaryColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Canlı Ayarlarla Senkron Prompt",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Box(
                modifier = Modifier
                    .liquidGlassSurface(
                        config = config.copy(glossLevel = 0.7f, transparency = 0.85f),
                        cornerRadius = 10.dp
                    )
                    .clickable { onCopy(dynamicPrompt) }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = "Kopyala",
                        tint = if (isCopied) Color(0xFF34D399) else Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isCopied) "Kopyalandı!" else "Kopyala",
                        color = if (isCopied) Color(0xFF34D399) else Color.White,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black.copy(alpha = 0.45f))
                .padding(12.dp)
        ) {
            Text(
                text = dynamicPrompt,
                color = Color(0xFFE2E8F0),
                fontFamily = FontFamily.Monospace,
                fontSize = 11.5.sp,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun VisualPromptCard(
    item: VisualPromptItem,
    config: GlassConfig,
    isCopied: Boolean,
    onCopy: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    LiquidGlassCard(
        config = config.copy(glossLevel = 0.7f, transparency = 0.82f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(config.activePreset.primaryColor.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = item.category.uppercase(),
                        color = config.activePreset.primaryColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            // Copy Button
            Button(
                onClick = onCopy,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCopied) Color(0xFF059669) else Color.White.copy(alpha = 0.15f),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                    contentDescription = "Kopyala",
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isCopied) "Kopyalandı!" else "Kopyala",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // English Master Prompt Box
        Text(
            text = "Yapay Zeka Promptu (Midjourney, DALL-E, Flux):",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(12.dp)
        ) {
            Text(
                text = "${item.englishPrompt} ${item.parameters}",
                color = Color(0xFFBAE6FD),
                fontFamily = FontFamily.Monospace,
                fontSize = 11.5.sp,
                lineHeight = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Turkish Explanation
        Text(
            text = "Türkçe Açıklama & Anlamı:",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = item.turkishTranslation,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 12.5.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Toggle Technical Breakdown
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = !expanded }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (expanded) "Optik Terim Analizini Gizle" else "Optik Terim Analizini Göster (${item.technicalBreakdown.size} kural)",
                color = config.activePreset.primaryColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null,
                tint = config.activePreset.primaryColor,
                modifier = Modifier.size(18.dp)
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item.technicalBreakdown.forEach { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "• ",
                            color = config.activePreset.primaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = note,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
