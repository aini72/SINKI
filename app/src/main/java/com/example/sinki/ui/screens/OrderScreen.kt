package com.example.sinki.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sinki.data.SinkiConfig
import com.example.sinki.model.Perfume
import com.example.sinki.ui.components.formatRupiah
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

/**
 * Halaman Order
 * Sesuai spesifikasi:
 * - Judul: "Order"
 * - Tampilkan:
 *   - pilihan parfum
 *   - pilihan ukuran
 *   - harga
 *   - jumlah
 *   - ringkasan pesanan
 * - Tombol: "Order via WhatsApp"
 * - Menyiapkan format pesan WhatsApp otomatis:
 *     Halo SINKI, saya ingin memesan:
 *
 *     Parfum:
 *     Ukuran:
 *     Jumlah:
 *
 *     Mohon informasi ketersediaannya.
 * - Konfigurasi nomor WhatsApp mudah diganti di SinkiConfig.WHATSAPP_NUMBER
 */
@Composable
fun OrderScreen(
    allPerfumes: List<Perfume>,
    selectedPerfume: Perfume,
    selectedSize: String,
    quantity: Int,
    unitPrice: Long,
    totalPrice: Long,
    customerNote: String,
    onSelectPerfume: (Perfume) -> Unit,
    onSelectSize: (String) -> Unit,
    onIncrementQuantity: () -> Unit,
    onDecrementQuantity: () -> Unit,
    onNoteChange: (String) -> Unit,
    onOrderWhatsApp: (Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // 1. JUDUL HALAMAN
        Text(
            text = "Order",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = TextPrimary
        )
        Text(
            text = "Pilih varian favorit Anda & pesan langsung melalui WhatsApp",
            fontFamily = FontFamily.SansSerif,
            fontSize = 12.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 2. PILIHAN PARFUM (DROPDOWN SELECTOR)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            border = BorderStroke(0.6.dp, BorderSubtle)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = "PILIH PARFUM",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = GoldDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ChampagneHighlight.copy(alpha = 0.7f))
                            .border(BorderStroke(0.5.dp, BorderSoft), RoundedCornerShape(10.dp))
                            .clickable { isDropdownExpanded = true }
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                            .testTag("dropdown_pilih_parfum"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedPerfume.name,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = selectedPerfume.category,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Buka daftar pilihan parfum",
                            tint = TextPrimary
                        )
                    }

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier
                            .background(ChampagneSurfaceLight)
                            .border(BorderStroke(0.5.dp, BorderSubtle))
                    ) {
                        allPerfumes.forEach { perfume ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = perfume.name,
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = if (perfume.id == selectedPerfume.id) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 14.sp,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = "${perfume.category} • ${formatRupiah(perfume.price)}",
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                    }
                                },
                                onClick = {
                                    onSelectPerfume(perfume)
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 3. PILIHAN UKURAN (SIZE CHIPS)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            border = BorderStroke(0.6.dp, BorderSubtle)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = "PILIHAN UKURAN",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = GoldDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SinkiConfig.AVAILABLE_SIZES.forEach { sizeOption ->
                        val isSelected = sizeOption == selectedSize
                        FilterChip(
                            selected = isSelected,
                            onClick = { onSelectSize(sizeOption) },
                            label = {
                                Text(
                                    text = sizeOption,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = ChampagneHighlight.copy(alpha = 0.5f),
                                labelColor = TextSecondary,
                                selectedContainerColor = GoldAccent,
                                selectedLabelColor = Color.White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = BorderSubtle,
                                selectedBorderColor = GoldAccent
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("chip_size_$sizeOption")
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 4. JUMLAH & HARGA SATUAN
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            border = BorderStroke(0.6.dp, BorderSubtle)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "JUMLAH",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp,
                        color = GoldDark
                    )
                    Text(
                        text = "${formatRupiah(unitPrice)} / botol",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }

                // Counter buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = onDecrementQuantity,
                        enabled = quantity > 1,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ChampagneHighlight.copy(alpha = 0.8f))
                            .border(BorderStroke(0.5.dp, BorderSubtle), CircleShape)
                            .testTag("btn_decrement_qty")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Kurang",
                            tint = if (quantity > 1) TextPrimary else TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = "$quantity",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(
                        onClick = onIncrementQuantity,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ChampagneHighlight.copy(alpha = 0.8f))
                            .border(BorderStroke(0.5.dp, BorderSubtle), CircleShape)
                            .testTag("btn_increment_qty")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah",
                            tint = TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 5. CATATAN OPSIONAL
        OutlinedTextField(
            value = customerNote,
            onValueChange = onNoteChange,
            label = { Text("Catatan Tambahan (Opsional)", fontSize = 12.sp) },
            placeholder = { Text("Contoh: Mohon sertakan kartu ucapan", fontSize = 12.sp, color = TextMuted) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CardBackground,
                unfocusedContainerColor = CardBackground,
                focusedBorderColor = GoldAccent,
                unfocusedBorderColor = BorderSubtle,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_catatan_order"),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 6. RINGKASAN PESANAN (ORDER SUMMARY)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("card_ringkasan_pesanan"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = ChampagneHighlight.copy(alpha = 0.75f)
            ),
            border = BorderStroke(0.8.dp, GoldAccent.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = "RINGKASAN PESANAN",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = GoldDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                SummaryRow(label = "Parfum", value = selectedPerfume.name)
                SummaryRow(label = "Ukuran", value = selectedSize)
                SummaryRow(label = "Jumlah", value = "$quantity botol")

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.6.dp,
                    color = BorderSubtle
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Estimasi",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = formatRupiah(totalPrice),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = GoldDark
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 7. TOMBOL ORDER VIA WHATSAPP
        Button(
            onClick = { onOrderWhatsApp(context) },
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldAccent,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("btn_order_whatsapp")
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Order via WhatsApp",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Format pesan otomatis akan terbuka langsung di aplikasi WhatsApp Anda.",
            fontSize = 10.sp,
            fontFamily = FontFamily.SansSerif,
            color = TextMuted,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = FontFamily.SansSerif,
            color = TextSecondary
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}
