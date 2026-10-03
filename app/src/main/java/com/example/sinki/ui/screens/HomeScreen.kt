package com.example.sinki.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sinki.data.SinkiConfig
import com.example.ui.theme.BorderSoft
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.ChampagneHighlight
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

/**
 * Halaman Beranda (Home)
 * Sesuai spesifikasi:
 * - Logo SINKI yang mempertahankan bentuk, proporsi, & identitas logo
 * - Foto chibi sebagai visual utama (fokus utama, proporsional, tidak terlalu besar)
 * - Pengenalan singkat: "SINKI hadir untuk menemani setiap momen dengan aroma yang meninggalkan kesan."
 * - Tombol: "Lihat Collection"
 * - Ruang kosong yang cukup agar tampil premium
 */
@Composable
fun HomeScreen(
    onNavigateToCollection: () -> Unit,
    onNavigateToTop10: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        // 1. LOGO SINKI
        // Menampilkan logo brand dengan bingkai halus
        Box(
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(0.6.dp, BorderSubtle), RoundedCornerShape(16.dp))
                .background(ChampagneHighlight.copy(alpha = 0.5f))
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = SinkiConfig.LOGO_RES_ID),
                contentDescription = "Logo SINKI Eau De Parfum",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 2. FOTO CHIBI (VISUAL UTAMA)
        // Dibuat sebagai visual utama dengan komposisi seimbang, tidak menutupi layar,
        // berbalut aura emas lembut dan sudut membulat elegan
        Box(
            modifier = Modifier
                .size(200.dp)
                .shadow(elevation = 3.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(ChampagneHighlight.copy(alpha = 0.85f))
                .border(BorderStroke(1.5.dp, GoldAccent.copy(alpha = 0.6f)), CircleShape)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = SinkiConfig.CHIBI_RES_ID),
                contentDescription = "Mascot Chibi SINKI",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 3. IDENTITAS BRAND & TEKS PENGENALAN SINGKAT
        Text(
            text = "EAU DE PARFUM",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            color = GoldDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = SinkiConfig.BRAND_NAME,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 1.5.sp,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Teks Pengenalan Sederhana (sesuai brief):
        // "SINKI hadir untuk menemani setiap momen dengan aroma yang meninggalkan kesan."
        Text(
            text = SinkiConfig.HOME_INTRO_TEXT,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 4. TOMBOL MENUJU COLLECTION
        Button(
            onClick = onNavigateToCollection,
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldAccent,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(48.dp)
                .testTag("btn_lihat_collection")
        ) {
            Text(
                text = "Lihat Collection",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 5. MINI VALUE PILL / BRAND VALUE
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MiniFeatureItem(
                icon = Icons.Default.WorkspacePremium,
                label = "Long Lasting"
            )
            MiniFeatureItem(
                icon = Icons.Default.AutoAwesome,
                label = "Amber & Spice"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun MiniFeatureItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(CardBackground)
            .border(BorderStroke(0.5.dp, BorderSubtle), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldDark,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}
