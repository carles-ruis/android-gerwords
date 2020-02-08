package com.example.muo090.gerwords

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun String.existsFile() = File(this).exists()

fun String.readLines(action: (String) -> Unit) = File(this).useLines { lines -> lines.forEach(action) }

fun String.append(entry: Pair<String, String>) = File(this).appendText("\n" + entry.first + " " + entry.second)

fun String.createFileFromAsset(context: Context, asset: String) {
    val output = FileOutputStream(File(this))
    context.javaClass.classLoader?.getResourceAsStream(asset)?.use { input ->
        input.copyTo(output)
    }
}