package com.example.sinki.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneHighlight
import com.example.ui.theme.ChampagneSurfaceLight
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

sealed class SinkiNavDestination(
    val index: Int,
    val title: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector,
    val testTag: String
) {
    object Beranda : SinkiNavDestination(0, "Beranda", Icons.Filled.Home, Icons.Outlined.Home, "nav_beranda")
    object Collection : SinkiNavDestination(1, "Collection", Icons.AutoMirrored.Outlined.List, Icons.AutoMirrored.Outlined.List, "nav_collection")
    object Top10 : SinkiNavDestination(2, "Top 10", Icons.Filled.Star, Icons.Outlined.StarOutline, "nav_top10")
    object Order : SinkiNavDestination(3, "Order", Icons.Filled.ShoppingBag, Icons.Outlined.ShoppingBag, "nav_order")
    object Penilaian : SinkiNavDestination(4, "Penilaian", Icons.Filled.ThumbUp, Icons.Outlined.ThumbUp, "nav_penilaian")

    companion object {
        val items = listOf(Beranda, Collection, Top10, Order, Penilaian)
    }
}

/**
 * Bottom Navigation Bar SINKI
 * Sesuai spesifikasi:
 * - Fixed/sticky di bagian bawah layar smartphone
 * - Safe area navigation bar
 * - Icon sederhana dan modern
 * - Label pendek
 * - Menu aktif menggunakan aksen gold secara elegan
 * - Nyaman ditekan (touch target memadai)
 * - Tidak terlalu tinggi
 */
@Composable
fun SinkiBottomNav(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp)
            .background(ChampagneHighlight.copy(alpha = 0.96f))
            .drawBehind {
                val strokeWidth = 0.8.dp.toPx()
                drawLine(
                    color = BorderSubtle,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
            }
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SinkiNavDestination.items.forEach { item ->
                val isSelected = selectedIndex == item.index

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = false, radius = 24.dp),
                            onClick = { onItemSelected(item.index) }
                        )
                        .testTag(item.testTag)
                        .padding(vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Indicator capsule for active state
                    Box(
                        modifier = Modifier
                            .size(width = 38.dp, height = 24.dp)
                            .background(
                                color = if (isSelected) ChampagneSurfaceLight.copy(alpha = 0.7f) else androidx.compose.ui.graphics.Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.activeIcon else item.inactiveIcon,
                            contentDescription = item.title,
                            tint = if (isSelected) GoldAccent else TextSecondary.copy(alpha = 0.7f),
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.title,
                        color = if (isSelected) TextPrimary else TextSecondary.copy(alpha = 0.75f),
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
