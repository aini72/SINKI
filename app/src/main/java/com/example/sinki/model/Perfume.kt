package com.example.sinki.model

/**
 * Data Model Produk Parfum SINKI
 * Sesuai dengan skema Supabase Table:
 * - id (text / uuid)
 * - name (text)
 * - description (text)
 * - notes (text - komposisi aroma top, mid, base)
 * - price (bigint / integer)
 * - image_url (text - URL foto botol dari Supabase Storage)
 * - category (text)
 * - is_top10 (boolean)
 * - sort_order (integer)
 */
data class Perfume(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val notes: String = "",
    val price: Long = 185000L,
    val imageUrl: String = "", // URL dari Supabase Storage atau fallback lokal
    val isTop10: Boolean = false,
    val sortOrder: Int = 0,
    val sizes: List<String> = listOf("30ml", "50ml", "100ml")
)
