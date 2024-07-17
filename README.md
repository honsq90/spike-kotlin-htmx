# Spring Boot + kotlinx.html + htmx

Inspired by [Radical Simplicity](https://www.radicalsimpli.city/) and [DjangoCon 2022 | From React to htmx on a real-world SaaS product: we did it, and it's awesome!](https://www.youtube.com/watch?v=3GObi93tjZI)

![demo](./docs/demo.gif)

## Goal

> To reduce the complexity of a full stack feature delivery by replacing Node.js tooling with Kotlin equivalents

As the Node.js ecosystem and tooling evolve, the number of tools to keep up to date with is quite significant:

- Node.js; v14 (April 2020) -> v24 (Jul 2024)
- React
- React Hooks
- styled-components
- axios/fetch/GraphQL
- MobX / Redux
- Webpack / Parcel / esbuild
- Babel
- Javascript
- Typescript
- ESLint
- Storybook
- CSS / SASS / PostCSS / Autoprefixer

This overhead is compounded due to each library having different deprecation schedules.

## Chosen technologies

### Kotlinx.html

This replaces Typescript and JSX completely, with built-in compile time feedback on nullable data.

Conditional code can now leverage Kotlin, making it easier to test.

Debugging can be done completely within the JVM, including generating logs during the generation of the html response.

For example:

```kotlin

body {
    if (flag) {
        div("text-center") {
            +"I'm feature flagged"
        }
    }
}
```

### htmx

Now that templating is resolved, we need to handle data retrieval and state management.

htmx provides a HTML attribute based approach, removing the need for:

- axios/fetch
- UI state management (React Hooks / MobX / Redux)

```kotlin
button(classes = "btn btn-primary btn-md px-4 gap-3") {
    attributes["hx-get"] = "/data-list"
    attributes["hx-target"] = "#data-list"
    attributes["data-testid"] = "primary-button"
    type = ButtonType.submit
    +"Click me"
}

div {
    id = "data-list"
}
```

## Comparison

<details>
<summary>Using Spring + Thymeleaf + React</summary>

1. Create Spring JPA Repo
1. Create Spring Service
1. Create Spring Controller
1. Create Kotlin data classes
1. Create JUnit tests
1. Create new Webpack entry
1. Create new API retrieval layer
1. Create Typescript data classes to match API response
1. Create UI state (MobX observers or Redux actions/dispatchers/reducers)
1. Create React component
1. Create Jest tests
1. Bundle TS and JS vendor files into minified vendor assets
1. Bundle TS and JS project files into minified project assets
1. Host JS and CSS either on a CDN or in a static folder
1. Spring Thymeleaf generates HTML
1. Load React into DOM
1. Load project bundle into DOM
1. Initialise React application
1. Fetch data from API
1. Parse JSON into Javascript objects
1. React creates a virtual DOM and observes for stateful changes
1. React re-renders the relevant DOM elements

</details>

<details>
<summary>Using Kotlinx.html and htmx</summary>

1. Create Spring JPA Repo
1. Create Spring Service
1. Create Spring Controller
1. Create Kotlin data classes
1. Create Kotlinx.html template with htmx tags
1. Create JUnit tests
1. Spring Thymeleaf generates HTML
1. Load base styles into DOM
1. htmx re-renders the relevant DOM elements

</details>

# Feature comparison

Ultimately, each Node.js ecosystem tool aims to improve the predictability of how we develop, test and package
interactive user interfaces.

| Feature               | With Node.js                                                                                 | Without Node.js                             |
|-----------------------|----------------------------------------------------------------------------------------------|---------------------------------------------|
| Dependency Management | Maven + NPM/Yarn                                                                             | Maven + [webjars](https://www.webjars.org/) |
| Module management     | Maven + Yarn workspaces/Nx/Lerna + Webpack entries + Webpack Module Federation               | Maven                                       |
| Date formatting       | Java date + moment.js + date-fns                                                             | Java date                                   |
| Currency formatting   | Java currency + browser locale                                                               | Java currency                               |
| Syntaxes              | Kotlin + Thymeleaf + React + Styled Components                                               | Kotlin + htmx                               |
| Safety checks         | Kotlin + Typescript + ESLint                                                                 | Kotlin                                      |
| Logging/Debugging     | Kotlin + Browser console + Sentry                                                            | Kotlin                                      |
| Data retrieval        | Kotlin data class + Typescript interface + axios/fetch                                       | Kotlin data class                           |
| Conditional UIs       | Kotlin + Typescript + React JSX                                                              | Kotlin                                      |
| CI                    | JUnit + Spring Testing + Webpack + Babel + Minifying + Typescript + ESLint + Jest            | JUnit + Spring Testing + JSoup              |
| Artifacts             | jar/war + Webpack vendor bundles + Webpack hashed entrypoint + Webpack hashed JS/CSS bundles | jar/war                                     |
| UI Datepicker         | react-datepicker / AntD / react-bootstrap + native HTML date input                           | (wip)                                       |
| UI Daterangepicker    | react-daterangepicker / AntD / react-bootstrap + native HTML date input                      | (wip)                                       |
| UI Datatables         | react-data-table-component / AntD / react-bootstrap                                          | (wip)                                       |
| UI Combobox           | react-select / AntD / react-bootstrap                                                        | (wip)                                       |
| UI Modal              | react-modal / AntD / react-bootstrap                                                         | (wip)                                       |

## Examples

### Testing with Kotlinx.html

```kotlin

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

// ...

@Test
fun `should render list`() {
    val number = 5
    val html = controller.renderList(number)

    val doc: Document = Jsoup.parse(html)

    val listItems = doc.getElementsByTag("li")

    listItems.forEach {
        it.text() shouldContain "Item "
    }

}

```

### Creating nested components

```typescript
const Wrapper = ({children}: {children: JSX.Element}) => {
  return <header>{children}</header>
}

const Page = () => {
    return <div>
        <Wrapper>
            <p>Hello</p>
        </Wrapper>
    </div>
}
```


```kotlin

fun FlowContent.wrapper(children: () -> Unit) {
    header {
        children()
    }
}


fun FlowContent.page() {
    div {
        wrapper {
            p {
                +"Hello"
            }
        }
    }
}

```


### Creating conditional UIs

```typescript
interface Props { 
    show: boolean;
}

const Demo = ({show = false}: Props) => {
    return <div>
        {show && <p>Hello</p>}
    </div>
}


const Page = () => {
    return <div>
        <Demo/>
        <Demo show={true}/>
    </div>
}
```


```kotlin

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
            demo()
            demo(show = true)
        }
}

```

### Type safety

![](./docs/type_safety.png)

### Test coverage

![](./docs/coverage.png)
