package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class GlassThemePreset(
    val title: String,
    val description: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val glowColor: Color,
    val defaultGloss: Float,
    val defaultTransparency: Float,
    val defaultRefraction: Float
) {
    OPAL_PRISM(
        title = "Opal Prism (Optik Kristal)",
        description = "Yumuşak prizmatik renk kırılmaları ve yüksek ayna parlaklığı",
        primaryColor = OpalPrism,
        secondaryColor = OpalSky,
        glowColor = Color(0xFFC084FC),
        defaultGloss = 0.88f,
        defaultTransparency = 0.72f,
        defaultRefraction = 0.85f
    ),
    CYAN_LIQUID(
        title = "Aqua Stream (Akışkan Su)",
        description = "Saf su damlacığı berraklığı ve turkuaz ışık kırılmaları",
        primaryColor = CyanRefract,
        secondaryColor = CyanGlow,
        glowColor = Color(0xFF00F2FE),
        defaultGloss = 0.95f,
        defaultTransparency = 0.65f,
        defaultRefraction = 0.90f
    ),
    OBSIDIAN_ICE(
        title = "Obsidian Ice (Karanlık Cam)",
        description = "Derin siyah-füme gövde üzerinde keskin beyaz ışık yansımaları",
        primaryColor = Color(0xFF94A3B8),
        secondaryColor = Color(0xFF475569),
        glowColor = Color(0xFF38BDF8),
        defaultGloss = 0.92f,
        defaultTransparency = 0.85f,
        defaultRefraction = 0.75f
    ),
    AURORA_EMERALD(
        title = "Aurora Emerald (Zümrüt Sıvı)",
        description = "Kuzey ışıkları akışkanlığı ve zümrüt yeşili derin iç ışıma",
        primaryColor = EmeraldGlow,
        secondaryColor = Color(0xFF059669),
        glowColor = Color(0xFF10B981),
        defaultGloss = 0.85f,
        defaultTransparency = 0.70f,
        defaultRefraction = 0.80f
    ),
    ROSE_QUARTZ(
        title = "Rose Quartz (Akışkan Lal)",
        description = "Sıcak pembe-şeftali ışık saçılımı ve yumuşak kavisler",
        primaryColor = RoseRefract,
        secondaryColor = AmberGlow,
        glowColor = Color(0xFFF43F5E),
        defaultGloss = 0.90f,
        defaultTransparency = 0.68f,
        defaultRefraction = 0.88f
    )
}

data class GlassConfig(
    val glossLevel: Float = 0.90f,           // 0.2f - 1.0f (Parlaklık şiddeti)
    val transparency: Float = 0.75f,         // 0.3f - 0.95f (Saydamlık seviyesi)
    val refractionStrength: Float = 0.85f,   // 0.1f - 1.0f (Işık kırılma/kenar payı)
    val fluidCurvature: Float = 28f,         // 12f - 40f (Köşe yumuşaklığı/akışkanlık)
    val lightAngle: Float = 135f,            // 0f - 360f (Işık kaynağı geliş açısı)
    val chromaticDispersion: Boolean = true, // İnce renk ayrışımı / prizma kenarı
    val activePreset: GlassThemePreset = GlassThemePreset.OPAL_PRISM
)
