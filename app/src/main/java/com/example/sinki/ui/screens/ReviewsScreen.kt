package com.example.sinki.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sinki.model.Review
import com.example.ui.theme.BorderSoft
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.ChampagneHighlight
import com.example.ui.theme.ChampagneSurfaceLight
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldStar
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

/**
 * Halaman Penilaian (Reviews)
 * Sesuai spesifikasi:
 * - Tampilan sederhana dan profesional
 * - Menampilkan rating bintang (★★★★★)
 * - Komentar pelanggan
 * - Nama/inisial pelanggan (— Pelanggan)
 * - Review dapat dimasukkan atau diubah secara fleksibel
 * - Compact & tidak terlalu besar
 */
@Composable
fun ReviewsScreen(
    reviews: List<Review>,
    isDialogOpen: Boolean,
    onOpenDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onSubmitReview: (name: String, rating: Int, comment: String, perfumeName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header Penilaian
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Penilaian",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextPrimary
                )
                Text(
                    text = "Ulasan otentik dari para pencinta aroma SINKI",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            OutlinedButton(
                onClick = onOpenDialog,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(0.8.dp, GoldAccent),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier
                    .height(32.dp)
                    .testTag("btn_tulis_penilaian")
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = null,
                    tint = GoldDark,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Ulas",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    color = TextPrimary
                )
            }
        }

        // Summary Rating Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            border = BorderStroke(0.6.dp, BorderSubtle)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "5.0",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = GoldStar,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = "Rata-rata kepuasan aroma",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = TextSecondary
                        )
                    }
                }

                Text(
                    text = "${reviews.size} Ulasan",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = GoldDark
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Daftar Review
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("reviews_list"),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(reviews, key = { it.id }) { review ->
                ReviewCard(review = review)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Dialog Tambah Review
    if (isDialogOpen) {
        AddReviewDialog(
            onDismiss = onDismissDialog,
            onSubmit = onSubmitReview
        )
    }
}

/**
 * Kartu Review Tunggal Sesuai Contoh Visual:
 * ★★★★★
 * "Parfumnya wangi dan tahan lama."
 * — Pelanggan
 */
@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("review_card_${review.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = BorderStroke(0.6.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Rating Bintang
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    repeat(review.rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GoldStar,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    repeat(5 - review.rating) {
                        Icon(
                            imageVector = Icons.Outlined.StarOutline,
                            contentDescription = null,
                            tint = BorderSoft,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                if (review.perfumeName.isNotBlank()) {
                    Text(
                        text = review.perfumeName,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        color = GoldDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Komentar Pelanggan
            Text(
                text = "\"${review.comment}\"",
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Normal,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nama / Inisial Pelanggan
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "— ${review.customerName}",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Text(
                    text = review.date,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = TextMuted
                )
            }
        }
    }
}

/**
 * Dialog Menulis Ulasan Baru
 */
@Composable
fun AddReviewDialog(
    onDismiss: () -> Unit,
    onSubmit: (name: String, rating: Int, comment: String, perfumeName: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(5) }
    var perfumeName by remember { mutableStateOf("Amber Wood & Spice") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ChampagneSurfaceLight,
        title = {
            Text(
                text = "Tulis Penilaian",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextPrimary
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Pilih Bintang
                Text(
                    text = "Rating Anda:",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { star ->
                        Icon(
                            imageVector = if (star <= rating) Icons.Default.Star else Icons.Outlined.StarOutline,
                            contentDescription = "Bintang $star",
                            tint = if (star <= rating) GoldStar else BorderSoft,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { rating = star }
                        )
                    }
                }

                // Nama Anda
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama atau Inisial", fontSize = 12.sp) },
                    placeholder = { Text("Contoh: Maya S.", fontSize = 12.sp) },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldAccent,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Komentar Ulasan
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Ulasan Anda", fontSize = 12.sp) },
                    placeholder = { Text("Ceritakan kesan aroma parfum...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldAccent,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && comment.isNotBlank()) {
                        onSubmit(name, rating, comment, perfumeName)
                    }
                },
                enabled = name.isNotBlank() && comment.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldAccent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("Kirim Penilaian", fontSize = 12.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = TextSecondary, fontSize = 12.sp)
            }
        }
    )
}
