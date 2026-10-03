package com.example.sinki.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.example.sinki.ui.components.ChampagneMarbleBackground
import com.example.sinki.ui.components.MarqueeHeader
import com.example.sinki.ui.components.SinkiBottomNav
import com.example.sinki.ui.screens.CollectionScreen
import com.example.sinki.ui.screens.HomeScreen
import com.example.sinki.ui.screens.OrderScreen
import com.example.sinki.ui.screens.ReviewsScreen
import com.example.sinki.ui.screens.Top10Screen
import com.example.sinki.viewmodel.SinkiViewModel
import kotlinx.coroutines.launch

/**
 * Komponen Utama Aplikasi SINKI
 * Menangani:
 * - Latar belakang Warm Champagne Marble
 * - Teks berjalan (Marquee) di bagian atas
 * - Horizontal Pager untuk navigasi swipe antar 5 halaman utama
 * - Bottom Navigation Bar tetap di bagian bawah
 * - Sinkronisasi dua arah antara swipe gesture dan bottom navigation
 */
@Composable
fun MainApp(
    viewModel: SinkiViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 5 })
    val coroutineScope = rememberCoroutineScope()

    // Sinkronisasi saat viewModel mengubah currentPageIndex (misal tombol di card atau Beranda ditekan)
    LaunchedEffect(uiState.currentPageIndex) {
        if (pagerState.currentPage != uiState.currentPageIndex) {
            pagerState.animateScrollToPage(uiState.currentPageIndex)
        }
    }

    // Sinkronisasi saat pengguna melakukan swipe horizontal
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (uiState.currentPageIndex != page) {
                viewModel.setPageIndex(page)
            }
        }
    }

    ChampagneMarbleBackground(modifier = modifier) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                // Teks Berjalan (Marquee) Halus di Bagian Atas dengan safe-area status bar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                ) {
                    MarqueeHeader()
                }
            },
            bottomBar = {
                // Bottom Navigation Bar tetap di bagian bawah layar smartphone
                SinkiBottomNav(
                    selectedIndex = pagerState.currentPage,
                    onItemSelected = { index ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Horizontal Pager: Mendukung Gesture Swipe Horizontal
                // Beranda (0) -> Collection (1) -> Top 10 (2) -> Order (3) -> Penilaian (4)
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("sinki_horizontal_pager")
                ) { page ->
                    when (page) {
                        0 -> HomeScreen(
                            onNavigateToCollection = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                            },
                            onNavigateToTop10 = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(2)
                                }
                            }
                        )

                        1 -> CollectionScreen(
                            perfumes = uiState.filteredCollection,
                            categories = uiState.categories,
                            selectedCategory = uiState.selectedCategory,
                            onCategorySelected = { category ->
                                viewModel.selectCategory(category)
                            },
                            onPerfumeSelected = { perfume ->
                                viewModel.selectPerfumeForOrder(perfume, navigateToOrder = true)
                            }
                        )

                        2 -> Top10Screen(
                            top10List = uiState.top10Perfumes,
                            onOrderClick = { perfume ->
                                viewModel.selectPerfumeForOrder(perfume, navigateToOrder = true)
                            }
                        )

                        3 -> OrderScreen(
                            allPerfumes = uiState.perfumes,
                            selectedPerfume = uiState.selectedPerfume,
                            selectedSize = uiState.selectedSize,
                            quantity = uiState.quantity,
                            unitPrice = uiState.calculatedUnitPrice,
                            totalPrice = uiState.totalPrice,
                            customerNote = uiState.customerNote,
                            onSelectPerfume = { perfume ->
                                viewModel.selectPerfumeForOrder(perfume, navigateToOrder = false)
                            },
                            onSelectSize = { size ->
                                viewModel.selectSize(size)
                            },
                            onIncrementQuantity = {
                                viewModel.incrementQuantity()
                            },
                            onDecrementQuantity = {
                                viewModel.decrementQuantity()
                            },
                            onNoteChange = { note ->
                                viewModel.setCustomerNote(note)
                            },
                            onOrderWhatsApp = { context ->
                                viewModel.sendOrderViaWhatsApp(context)
                            }
                        )

                        4 -> ReviewsScreen(
                            reviews = uiState.reviews,
                            isDialogOpen = uiState.isReviewDialogOpen,
                            onOpenDialog = {
                                viewModel.setReviewDialogVisible(true)
                            },
                            onDismissDialog = {
                                viewModel.setReviewDialogVisible(false)
                            },
                            onSubmitReview = { name, rating, comment, perfumeName ->
                                viewModel.submitReview(name, rating, comment, perfumeName)
                            }
                        )
                    }
                }
            }
        }
    }
}
