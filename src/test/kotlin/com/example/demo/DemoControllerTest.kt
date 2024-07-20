package com.example.demo

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class DemoControllerTest {

    @InjectMocks
    private lateinit var controller: DemoController

    @Test
    fun `should render default layout`() {
        System.setProperty("feature-1", "false")
        val html = controller.renderDashboard()

        val doc: Document = Jsoup.parse(html)
        val heroPrimaryButton = doc.getElementById("button-generate-items")
        assertEquals("Generate items", heroPrimaryButton.text())
        assertEquals("primary-button", heroPrimaryButton.attr("data-testid"))

        val form = doc.getElementById("updateForm")
        assertEquals("/list", form.attr("hx-post"))
        assertEquals("#list-display", form.attr("hx-target"))

    }

    @Test
    fun `should render feature-1 layout`() {
        System.setProperty("feature-1", "true")
        val html = controller.renderDashboard()

        val doc: Document = Jsoup.parse(html)
        val heroPrimaryButton = doc.getElementById("button-generate-items")
        assertEquals("Generate items", heroPrimaryButton.text())
        assertEquals("primary-button", heroPrimaryButton.attr("data-testid"))

        val form = doc.getElementById("updateForm")
        assertEquals("/list", form.attr("hx-post"))
        assertEquals("#list-display", form.attr("hx-target"))

        assertContains(doc.text(), "I'm feature flagged")
    }

    @Test
    fun `should render list`() {
        val number = 5
        val html = controller.renderList(number)

        val doc: Document = Jsoup.parse(html)

        val listItems = doc.getElementsByTag("li")

        listItems.forEach {
            assertTrue("${it.text()} does not match /Item [1-5]/") { it.text().matches(Regex("Item [1-5]")) }
        }

    }

    @Test
    fun `should render list - 9 items`() {
        val number = 9
        val html = controller.renderList(number)

        val doc: Document = Jsoup.parse(html)

        val listItems = doc.getElementsByTag("li")

        listItems.forEach {
            assertTrue("${it.text()} does not match /Item [1-9]/") { it.text().matches(Regex("Item [1-9]")) }
        }

    }

    @Test
    fun `should render user input`() {
        val html = controller.renderText("hello")

        val doc: Document = Jsoup.parse(html)

        val result = doc.getElementsByTag("span")[0]

        assertEquals("hello", result.text())
    }

    @Test
    fun `should escape user input`() {
        val html = controller.renderText("<script>alert('hi!')</script>")

        val doc: Document = Jsoup.parse(html)

        val result = doc.getElementsByTag("span")[0]

        assertEquals("&lt;script&gt;alert('hi!')&lt;/script&gt;", result.html())
        assertEquals("<script>alert('hi!')</script>", result.text())
    }
}