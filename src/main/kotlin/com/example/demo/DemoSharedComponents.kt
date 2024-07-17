package com.example.demo

import kotlinx.html.*
import kotlinx.html.stream.createHTML

/**
 *
 * interface Props {
 *   indicatorId: string;
 * }
 *
 * export const LoadingIndicator = (props: Props) => {
 *   return <div id={props.indicatorId}>
 *      Loading...
 *   </div>
 * }
 */

fun FlowContent.loadingIndicator(indicatorId: String) {
    div {
        id = indicatorId
        style = "opacity: 0;"
        +"""Loading..."""
    }
}


/**
 *
 * interface Props {
 *   items?: string[];
 *   useBTags: boolean;
 * }
 *
 * const List = (props: Props) => {
 *  return items && items.map((item)) => {
 *    return <div>
 *        <Wrapper>
 *            { useBTags ? <b>{item}</b> : <p>{item}</p> }
 *        </Wrapper>
 *    </div>
 *  })
 * }
 *
 */

fun FlowContent.list(items: List<String>?, useBTags: Boolean = false) {
    items?.forEach { item ->
        div {
            wrapper {
                if (useBTags) {
                    b {
                        +item
                    }
                } else {
                    p {
                        +item
                    }
                }

            }
        }
    }
}

/**
 * const Wrapper = ({children}: {children: JSX.Element}) => <header>{children}</header>
 */
fun FlowContent.wrapper(children: () -> Unit) {
    header {
        children()
    }
}

fun FlowContent.demo(show: Boolean = false) {
    div {
        if (show) {
            p {
                +"Hello"
            }
        }
    }
}

fun renderPage(): String {
    return createHTML()
        .div {
            demo() // follows existing Kotlin behaviour
            demo(show = true)
        }
}