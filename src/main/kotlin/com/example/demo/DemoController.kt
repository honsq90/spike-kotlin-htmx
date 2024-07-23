package com.example.demo

import kotlinx.html.*
import kotlinx.html.dom.document
import kotlinx.html.stream.createHTML
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {
    private val flag
        get() = System.getProperty("feature-1").toBoolean()

    @PostMapping("/list")
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

    @PostMapping("/submitUserInput")
    fun renderText(@RequestParam userInput: String): String {
        return createHTML()
            .span { +userInput }
    }

    @GetMapping("/")
    fun renderDashboard(): String {

        return createHTML()
            .html {
                document {
                    head {
                        link(rel = "stylesheet", href = "/webjars/bootstrap/css/bootstrap.min.css") { }
                        script(src = "/webjars/bootstrap/js/bootstrap.min.js") { }
                        script(src = "/webjars/htmx.org/2.0.1/dist/htmx.min.js") { }
                        style {
                            unsafe {
                                raw(".htmx-request { opacity: 1 !important; }")
                            }
                        }
                        title(content = "Spring Boot + Kotlinx.html + htmx")
                    }
                    body {
                        main {
                            h1("visually-hidden") { +"""Heroes examples""" }
                            div("px-4 py-5 text-center") {
                                h1("display-5 fw-bold text-body-emphasis") { +"""Spring Boot + Kotlinx.html + htmx""" }
                                hr { }

                                div("container mt-5") {
                                    ul("nav nav-tabs") {
                                        id = "myTab"
                                        role = "tablist"
                                        li("nav-item") {
                                            role = "presentation"
                                            button(classes = "nav-link active") {
                                                id = "option1-tab"
                                                attributes["data-bs-toggle"] = "tab"
                                                attributes["data-bs-target"] = "#option1"
                                                type = ButtonType.button
                                                role = "tab"
                                                attributes["aria-controls"] = "option1"
                                                attributes["aria-selected"] = "true"
                                                +"""User Input -> Text"""
                                            }
                                        }
                                        li("nav-item") {
                                            role = "presentation"
                                            button(classes = "nav-link") {
                                                id = "option2-tab"
                                                attributes["data-bs-toggle"] = "tab"
                                                attributes["data-bs-target"] = "#option2"
                                                type = ButtonType.button
                                                role = "tab"
                                                attributes["aria-controls"] = "option2"
                                                attributes["aria-selected"] = "false"
                                                +"""Feature Flagged"""
                                            }
                                        }
                                        li("nav-item") {
                                            role = "presentation"
                                            button(classes = "nav-link") {
                                                id = "option3-tab"
                                                attributes["data-bs-toggle"] = "tab"
                                                attributes["data-bs-target"] = "#option3"
                                                type = ButtonType.button
                                                role = "tab"
                                                attributes["aria-controls"] = "option3"
                                                attributes["aria-selected"] = "false"
                                                +"""User Input -> List"""
                                            }
                                        }
                                        li("nav-item") {
                                            role = "presentation"
                                            button(classes = "nav-link") {
                                                id = "option4-tab"
                                                attributes["data-bs-toggle"] = "tab"
                                                attributes["data-bs-target"] = "#option4"
                                                type = ButtonType.button
                                                role = "tab"
                                                attributes["aria-controls"] = "option4"
                                                attributes["aria-selected"] = "false"
                                                +"""Paginated table"""
                                            }
                                        }
                                        li("nav-item") {
                                            role = "presentation"
                                            button(classes = "nav-link") {
                                                id = "option5-tab"
                                                attributes["data-bs-toggle"] = "tab"
                                                attributes["data-bs-target"] = "#option5"
                                                type = ButtonType.button
                                                role = "tab"
                                                attributes["aria-controls"] = "option5"
                                                attributes["aria-selected"] = "false"
                                                +"""Option 5"""
                                            }
                                        }
                                    }

                                    div("tab-content mt-3") {
                                        id = "myTabContent"
                                        div("tab-pane fade show active") {
                                            id = "option1"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "option1-tab"
                                            h2 { +"""User Input -> Text""" }
                                            div("col-lg-6 mx-auto") {
                                                div("d-grid gap-2 d-sm-flex justify-content-sm-center") {
                                                    form(classes = "row row-cols-lg-auto align-items-center") {
                                                        id = "textForm"
                                                        attributes["hx-post"] = "/submitUserInput"
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
                                        }
                                        div("tab-pane fade") {
                                            id = "option2"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "option2-tab"
                                            h2 { +"Feature Flag" }
                                            div("text-center") {
                                                if (flag) {
                                                    +"I'm feature flagged"
                                                } else {
                                                    +"I'm not feature flagged"
                                                }
                                            }

                                        }
                                        div("tab-pane fade") {
                                            id = "option3"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "option3-tab"
                                            h2 { +"User Input -> List" }

                                            div("col-lg-6 mx-auto") {
                                                div("d-grid gap-2 d-sm-flex justify-content-sm-center") {

                                                    form(classes = "row row-cols-lg-auto align-items-center") {
                                                        id = "updateForm"
                                                        attributes["hx-post"] = "/list"
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
                                        div("tab-pane fade") {
                                            id = "option4"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "option4-tab"
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
                                        div("tab-pane fade") {
                                            id = "option5"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "option5-tab"
                                            +"""Content for Option 5"""
                                        }
                                    }
                                }

                            }

                        }

                    }

                }
            }
    }
}

