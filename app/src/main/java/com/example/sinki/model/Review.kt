package com.example.sinki.model

/**
 * Data Model Penilaian / Ulasan Pelanggan SINKI
 * Dapat dihubungkan ke Supabase table "reviews":
 * - id (text)
 * - customer_name (text)
 * - rating (int 1-5)
 * - comment (text)
 * - perfume_name (text optional)
 * - created_at (text)
 */
data class Review(
    val id: String,
    val customerName: String,
    val rating: Int = 5,
    val comment: String,
    val perfumeName: String = "",
    val date: String = "Baru saja"
)
