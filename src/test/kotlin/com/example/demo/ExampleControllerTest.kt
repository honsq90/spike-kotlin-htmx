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
class ExampleControllerTest {

    @InjectMocks
    private lateinit var controller: ExampleController

    @Test
    fun `userInputs - should render inputs`() {
        val html = controller.userInputs()

        val doc: Document = Jsoup.parse(html)
        val generateButton = doc.getElementById("button-generate-items")
        assertEquals("Generate items", generateButton.text())
        assertEquals("primary-button", generateButton.attr("data-testid"))

        val form = doc.getElementById("updateForm")
        assertEquals("/example/user-input/list", form.attr("hx-post"))
        assertEquals("#list-display", form.attr("hx-target"))

    }

    @Test
    fun `featureFlag - should render default layout`() {
        System.setProperty("feature-1", "false")
        val html = controller.featureFlag()

        val doc: Document = Jsoup.parse(html)

        assertContains(doc.text(), "I'm not feature flagged")
    }

    @Test
    fun `featureFlag - should render feature-1 layout`() {
        System.setProperty("feature-1", "true")
        val html = controller.featureFlag()

        val doc: Document = Jsoup.parse(html)

        assertContains(doc.text(), "I'm feature flagged")
    }

    @Test
    fun `renderList - should render list`() {
        val number = 5
        val html = controller.renderList(number)

        val doc: Document = Jsoup.parse(html)

        val listItems = doc.getElementsByTag("li")

        listItems.forEach {
            assertTrue("${it.text()} does not match /Item [1-5]/") { it.text().matches(Regex("Item [1-5]")) }
        }

    }

    @Test
    fun `renderList - should render list - 9 items`() {
        val number = 9
        val html = controller.renderList(number)

        val doc: Document = Jsoup.parse(html)

        val listItems = doc.getElementsByTag("li")

        listItems.forEach {
            assertTrue("${it.text()} does not match /Item [1-9]/") { it.text().matches(Regex("Item [1-9]")) }
        }

    }

    @Test
    fun `renderText - should render user input`() {
        val html = controller.renderText("hello")

        val doc: Document = Jsoup.parse(html)

        val result = doc.getElementsByTag("span")[0]

        assertEquals("hello", result.text())
    }

    @Test
    fun `renderText - should escape user input`() {
        val html = controller.renderText("<script>alert('hi!')</script>")

        val doc: Document = Jsoup.parse(html)

        val result = doc.getElementsByTag("span")[0]

        assertEquals("&lt;script&gt;alert('hi!')&lt;/script&gt;", result.html())
        assertEquals("<script>alert('hi!')</script>", result.text())
    }

}