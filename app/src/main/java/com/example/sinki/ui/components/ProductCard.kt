package com.example.sinki.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sinki.data.SinkiConfig
import com.example.sinki.model.Perfume
import com.example.ui.theme.BorderSoft
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.ChampagneHighlight
import com.example.ui.theme.ChampagneSurfaceLight
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.NumberFormat
import java.util.Locale

/**
 * Kartu Koleksi Bersih (Clean Collection Card)
 * Khusus halaman Collection:
 * - TIDAK menampilkan foto botol
 * - TIDAK menampilkan harga besar
 * - TIDAK menampilkan tombol WhatsApp per kartu
 * - Menampilkan nama parfum, kategori, deskripsi singkat
 * - Elegan, compact, nyaman dibaca di smartphone
 */
@Composable
fun CollectionCard(
    perfume: Perfume,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("collection_card_${perfume.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        border = BorderStroke(0.6.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = perfume.name,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Badge Kategori Kecil
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = ChampagneHighlight.copy(alpha = 0.8f),
                        border = BorderStroke(0.5.dp, BorderSubtle)
                    ) {
                        Text(
                            text = perfume.category,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = perfume.description,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Icon panah halus untuk indikasi bisa dipesan
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(ChampagneHighlight.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Pilih ${perfume.name}",
                    tint = GoldDark,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

/**
 * Kartu Top 10 Pilihan (Top 10 Product Card)
 * Khusus halaman Top 10:
 * - Menampilkan foto botol sebagai fokus (bisa diganti via Supabase Storage image_url)
 * - Nomor urutan (01, 02, ...) dengan aksen gold elegan
 * - Nama parfum
 * - Deskripsi aroma singkat
 * - Harga
 * - Tombol Pesan cepat
 * - Desain compact agar tidak perlu terlalu banyak scrolling
 */
@Composable
fun Top10ProductCard(
    perfume: Perfume,
    rank: Int,
    onOrderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formattedPrice = formatRupiah(perfume.price)
    val imageUrl = if (perfume.imageUrl.isNotBlank()) {
        SinkiConfig.getSupabaseImageUrl(perfume.imageUrl)
    } else {
        null
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("top10_card_${perfume.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        border = BorderStroke(0.6.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // FOTO BOTOL (Fokus utama dengan rasio konsisten)
            Box(
                modifier = Modifier
                    .size(width = 82.dp, height = 100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ChampagneHighlight.copy(alpha = 0.5f))
                    .border(BorderStroke(0.5.dp, BorderSoft), RoundedCornerShape(10.dp))
            ) {
                if (!imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUrl)
                            .crossfade(true)
                            .error(SinkiConfig.DEFAULT_BOTTLE_RES_ID)
                            .placeholder(SinkiConfig.DEFAULT_BOTTLE_RES_ID)
                            .build(),
                        contentDescription = "Foto botol ${perfume.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                } else {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = SinkiConfig.DEFAULT_BOTTLE_RES_ID),
                        contentDescription = "Foto botol ${perfume.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }

                // Rank Badge di atas foto (e.g. #01)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(GoldAccent.copy(alpha = 0.9f))
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = String.format(Locale.getDefault(), "#%02d", rank),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // DETAIL PRODUK
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Kategori
                Text(
                    text = perfume.category.uppercase(Locale.getDefault()),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    fontSize = 9.sp,
                    letterSpacing = 1.sp,
                    color = GoldDark
                )

                // Nama Parfum
                Text(
                    text = perfume.name,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Deskripsi Aroma Singkat
                Text(
                    text = perfume.description,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Baris Harga & Tombol Pesan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Mulai dari",
                            fontSize = 9.sp,
                            color = TextMuted
                        )
                        Text(
                            text = formattedPrice,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = TextPrimary
                        )
                    }

                    Button(
                        onClick = onOrderClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldAccent,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 12.dp,
                            vertical = 4.dp
                        ),
                        modifier = Modifier
                            .height(30.dp)
                            .testTag("btn_order_${perfume.id}")
                    ) {
                        Text(
                            text = "Pesan",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

/**
 * Format angka rupiah: e.g. Rp 185.000
 */
fun formatRupiah(amount: Long): String {
    val format = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return "Rp " + format.format(amount)
}
