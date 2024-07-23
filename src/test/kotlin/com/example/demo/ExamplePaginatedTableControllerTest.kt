package com.example.demo

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ExamplePaginatedTableControllerTest {

    @InjectMocks
    private lateinit var controller: ExamplePaginatedTableController

    @Test
    fun `paginatedTable - should render table`() {
        val html = controller.paginatedTable()

        val doc: Document = Jsoup.parse(html)

        val result = doc.getElementsByTag("table")

        kotlin.test.assertEquals(1, result.size)
    }
}