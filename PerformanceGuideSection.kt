package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.components.glass.LiquidGlassCard
import com.example.model.GlassConfig

@Composable
fun PerformanceGuideSection(
    config: GlassConfig,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Zero-Lag Performance Hero Card
        LiquidGlassCard(
            config = config,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF059669).copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = Color(0xFF34D399),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "\"Kasma ve Donma Olmasın\": Sıfır Gecikme",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "120 FPS Donanım Hızlandırmalı Cam Mimarisi",
                        color = Color(0xFF34D399),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Mobil cihazlarda cam ve sıvı (glassmorphism) efektlerinin kasmasının asıl nedeni: her karede piksel piksel CPU üzerinden bulanıklık (blur) hesaplanması ve Compose'un sürekli recomposition'a girmesidir.",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }

        // Comparison: Neden Kasar vs Nasıl Çözdük
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Bad Practice Card
            LiquidGlassCard(
                config = config.copy(glossLevel = 0.4f, transparency = 0.9f),
                modifier = Modifier.weight(1f),
                padding = PaddingValues(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                        tint = Color(0xFFF87171),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Kasmaya Yol Açanlar",
                        color = Color(0xFFFCA5A5),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "❌ Canlı render blur (RenderEffect)\n❌ Sürekli recomposition tetikleme\n❌ CPU Bitmap kopyalama\n❌ Ağır gölge katmanları\n❌ 16ms bütçesini aşan GPU kilitlenmesi",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 11.sp,
                    lineHeight = 17.sp
                )
            }

            // Good Practice Card
            LiquidGlassCard(
                config = config.copy(glossLevel = 0.8f, transparency = 0.8f),
                modifier = Modifier.weight(1f),
                padding = PaddingValues(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF34D399),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Bizim Çözümümüz",
                        color = Color(0xFF6EE7B7),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "✅ Draw Phase (drawBehind)\n✅ 0 Recomposition döngüsü\n✅ Matematiksel optik gradyanlar\n✅ GPU RenderNode önbellekleme\n✅ <1.2ms ultra akıcı çizim",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 11.sp,
                    lineHeight = 17.sp
                )
            }
        }

        // Technical Steps Cards
        Text(
            text = "4 ADIMDA SIVI CAM MİMARİSİ",
            color = config.activePreset.primaryColor,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        PerformanceStepCard(
            stepNumber = "1",
            title = "Optik Kırılma (Caustic & IOR)",
            description = "Pahalı piksel kaydırma shader'ları yerine, açıya duyarlı radyal ve doğrusal Brush gradyanı ile yüzey altı ışık saçılımı (subsurface scattering) simüle edilir.",
            codeSnippet = "Brush.radialGradient(colors = listOf(glow.copy(alpha = 0.25f), Transparent))",
            config = config
        )

        PerformanceStepCard(
            stepNumber = "2",
            title = "Fresnel Yüksek Parlaklık (Specular Lens)",
            description = "Işığın geliş açısına göre hesaplanan 4 katmanlı alfa gradyanı, camın üst kavisinde ayna parlaklığı oluşturur. CPU'ya dokunmadan doğrudan GPU'da çalışır.",
            codeSnippet = "val specularBrush = Brush.linearGradient(listOf(White.copy(0.45f), Transparent))",
            config = config
        )

        PerformanceStepCard(
            stepNumber = "3",
            title = "Prizmatik Mikro-Eğim (Chromatic Bevel)",
            description = "Erimiş camın kenarındaki ince ışık kırılmasını vermek için çok renkli (Cyan -> Menekşe -> Beyaz) kontur çizilir. Akışkan cıva-cam hissini bu verir.",
            codeSnippet = "Stroke(width = 1.5.dp, brush = chromaticBrush)",
            config = config
        )

        PerformanceStepCard(
            stepNumber = "4",
            title = "Recomposition'sız Dokunmatik Yay (Spring Tactile)",
            description = "Butona basıldığında UI ağacının baştan hesaplanmasını engellemek için dokunma animasyonu graphicsLayer ve drawWithContent içinde izole edilir.",
            codeSnippet = "Modifier.graphicsLayer { scaleX = pressScale }",
            config = config
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PerformanceStepCard(
    stepNumber: String,
    title: String,
    description: String,
    codeSnippet: String,
    config: GlassConfig
) {
    LiquidGlassCard(
        config = config.copy(glossLevel = 0.6f, transparency = 0.85f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(config.activePreset.primaryColor.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber,
                    color = config.activePreset.primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            color = Color.White.copy(alpha = 0.78f),
            fontSize = 12.sp,
            lineHeight = 17.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            Text(
                text = codeSnippet,
                color = Color(0xFF38BDF8),
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp
            )
        }
    }
}
