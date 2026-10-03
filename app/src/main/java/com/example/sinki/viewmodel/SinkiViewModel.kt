package com.example.sinki.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.sinki.data.SinkiConfig
import com.example.sinki.data.SinkiData
import com.example.sinki.model.Perfume
import com.example.sinki.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class SinkiUiState(
    val currentPageIndex: Int = 0,
    val perfumes: List<Perfume> = SinkiData.initialPerfumes,
    val selectedCategory: String = "Semua",
    val reviews: List<Review> = SinkiData.initialReviews,

    // Order form state
    val selectedPerfume: Perfume = SinkiData.initialPerfumes.first(),
    val selectedSize: String = SinkiConfig.AVAILABLE_SIZES.first(),
    val quantity: Int = 1,
    val customerNote: String = "",
    val isReviewDialogOpen: Boolean = false
) {
    val top10Perfumes: List<Perfume>
        get() = perfumes.filter { it.isTop10 }.sortedBy { it.sortOrder }

    val categories: List<String>
        get() = listOf("Semua") + perfumes.map { it.category }.distinct()

    val filteredCollection: List<Perfume>
        get() = if (selectedCategory == "Semua") {
            perfumes
        } else {
            perfumes.filter { it.category == selectedCategory }
        }

    val calculatedUnitPrice: Long
        get() {
            val basePrice = selectedPerfume.price
            val multiplier = SinkiConfig.SIZE_PRICE_MULTIPLIER[selectedSize] ?: 1.0
            return (basePrice * multiplier).toLong()
        }

    val totalPrice: Long
        get() = calculatedUnitPrice * quantity
}

class SinkiViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SinkiUiState())
    val uiState: StateFlow<SinkiUiState> = _uiState.asStateFlow()

    fun setPageIndex(index: Int) {
        _uiState.update { it.copy(currentPageIndex = index.coerceIn(0, 4)) }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun selectPerfumeForOrder(perfume: Perfume, navigateToOrder: Boolean = true) {
        _uiState.update {
            it.copy(
                selectedPerfume = perfume,
                currentPageIndex = if (navigateToOrder) 3 else it.currentPageIndex
            )
        }
    }

    fun selectSize(size: String) {
        _uiState.update { it.copy(selectedSize = size) }
    }

    fun incrementQuantity() {
        _uiState.update { it.copy(quantity = (it.quantity + 1).coerceAtMost(99)) }
    }

    fun decrementQuantity() {
        _uiState.update { it.copy(quantity = (it.quantity - 1).coerceAtLeast(1)) }
    }

    fun setCustomerNote(note: String) {
        _uiState.update { it.copy(customerNote = note) }
    }

    fun setReviewDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(isReviewDialogOpen = visible) }
    }

    fun submitReview(name: String, rating: Int, comment: String, perfumeName: String) {
        if (name.isBlank() || comment.isBlank()) return
        val newReview = Review(
            id = "rev-${System.currentTimeMillis()}",
            customerName = name.trim(),
            rating = rating.coerceIn(1, 5),
            comment = comment.trim(),
            perfumeName = perfumeName.ifBlank { "SINKI Perfume" },
            date = "Baru saja"
        )
        _uiState.update {
            it.copy(
                reviews = listOf(newReview) + it.reviews,
                isReviewDialogOpen = false
            )
        }
    }

    /**
     * Memformat teks WhatsApp sesuai spesifikasi:
     *
     * Halo SINKI, saya ingin memesan:
     *
     * Parfum: [Nama]
     * Ukuran: [Ukuran]
     * Jumlah: [Jumlah]
     *
     * Mohon informasi ketersediaannya.
     */
    fun createWhatsAppMessage(): String {
        val state = _uiState.value
        val sb = StringBuilder()
        sb.append("Halo SINKI, saya ingin memesan:\n\n")
        sb.append("Parfum: ${state.selectedPerfume.name}\n")
        sb.append("Ukuran: ${state.selectedSize}\n")
        sb.append("Jumlah: ${state.quantity} botol\n")
        if (state.customerNote.isNotBlank()) {
            sb.append("Catatan: ${state.customerNote.trim()}\n")
        }
        sb.append("\nMohon informasi ketersediaannya.")
        return sb.toString()
    }

    /**
     * Membuka aplikasi WhatsApp dengan pesan terformat
     */
    fun sendOrderViaWhatsApp(context: Context) {
        val message = createWhatsAppMessage()
        val phoneNumber = SinkiConfig.WHATSAPP_NUMBER.replace("+", "").replace("-", "").replace(" ", "").trim()

        try {
            val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
            // Coba url wa.me / api.whatsapp.com
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=$encodedMessage"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback: salin ke clipboard & tampilkan notifikasi
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Pesanan SINKI", message)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                context,
                "Format pesanan telah disalin ke clipboard! Silakan kirimkan ke WhatsApp.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
