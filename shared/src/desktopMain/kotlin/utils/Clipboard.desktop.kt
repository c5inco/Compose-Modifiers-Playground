package utils

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

internal actual fun copyString(str: String) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(StringSelection(str), null)

    println("Copied to clipboard: $str")
}