package com.example.demo

import kotlinx.html.*
import kotlinx.html.dom.document
import kotlinx.html.stream.createHTML
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {
    @GetMapping("/")
    fun renderDashboard(): String {
        return createHTML()
            .html {
                document {
                    head {
                        link(rel="stylesheet", href = "/webjars/bootstrap/css/bootstrap.min.css") {  }
                        script(src = "/webjars/bootstrap/js/bootstrap.min.js") {  }
                        script(src = "/webjars/htmx.org/2.0.1/dist/htmx.min.js") {  }
                    }
                    body {
                        main {
                            h1("visually-hidden") { +"""Heroes examples""" }
                            div("px-4 py-5 my-5 text-center") {
                                h1("display-5 fw-bold text-body-emphasis") { +"""Centered hero""" }
                                div("col-lg-6 mx-auto") {
                                    p("lead mb-4") { +"""Quickly design and customize responsive mobile-first sites with Bootstrap, the world’s most popular front-end open source toolkit, featuring Sass variables and mixins, responsive grid system, extensive prebuilt components, and powerful JavaScript plugins.""" }
                                    div("d-grid gap-2 d-sm-flex justify-content-sm-center") {
                                        button(classes = "btn btn-primary btn-lg px-4 gap-3") {
                                            attributes["data-testid"] = "hero-primary-button"
                                            type = ButtonType.button
                                            +"""Primary button"""
                                        }
                                        button(classes = "btn btn-outline-secondary btn-lg px-4") {
                                            type = ButtonType.button
                                            +"""Secondary"""
                                        }
                                    }
                                }
                            }
                            div("b-example-divider") {
                            }
                            div("px-4 pt-5 my-5 text-center border-bottom") {
                                h1("display-4 fw-bold text-body-emphasis") { +"""Centered screenshot""" }
                                div("col-lg-6 mx-auto") {
                                    p("lead mb-4") { +"""Quickly design and customize responsive mobile-first sites with Bootstrap, the world’s most popular front-end open source toolkit, featuring Sass variables and mixins, responsive grid system, extensive prebuilt components, and powerful JavaScript plugins.""" }
                                    div("d-grid gap-2 d-sm-flex justify-content-sm-center mb-5") {
                                        button(classes = "btn btn-primary btn-lg px-4 me-sm-3") {
                                            type = ButtonType.button
                                            +"""Primary button"""
                                        }
                                        button(classes = "btn btn-outline-secondary btn-lg px-4") {
                                            type = ButtonType.button
                                            +"""Secondary"""
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

