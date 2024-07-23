package com.example.demo

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertContains
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ExamplePaginatedTableControllerTest {

    @InjectMocks
    private lateinit var controller: ExamplePaginatedTableController

    @Test
    fun `paginatedTable - should render table`() {
        val html = controller.paginatedTable()

        val doc: Document = Jsoup.parse(html)

        val result = doc.getElementById("paginated-table-container")

        assertNotNull(result)
    }

    @Test
    fun `tableSearch - should render first page`() {
        val html = controller.tableSearch()

        val doc: Document = Jsoup.parse(html)

        val result = doc.select("tbody tr")
        assertEquals(10, result.size)

        val previousButton = doc.getElementById("pagination-prev")
        val nextButton = doc.getElementById("pagination-next")
        assertEquals("page-item disabled", previousButton.className())
        assertEquals("page-item", nextButton.className())
    }

    @Test
    fun `tableSearch - should render last page`() {
        val html = controller.tableSearch(page = 8)

        val doc: Document = Jsoup.parse(html)

        val result = doc.select("tbody tr")
        assertEquals(5, result.size)

        val previousButton = doc.getElementById("pagination-prev")
        val nextButton = doc.getElementById("pagination-next")
        assertEquals("page-item", previousButton.className())
        assertEquals("page-item disabled", nextButton.className())
    }
}