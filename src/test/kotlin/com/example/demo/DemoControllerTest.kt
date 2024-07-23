package com.example.demo

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class DemoControllerTest {

    @InjectMocks
    private lateinit var controller: DemoController

    @Test
    fun `renderDashboard - should render tabs`() {
        val html = controller.renderDashboard()

        val doc: Document = Jsoup.parse(html)

        assertEquals("User inputs", doc.getElementsByAttributeValue("hx-get", "/example/user-input")[0].text())
        assertEquals("Paginated table", doc.getElementsByAttributeValue("hx-get", "/example/paginated-table")[0].text())
        assertEquals("Feature flag", doc.getElementsByAttributeValue("hx-get", "/example/feature-flag")[0].text())
    }
}