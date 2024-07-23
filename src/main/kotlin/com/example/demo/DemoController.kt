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
                        attributes["data-bs-theme"] = "dark"
                        main {
                            div(classes = "container") {
                                header("d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom") {
                                    a(classes = "d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none") {
                                        href = "/"
                                        span("fs-4") { +"""Spring Boot + Kotlinx.html + htmx""" }
                                    }
                                    ul("nav nav-pills") {
                                        li("nav-item") {
                                            a(classes = "nav-link active") {
                                                href = "/"
                                                +"""Home"""
                                            }
                                        }
                                        li("nav-item") {
                                            a(classes = "nav-link") {
                                                attributes["hx-get"] = "/example/feature-flag"
                                                attributes["hx-target"] = "#tabContent"

                                                +"""Feature flag"""
                                            }
                                        }
                                        li("nav-item") {
                                            a(classes = "nav-link") {
                                                attributes["hx-get"] = "/example/user-input"
                                                attributes["hx-target"] = "#tabContent"

                                                +"""User inputs"""
                                            }
                                        }
                                        li("nav-item") {
                                            a(classes = "nav-link") {
                                                attributes["hx-get"] = "/example/paginated-table"
                                                attributes["hx-target"] = "#tabContent"

                                                +"""Paginated table"""
                                            }
                                        }
                                    }
                                }
                            }
                            div(classes = "container") {
                                id = "tabContent"
                            }

                        }

                    }

                }
            }
    }
}

