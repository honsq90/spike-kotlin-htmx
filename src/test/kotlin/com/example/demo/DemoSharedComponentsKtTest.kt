package com.example.demo

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


/**
 *
 * describe("LoadingIndicator", () => {
 *     it("should return the specified id", () => {
 *       const component = render(<LoadingIndicator indicatorId="test" />)
 *
 *       expect(component.queryById("test")).toBeInTheDocument()
 *       expect(component.queryById("not-exists")).not.toBeInTheDocument()
 *
 *       expect(component.getByText("Loading...").toBeInTheDocument()
 *     })
 * })
 */

class DemoSharedComponentsKtTest {

    @Test
    fun `loadingIndicator - should return the specified id`() {
        val html = createHTML()
            .div {
                loadingIndicator("test")
            }.toString()
        val component = Jsoup.parse(html)

        assertTrue(component.select("#test").isNotEmpty())
        assertTrue(component.select("#not-exists").isEmpty())

        assertContains(component.body().text(), "Loading...")
    }

    @Test
    fun `list - should render headers in wrapper component using default tag`() {
        val items = listOf("Test 1", "Test 2")
        val html = createHTML()
            .div {
                list(items)
            }.toString()
        val component = Jsoup.parse(html)

        val headers = component.select("div > header > p")
        assertEquals(2, headers.size)
        assertEquals(items, headers.map { it.text() })

        val bHeaders = component.select("div > header > b")
        assertEquals(0, bHeaders.size)
    }

    @Test
    fun `list - should render headers in wrapper component using b tags`() {
        val items = listOf("Test 1", "Test 2")
        val html = createHTML()
            .div {
                list(items, useBTags = true)
            }.toString()
        val component = Jsoup.parse(html)

        val headers = component.select("div > header > b")
        assertEquals(2, headers.size)
        assertEquals(items, headers.map { it.text() })

        val pHeaders = component.select("div > header > p")
        assertEquals(0, pHeaders.size)
    }

    @Test
    fun `list - should not render anything if the list is null`() {
        val html = createHTML()
            .div {
                list(null)
            }.toString()
        val component = Jsoup.parse(html)

        val headers = component.select("div > header > b")
        assertEquals(0, headers.size)

        val pHeaders = component.select("div > header > p")
        assertEquals(0, pHeaders.size)
    }

}