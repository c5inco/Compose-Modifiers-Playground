package utils

import kotlinx.browser.window

internal actual fun copyString(str: String) {
    window.navigator.clipboard.writeText(str).then { _: JsAny? ->
        println("Copied to clipboard: $str")
        null
    }
}
