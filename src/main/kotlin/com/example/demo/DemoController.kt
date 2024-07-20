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
                                hr {  }
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
                            if (flag) {
                                div("text-center") {
                                    +"I'm feature flagged"
                                }
                            }
                            hr {  }
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

                    }

                }
            }
    }
}

