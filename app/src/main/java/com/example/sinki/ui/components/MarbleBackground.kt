package com.example.sinki.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.theme.ChampagneHighlight
import com.example.ui.theme.ChampagneMain

/**
 * Komponen Latar Belakang Marmer Champagne Hangat (Warm Champagne Marble)
 * Sesuai spesifikasi:
 * - Warna utama background: #D8C7AE
 * - Warna highlight: #E8DCC8
 * - Urat marmer tipis, kontras rendah, sangat halus
 * - Bagian tengah layar tetap tenang dan nyaman dibaca
 * - Bukan pola wallpaper berulang
 */
@Composable
fun ChampagneMarbleBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ChampagneMain)
    ) {
        // Lapisan kanvas marmer halus
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 1. Highlight gradasi lembut di sudut atas dan bawah
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        ChampagneHighlight.copy(alpha = 0.45f),
                        ChampagneMain.copy(alpha = 0f)
                    ),
                    center = Offset(width * 0.15f, height * 0.12f),
                    radius = width * 0.7f
                ),
                radius = width * 0.7f,
                center = Offset(width * 0.15f, height * 0.12f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        ChampagneHighlight.copy(alpha = 0.35f),
                        ChampagneMain.copy(alpha = 0f)
                    ),
                    center = Offset(width * 0.85f, height * 0.88f),
                    radius = width * 0.8f
                ),
                radius = width * 0.8f,
                center = Offset(width * 0.85f, height * 0.88f)
            )

            // 2. Urat marmer organik halus (low contrast, warm)
            // Urat 1 (atas mengalir ke kanan)
            val vein1 = Path().apply {
                moveTo(-width * 0.1f, height * 0.08f)
                cubicTo(
                    width * 0.25f, height * 0.04f,
                    width * 0.55f, height * 0.14f,
                    width * 1.1f, height * 0.10f
                )
            }
            drawPath(
                path = vein1,
                color = Color(0x18FAF6F0),
                style = Stroke(width = 3.5f)
            )
            drawPath(
                path = vein1,
                color = Color(0x104A4036),
                style = Stroke(width = 1.2f)
            )

            // Urat 2 (sudut kiri bawah ke tengah bawah)
            val vein2 = Path().apply {
                moveTo(-width * 0.05f, height * 0.75f)
                cubicTo(
                    width * 0.25f, height * 0.70f,
                    width * 0.45f, height * 0.82f,
                    width * 0.95f, height * 0.78f
                )
            }
            drawPath(
                path = vein2,
                color = Color(0x1AFAF6F0),
                style = Stroke(width = 2.8f)
            )
            drawPath(
                path = vein2,
                color = Color(0x0E4A4036),
                style = Stroke(width = 1.0f)
            )

            // Urat 3 (diagonal sangat tipis di sisi kanan)
            val vein3 = Path().apply {
                moveTo(width * 0.70f, -height * 0.05f)
                cubicTo(
                    width * 0.85f, height * 0.25f,
                    width * 0.75f, height * 0.45f,
                    width * 1.05f, height * 0.65f
                )
            }
            drawPath(
                path = vein3,
                color = Color(0x14E8DCC8),
                style = Stroke(width = 2.0f)
            )

            // Subtle gold vein glimmer (aksen sangat tipis)
            val goldVein = Path().apply {
                moveTo(width * 0.10f, height * 0.20f)
                cubicTo(
                    width * 0.35f, height * 0.23f,
                    width * 0.50f, height * 0.28f,
                    width * 0.80f, height * 0.25f
                )
            }
            drawPath(
                path = goldVein,
                color = Color(0x12C99A3D),
                style = Stroke(width = 1.5f)
            )
        }

        // Konten diletakkan di atas background marmer
        content()
    }
}
