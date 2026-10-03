package com.example.sinki.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sinki.data.SinkiConfig
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneHighlight
import com.example.ui.theme.TextSecondary

/**
 * Teks Berjalan (Marquee) Elegan
 * Sesuai spesifikasi:
 * - Teks: "✦ Welcome to SINKI ✦"
 * - Berjalan perlahan dari kanan ke kiri secara halus & loop terus menerus
 * - Sebagai elemen kecil di bagian atas konten
 * - Warna teks kontras lembut
 */
@Composable
fun MarqueeHeader(
    modifier: Modifier = Modifier,
    text: String = SinkiConfig.MARQUEE_TEXT
) {
    val repeatedText = "$text     •     $text     •     $text     •     $text"

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(ChampagneHighlight.copy(alpha = 0.55f))
            .drawBehind {
                val strokeWidth = 0.8.dp.toPx()
                drawLine(
                    color = BorderSubtle,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .padding(vertical = 5.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = repeatedText,
            color = TextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.2.sp,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    velocity = 26.dp,
                    initialDelayMillis = 0
                )
        )
    }
}
