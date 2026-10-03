package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.sinki.viewmodel.SinkiViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SINKI", appName)
  }

  @Test
  fun `verify SinkiViewModel initial state and order formatting`() {
    val viewModel = SinkiViewModel()
    val state = viewModel.uiState.value

    assertEquals(0, state.currentPageIndex)
    assertEquals(10, state.top10Perfumes.size)
    assertNotNull(state.selectedPerfume)
    assertEquals("30ml", state.selectedSize)
    assertEquals(1, state.quantity)

    // Test message creation
    val message = viewModel.createWhatsAppMessage()
    assertTrue(message.contains("Halo SINKI, saya ingin memesan:"))
    assertTrue(message.contains("Parfum: ${state.selectedPerfume.name}"))
    assertTrue(message.contains("Mohon informasi ketersediaannya."))
  }
}
