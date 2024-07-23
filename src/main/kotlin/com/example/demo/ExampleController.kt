package com.example.demo

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {
    private val flag
        get() = System.getProperty("feature-1").toBoolean()

    @GetMapping("/example/feature-flag")
    fun featureFlag(): String {
        return createHTML()
            .div(classes = "text-center") {
                h2 { +"Feature Flag" }
                div("text-center") {
                    if (flag) {
                        +"I'm feature flagged"
                    } else {
                        +"I'm not feature flagged"
                    }
                }
            }
    }

    @PostMapping("/example/user-input")
    fun renderText(@RequestParam userInput: String): String {
        return createHTML()
            .span { +userInput }
    }


    @PostMapping("/example/user-input/list")
    fun renderList(@RequestParam number: Int): String {
        return createHTML()
            .ul {
                repeat(number) {
                    li {
                        +"Item ${it + 1}"
                    }
                }
            }
    }


    @GetMapping("/example/user-input")
    fun userInputs(): String {
        return createHTML()
            .div(classes = "text-center") {
                h2 { +"""User Input -> Text""" }
                div("col-lg-6 mx-auto") {
                    div("d-grid gap-2 d-sm-flex justify-content-sm-center") {
                        form(classes = "row row-cols-lg-auto align-items-center") {
                            id = "textForm"
                            attributes["hx-post"] = "/example/user-input"
                            attributes["hx-target"] = "#user-input-display"

                            div(classes = "col-12") {
                                label("visually-hidden") {
                                    htmlFor = "textInput"
                                }
                                input(classes = "form-control") {
                                    id = "userInput"
                                    type = InputType.text
                                    name = "userInput"
                                    required = true
                                    placeholder = "Enter some text to display"
                                }

                            }

                            div(classes = "col-12") {
                                button(classes = "btn btn-primary btn-md px-4 gap-3") {
                                    id = "button-submit-user-input"
                                    type = ButtonType.submit
                                    +"Submit"
                                }
                            }
                        }

                    }
                    div {
                        id = "user-input-display"
                    }
                }
                hr {  }
                h2 { +"User Input -> List" }

                div("col-lg-6 mx-auto") {
                    div("d-grid gap-2 d-sm-flex justify-content-sm-center") {

                        form(classes = "row row-cols-lg-auto align-items-center") {
                            id = "updateForm"
                            attributes["hx-post"] = "/example/user-input/list"
                            attributes["hx-target"] = "#list-display"
                            attributes["hx-indicator"] = "#loadingIndicator"

                            div(classes = "col-12") {
                                label("visually-hidden") {
                                    htmlFor = "inlineFormInputGroupUsername"
                                    +"""Username"""
                                }

                                input(classes = "form-control") {
                                    id = "numberInput"
                                    type = InputType.number
                                    name = "number"
                                    max = "100"
                                    required = true
                                    placeholder = "Enter a number to generate items below"
                                }

                            }
                            div("col-12") {
                                button(classes = "btn btn-primary btn-md px-4 gap-3") {
                                    id = "button-generate-items"
                                    attributes["data-testid"] = "primary-button"
                                    type = ButtonType.submit
                                    +"Generate items"
                                }
                            }
                        }

                    }
                    loadingIndicator(indicatorId = "loadingIndicator")
                    div("text-center") {
                        p { +"List will be loaded below" }
                        div("text-center") {
                            div {
                                id = "list-display"
                            }

                        }
                    }
                }
            }
    }

    @GetMapping("/example/paginated-table")
    fun paginatedTable(): String {
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
                            tr {
                                td { +"""1""" }
                                td { +"""John Doe""" }
                                td { +"""Developer""" }
                                td { +"""john.doe@example.com""" }
                            }
                            tr {
                                td { +"""2""" }
                                td { +"""Jane Smith""" }
                                td { +"""Designer""" }
                                td { +"""jane.smith@example.com""" }
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