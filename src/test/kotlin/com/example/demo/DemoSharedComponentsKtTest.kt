package com.example.demo

import kotlinx.html.div
import kotlinx.html.stream.createHTML
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertContains


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
    fun `should return the specified id`() {
        val html = createHTML()
            .div {
                loadingIndicator("test")
            }.toString()
        val component = Jsoup.parse(html)

        assertTrue(component.select("#test").isNotEmpty())
        assertTrue(component.select("#not-exists").isEmpty())

        assertContains(component.body().text(), "Loading...")
    }
}