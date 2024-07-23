package com.example.demo

import io.github.serpro69.kfaker.faker
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class ExamplePaginatedTableController {

    data class TableDataRow(
        val id: Int,
        val name: String,
        val email: String,
    )

    private val faker = faker {}
    private val list: List<TableDataRow> by lazy {
        List(75) {
            TableDataRow(
                id = it,
                name = faker.name.firstName(),
                email = faker.internet.email(),
            )
        }

    }

    @GetMapping("/example/paginated-table")
    fun paginatedTable(): String {

        return createHTML()
            .div(classes = "text-center") {
                h2 { +"""Paginated table""" }
                div("container mt-5") {
                    id = "paginated-table-container"
                    attributes["hx-get"] = "/example/paginated-table/search"
                    attributes["hx-swap"] = "outerHTML"
                    attributes["hx-trigger"] = "load"

                }
            }
    }


    @GetMapping("/example/paginated-table/search")
    fun tableSearch(
        @RequestParam(required = false) page: Int = 1,
        @RequestParam(required = false) pageSize: Int = 10,
    ): String {
        // Calculate pagination offsets
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, list.size)

        // Extract items for the current page
        val rows = list.subList(startIndex, endIndex)

        val totalPages = list.size / pageSize

        return createHTML()
            .div("container mt-5") {
                id = "paginated-table-container"
                div("row mb-3 justify-content-end") {
                    div("col-auto") {
                        input(classes = "form-control") {
                            type = InputType.text
                            placeholder = "Filter..."
                        }
                    }
                }
                table("table table-striped") {
                    thead {
                        tr {
                            th { +"""#""" }
                            th { +"""Name""" }
                            th { +"""Role""" }
                            th { +"""Email""" }
                        }
                    }
                    tbody {
                        rows.forEach { row ->
                            tr {
                                td { +"${row.id}" }
                                td { +row.name }
                                td { +"""Developer""" }
                                td { +row.email }
                            }
                        }
                    }
                }
                nav {
                    attributes["aria-label"] = "Page navigation"
                    ul("pagination justify-content-end") {
                        val previousClasses = if (page == 1) {
                            "page-item disabled"
                        } else {
                            "page-item"
                        }
                        li(classes = previousClasses) {
                            id="pagination-prev"
                            a(classes = "page-link") {
                                attributes["hx-get"] = "/example/paginated-table/search?page=${page - 1}"
                                attributes["hx-target"] = "#paginated-table-container"
                                tabIndex = "-1"
                                if (page == 1) {
                                    attributes["aria-disabled"] = "true"
                                }
                                +"""Previous"""
                            }
                        }
                        (0..totalPages).forEach {
                            val pageIndex = it + 1
                            val isCurrent = pageIndex == page
                            val listClasses = if (isCurrent) {
                                "page-item active"
                            } else {
                                "page-item"
                            }
                            li(classes = listClasses) {
                                if (isCurrent) {
                                    attributes["aria-current"] = "page"
                                }
                                a(classes = "page-link") {
                                    attributes["hx-get"] = "/example/paginated-table/search?page=${pageIndex}"
                                    attributes["hx-target"] = "#paginated-table-container"
                                    +"""$pageIndex"""
                                    if (isCurrent) {
                                        span("visually-hidden") { +"""(current)""" }
                                    }
                                }
                            }
                        }
                        val nextClasses = if (page > totalPages) {
                            "page-item disabled"
                        } else {
                            "page-item"
                        }
                        li(classes = nextClasses) {
                            id="pagination-next"
                            a(classes = "page-link") {
                                attributes["hx-get"] = "/example/paginated-table/search?page=${page + 1}"
                                attributes["hx-target"] = "#paginated-table-container"
                                if (page > totalPages) {
                                    attributes["aria-disabled"] = "true"
                                }
                                +"""Next"""
                            }
                        }
                    }
                }

            }
    }
}