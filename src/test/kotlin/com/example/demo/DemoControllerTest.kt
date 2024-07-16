package com.example.demo

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DemoControllerTest {

    @InjectMocks private lateinit var controller: DemoController

    @Test
    fun renderDashboard() {
        val html = controller.renderDashboard()

        val doc: Document = Jsoup.parse(html)
        val heroPrimaryButton = doc.select("[data-testid='hero-primary-button']")
        assertEquals("Primary button", heroPrimaryButton.text())
        assertEquals("hero-primary-button", heroPrimaryButton.attr("data-testid"))
    }
}