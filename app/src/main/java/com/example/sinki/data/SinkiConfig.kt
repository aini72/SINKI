package com.example.sinki.data

import com.example.R

/**
 * Konfigurasi Utama Aplikasi SINKI
 * Ubah nilai di file ini untuk mengganti nomor WhatsApp, URL Supabase,
 * aset logo, foto, dan konfigurasi lainnya dengan mudah tanpa mengubah layout.
 */
object SinkiConfig {
    // ==========================================
    // 1. KONTAK & PEMESANAN (WHATSAPP)
    // ==========================================
    // Masukkan nomor WhatsApp tujuan pemesanan dengan format internasional tanpa tanda +, contoh: "6281234567890"
    const val WHATSAPP_NUMBER = "6281234567890"

    // Nama brand
    const val BRAND_NAME = "SINKI"
    const val BRAND_SUBTITLE = "EAU DE PARFUM"
    const val BRAND_SLOGAN = "AMBER WOOD & SPICE"

    // Teks pengenalan di Beranda
    const val HOME_INTRO_TEXT = "SINKI hadir untuk menemani setiap momen dengan aroma yang meninggalkan kesan."

    // Teks running marquee
    const val MARQUEE_TEXT = "✦ Welcome to SINKI ✦"

    // ==========================================
    // 2. SUPABASE STORAGE & API INTEGRASI
    // ==========================================
    // URL dasar project Supabase Anda (bila nanti sudah dihubungkan)
    // Contoh: "https://xyzcompany.supabase.co"
    const val SUPABASE_PROJECT_URL = "https://your-project-id.supabase.co"
    const val SUPABASE_STORAGE_BUCKET = "perfume-bottles"

    /**
     * Helper untuk menghasilkan full URL gambar produk dari Supabase Storage.
     * Jika path sudah berupa full http/https URL, dikembalikan langsung.
     */
    fun getSupabaseImageUrl(imageFileNameOrUrl: String): String {
        if (imageFileNameOrUrl.startsWith("http://") || imageFileNameOrUrl.startsWith("https://")) {
            return imageFileNameOrUrl
        }
        if (imageFileNameOrUrl.isBlank()) {
            return ""
        }
        return "$SUPABASE_PROJECT_URL/storage/v1/object/public/$SUPABASE_STORAGE_BUCKET/$imageFileNameOrUrl"
    }

    // ==========================================
    // 3. ASET LOKAL (DEFAULT DRAWABLES)
    // ==========================================
    val LOGO_RES_ID = R.drawable.img_sinki_logo
    val CHIBI_RES_ID = R.drawable.img_sinki_chibi
    val DEFAULT_BOTTLE_RES_ID = R.drawable.img_perfume_bottle

    // ==========================================
    // 4. UKURAN & HARGA
    // ==========================================
    // Daftar ukuran botol yang tersedia beserta rasio harga
    val AVAILABLE_SIZES = listOf("30ml", "50ml", "100ml")
    val SIZE_PRICE_MULTIPLIER = mapOf(
        "30ml" to 1.0,
        "50ml" to 1.45,
        "100ml" to 2.40
    )
}
