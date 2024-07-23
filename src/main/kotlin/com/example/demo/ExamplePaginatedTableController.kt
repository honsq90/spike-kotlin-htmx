package com.example.demo

import io.github.serpro69.kfaker.faker
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.web.bind.annotation.GetMapping
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
        List(50) {
            TableDataRow(
                id = it,
                name = faker.name.firstName(),
                email = faker.internet.email(),
            )
        }

    }

    @GetMapping("/example/paginated-table")
    fun paginatedTable(): String {

        val rows = list.take(10)

        return createHTML()
            .div(classes = "text-center") {
                h2 { +"""Paginated table""" }
                div("container mt-5") {
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
                            li("page-item disabled") {
                                a(classes = "page-link") {
                                    href = "#"
                                    tabIndex = "-1"
                                    attributes["aria-disabled"] = "true"
                                    +"""Previous"""
                                }
                            }
                            li("page-item active") {
                                attributes["aria-current"] = "page"
                                a(classes = "page-link") {
                                    href = "#"
                                    +"""1"""
                                    span("visually-hidden") { +"""(current)""" }
                                }
                            }
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "#"
                                    +"""2"""
                                }
                            }
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "#"
                                    +"""3"""
                                }
                            }
                            li("page-item") {
                                a(classes = "page-link") {
                                    href = "#"
                                    +"""Next"""
                                }
                            }
                        }
                    }
                }
            }
    }
}