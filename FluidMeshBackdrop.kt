package com.example.components.glass

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.model.GlassThemePreset
import kotlin.math.cos
import kotlin.math.sin

/**
 * 60/120 FPS Fluid Dynamic Background Mesh.
 * Renders glowing drifting optical orbs using hardware Canvas drawing
 * without triggering any Compose UI recompositions or layout recalculations.
 */
@Composable
fun FluidMeshBackdrop(
    modifier: Modifier = Modifier,
    activePreset: GlassThemePreset = GlassThemePreset.OPAL_PRISM,
    animated: Boolean = true,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "fluidMesh")

    val phaseA by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (Math.PI * 2).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phaseA"
    )

    val phaseB by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (Math.PI * 2).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phaseB"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val w = size.width
                val h = size.height

                // Deep Obsidian Base
                drawRect(color = Color(0xFF070B14))

                // Orb 1: Primary Refractive Orb (drifting smoothly)
                val orb1X = if (animated) w * 0.35f + cos(phaseA) * (w * 0.22f) else w * 0.35f
                val orb1Y = if (animated) h * 0.25f + sin(phaseA) * (h * 0.12f) else h * 0.25f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            activePreset.primaryColor.copy(alpha = 0.38f),
                            activePreset.glowColor.copy(alpha = 0.16f),
                            Color.Transparent
                        ),
                        center = Offset(orb1X, orb1Y),
                        radius = w * 0.65f
                    ),
                    center = Offset(orb1X, orb1Y),
                    radius = w * 0.65f
                )

                // Orb 2: Secondary Chromatic Orb (counter-phase movement)
                val orb2X = if (animated) w * 0.68f + sin(phaseB) * (w * 0.20f) else w * 0.68f
                val orb2Y = if (animated) h * 0.65f + cos(phaseB) * (h * 0.14f) else h * 0.65f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            activePreset.secondaryColor.copy(alpha = 0.32f),
                            Color(0xFF818CF8).copy(alpha = 0.12f),
                            Color.Transparent
                        ),
                        center = Offset(orb2X, orb2Y),
                        radius = w * 0.70f
                    ),
                    center = Offset(orb2X, orb2Y),
                    radius = w * 0.70f
                )

                // Orb 3: Bottom ambient glow
                val orb3X = w * 0.5f
                val orb3Y = h * 0.90f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            activePreset.glowColor.copy(alpha = 0.22f),
                            Color.Transparent
                        ),
                        center = Offset(orb3X, orb3Y),
                        radius = w * 0.55f
                    ),
                    center = Offset(orb3X, orb3Y),
                    radius = w * 0.55f
                )

                // Subtle micro-grid noise / tech texture overlay
                val gridAlpha = 0.04f
                val step = 48f
                var x = 0f
                while (x < w) {
                    drawLine(
                        color = Color.White.copy(alpha = gridAlpha),
                        start = Offset(x, 0f),
                        end = Offset(x, h),
                        strokeWidth = 1f
                    )
                    x += step
                }
            }
    ) {
        content()
    }
}
