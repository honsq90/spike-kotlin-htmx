package com.example.demo

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.style

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