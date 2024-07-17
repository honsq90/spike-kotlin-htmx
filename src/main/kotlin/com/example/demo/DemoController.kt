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
                                div("col-lg-6 mx-auto") {
                                    p("lead mb-4") { +"""Quickly design and customize responsive mobile-first sites with Bootstrap""" }
                                    div("d-grid gap-2 d-sm-flex justify-content-sm-center") {

                                        form {
                                            id = "updateForm"
                                            attributes["hx-post"] = "/list"
                                            attributes["hx-target"] = "#hero-list"
                                            attributes["hx-indicator"] = "#loadingIndicator"

                                            div(classes = "form-group row") {
                                                label {
                                                    htmlFor = "numberInput"
                                                    +"""Number:"""
                                                }
                                                input(classes = "form-control ") {
                                                    id = "numberInput"
                                                    type = InputType.number
                                                    name = "number"
                                                    max = "100"
                                                    required = true
                                                }

                                            }


                                            button(classes = "btn btn-primary btn-md px-4 gap-3") {
                                                attributes["data-testid"] = "hero-primary-button"
                                                type = ButtonType.submit
                                                +"Click me"
                                            }
                                        }

                                    }
                                    loadingIndicator(indicatorId = "loadingIndicator")
                                }
                            }
                            if (flag) {
                                div("text-center") {
                                    +"I'm feature flagged"
                                }
                            }
                            div("text-center") {
                                p { +"List will be loaded below" }
                                div("text-center") {
                                    div {
                                        id = "hero-list"
                                    }

                                }
                            }
                        }
                    }

                }
            }
    }
}

