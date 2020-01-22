package com.example.muo090.gerwords

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.File

/* private fun copyFile(input: InputStream, output: OutputStream) {
    val buffer = ByteArray(1024)
    var length = input.read(buffer)
    while (length > 0) {
        output.write(buffer, 0, length)
        length = input.read(buffer)
    }
} */

fun String.existsFile() = File(this).exists()

fun String.readLines(action: (String) -> Unit) = File(this).useLines { lines -> lines.forEach(action) }

fun String.append(entry: Pair<String, String>) = File(this).appendText("\n" + entry.first + " " + entry.second)

fun String.createFileFromAsset(context: Context, asset: String) {
    val output = context.openFileOutput(this, MODE_PRIVATE)
    context.javaClass.classLoader?.getResourceAsStream(asset)?.use { input ->
        input.copyTo(output)
    }
}