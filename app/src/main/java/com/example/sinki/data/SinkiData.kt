package com.example.sinki.data

import com.example.sinki.model.Perfume
import com.example.sinki.model.Review

/**
 * Data Awal Katalog SINKI
 * Struktur disesuaikan persis dengan skema Supabase:
 * id, name, category, description, notes, price, image_url, is_top10, sort_order
 */
object SinkiData {

    val initialPerfumes = listOf(
        // TOP 10 PERFUMES
        Perfume(
            id = "sinki-01",
            name = "Amber Wood & Spice",
            category = "Amber Wood",
            description = "Aroma signature SINKI dengan kehangatan amber mewah dan rempah eksotis yang berkarakter kuat.",
            notes = "Top: Bergamot, Cardamom • Heart: Nutmeg, Ambergris • Base: Cedarwood, Sandalwood",
            price = 195000L,
            imageUrl = "", // Bisa diisi URL Supabase Storage: e.g. "amber_wood_spice.png"
            isTop10 = true,
            sortOrder = 1
        ),
        Perfume(
            id = "sinki-02",
            name = "Velvet Santal",
            category = "Woody Warm",
            description = "Kelembutan kayu cendana berpadu vanilla halus, memberi kesan teduh, hangat, dan menenangkan.",
            notes = "Top: White Pepper, Fig • Heart: Sandalwood, Orris • Base: Cashmeran, Musk",
            price = 185000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 2
        ),
        Perfume(
            id = "sinki-03",
            name = "Golden Noir",
            category = "Oriental Spicy",
            description = "Sensasi malam yang elegan dengan sentuhan kopi gelap, saffron, dan sentuhan karamel keemasan.",
            notes = "Top: Saffron, Black Pepper • Heart: Roasted Coffee, Tonka • Base: Patchouli, Amber",
            price = 210000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 3
        ),
        Perfume(
            id = "sinki-04",
            name = "Rose Épice",
            category = "Floral Spice",
            description = "Mawar merah beludru yang dipadukan rempah manis, elegan tanpa kesan terlalu manis.",
            notes = "Top: Pink Pepper, Lychee • Heart: Damask Rose, Clove • Base: Frankincense, Oud",
            price = 195000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 4
        ),
        Perfume(
            id = "sinki-05",
            name = "Oud Sublime",
            category = "Precious Oud",
            description = "Gaharu modern yang halus, dipoles dengan jeruk bergamot dan kehangatan labdanum.",
            notes = "Top: Calabrian Bergamot • Heart: Rosewood, Sweet Tobacco • Base: Cambodian Oud, Amber",
            price = 225000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 5
        ),
        Perfume(
            id = "sinki-06",
            name = "Vanilla Bourbon",
            category = "Warm Gourmand",
            description = "Manis legit vanilla madagascar dipadukan nuansa oak barel yang matang dan memikat.",
            notes = "Top: Almond, Cognac • Heart: Bourbon Vanilla, Heliotrope • Base: Brown Sugar, Musk",
            price = 185000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 6
        ),
        Perfume(
            id = "sinki-07",
            name = "Citrus Elixir",
            category = "Citrus Aromatic",
            description = "Kesegaran jeruk mandarin Italia berpadu rosemary dan basil hangat untuk siang hari yang cerah.",
            notes = "Top: Italian Mandarin, Grapefruit • Heart: Neroli, Rosemary • Base: Vetiver, Cedar",
            price = 175000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 7
        ),
        Perfume(
            id = "sinki-08",
            name = "Smoky Vetiver",
            category = "Woody Smoky",
            description = "Akar wangi bumi yang maskulin dengan sentuhan asap halus dan kesegaran rempah kering.",
            notes = "Top: Cypress, Cardamom • Heart: Haitian Vetiver, Birch Tar • Base: Oakmoss, Leather",
            price = 195000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 8
        ),
        Perfume(
            id = "sinki-09",
            name = "Imperial Bloom",
            category = "Floral Sophisticated",
            description = "Bunga putih berkelas dengan sentuhan melati malam dan pir renyah yang memikat.",
            notes = "Top: Pear, Freesia • Heart: Jasmine Sambac, Tuberose • Base: White Amber, Cashmere",
            price = 185000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 9
        ),
        Perfume(
            id = "sinki-10",
            name = "Suede & Tobacco",
            category = "Leather Tobacco",
            description = "Aroma kulit lembut dan tembakau manis vanila, memancarkan aura wibawa yang hangat.",
            notes = "Top: Ginger, Orange Zest • Heart: Blonde Tobacco, Leather • Base: Vanilla, Benzoin",
            price = 210000L,
            imageUrl = "",
            isTop10 = true,
            sortOrder = 10
        ),

        // KOLEKSI TAMBAHAN LAINNYA (DITAMPILKAN DI COLLECTION SEBAGAI KATALOG BERSIH)
        Perfume(
            id = "sinki-11",
            name = "Midnight Cedar",
            category = "Woody Clean",
            description = "Kayu cedar bersih dengan angin malam sejuk pegunungan.",
            notes = "Top: Juniper Berries • Heart: Blue Pine, Iris • Base: Virginian Cedar",
            price = 180000L,
            isTop10 = false,
            sortOrder = 11
        ),
        Perfume(
            id = "sinki-12",
            name = "Cardamom Dusk",
            category = "Spicy Warm",
            description = "Kapulaga hangat yang lembut berpadu kayu manis sore hari.",
            notes = "Top: Green Cardamom • Heart: Nutmeg, Lavender • Base: Tonka Bean",
            price = 185000L,
            isTop10 = false,
            sortOrder = 12
        ),
        Perfume(
            id = "sinki-13",
            name = "White Musk & Linen",
            category = "Fresh Clean",
            description = "Aroma sprei bersih dan sabun mewah hotel bintang lima.",
            notes = "Top: Aldehydes, Aldehyde • Heart: Lily of the Valley • Base: White Musk",
            price = 170000L,
            isTop10 = false,
            sortOrder = 13
        ),
        Perfume(
            id = "sinki-14",
            name = "Patchouli Royal",
            category = "Earthy Amber",
            description = "Nilam premium yang telah disuling ganda, sangat kaya dan berkarakter.",
            notes = "Top: Bergamot • Heart: Dark Patchouli, Cocoa • Base: Dry Amber",
            price = 195000L,
            isTop10 = false,
            sortOrder = 14
        ),
        Perfume(
            id = "sinki-15",
            name = "Honey & Fig Wood",
            category = "Sweet Woody",
            description = "Madu liar keemasan berpadu daun ara hijau dan batang pohon hangat.",
            notes = "Top: Green Fig Leaf • Heart: Wild Honey, Coconut Milk • Base: Fig Wood",
            price = 185000L,
            isTop10 = false,
            sortOrder = 15
        )
    )

    val initialReviews = listOf(
        Review(
            id = "rev-01",
            customerName = "Rina S.",
            rating = 5,
            comment = "Parfumnya wangi dan tahan lama. Seharian dipakai aktivitas di kantor aromanya masih nempel.",
            perfumeName = "Amber Wood & Spice",
            date = "Kemarin"
        ),
        Review(
            id = "rev-02",
            customerName = "Dimas K.",
            rating = 5,
            comment = "Aroma Amber Wood-nya mewah banget, tidak bikin pusing sama sekali. Wanginya maskulin dan warm.",
            perfumeName = "Amber Wood & Spice",
            date = "3 hari lalu"
        ),
        Review(
            id = "rev-03",
            customerName = "Amanda P.",
            rating = 5,
            comment = "Packagingnya estetik dan rapi. Suka banget sama Velvet Santal, wanginya lembut menenangkan.",
            perfumeName = "Velvet Santal",
            date = "5 hari lalu"
        ),
        Review(
            id = "rev-04",
            customerName = "Fajar R.",
            rating = 5,
            comment = "Golden Noir juara sih buat jalan malam atau dinner. Kesan aromanya mewah dan beda dari parfum pasaran.",
            perfumeName = "Golden Noir",
            date = "1 minggu lalu"
        ),
        Review(
            id = "rev-05",
            customerName = "Nadia W.",
            rating = 5,
            comment = "Rose Épice perpaduan mawar dan rempahnya pas banget. Banyak teman yang tanya pakai parfum apa.",
            perfumeName = "Rose Épice",
            date = "1 minggu lalu"
        )
    )
}
