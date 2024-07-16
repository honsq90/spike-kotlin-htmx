# Experiment Purpose

> To reduce the complexity of a full stack feature delivery by replacing Node.js tooling with Kotlin equivalents

As the Node.js ecosystem and tooling evolve, one would need to keep these tools up to date:
- Node.js; v14 (April 2020) -> v24 (Jul 2024)
- React
- React Hooks
- styled-components
- axios/fetch
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

In a traditional Spring + React ecosystem, a full feature slice usually involves:

1. Create Spring JPA Repo
1. Create Spring Service
1. Create Spring Controller
1. Create Kotlin data classes
1. Create JUnit tests
1. Create new Webpack entry
1. Create new API retrieval layer
1. Create Typescript data classes to match API response
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

# Feature comparison

Ultimately, each Node.js ecosystem tool aims to improve the predictability of how we develop, test and package interactive user interfaces.

Feature | With Node.js | Without Node.js
------|--------|--------
Dependecy Management | Maven + NPM/Yarn | Maven + [webjars](https://www.webjars.org/)
Module management | Maven + Yarn workspaces/Nx/Lerna + Webpack entries + Webpack Module Federation | Maven
Date formatting | Java date + moment.js + date-fns | Java date
Currency formatting | Java currency + browser locale | Java currency
Syntaxes | Kotlin + Thymeleaf + React + Styled Components | Kotlin + htmx
Safety checks | Kotlin + Typescript + ESLint | Kotlin
Logging/Debugging | Kotlin + Browser console + Sentry  | Kotlin
Data retrieval | Kotlin data class + Typescript interface + axios/fetch | Kotlin data class
Conditional UIs | Kotlin + Typescript + React JSX | Kotlin
CI | JUnit + Spring Testing + Webpack + Babel + Minifying + Typescript + ESLint + Jest | JUnit + Spring Testing + JSoup
Artifacts | jar/war + Webpack vendor bundles + Webpack hashed entrypoints + Webpack hashed JS/CSS bundles | jar/war

