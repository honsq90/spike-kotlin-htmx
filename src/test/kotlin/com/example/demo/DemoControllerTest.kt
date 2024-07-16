package com.example.demo

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DemoControllerTest {

    @InjectMocks
    private lateinit var controller: DemoController

    @Test
    fun `should render default layout`() {
        val html = controller.renderDashboard()

        val doc: Document = Jsoup.parse(html)
        val heroPrimaryButton = doc.select("[data-testid='hero-primary-button']")
        assertEquals("Click me", heroPrimaryButton.text())
        assertEquals("hero-primary-button", heroPrimaryButton.attr("data-testid"))

        val form = doc.getElementById("updateForm")
        assertEquals("/list", form.attr("hx-post"))
        assertEquals("#hero-list", form.attr("hx-target"))

    }

    @Test
    fun `should render feature-1 layout`() {
        System.setProperty("feature-1", "true")
        val html = controller.renderDashboard()

        val doc: Document = Jsoup.parse(html)
        val heroPrimaryButton = doc.select("[data-testid='hero-primary-button']")
        assertEquals("Click me", heroPrimaryButton.text())
        assertEquals("hero-primary-button", heroPrimaryButton.attr("data-testid"))

        val form = doc.getElementById("updateForm")
        assertEquals("/list", form.attr("hx-post"))
        assertEquals("#hero-list", form.attr("hx-target"))

    }
}