package com.example.muo090.gerwords

import android.content.Context
import java.io.File
import java.io.FileOutputStream

interface FileHandler {

    fun createFile(context: Context, file: String, asset: String)

    fun loadFile(file: String): List<String>

    fun appendToFile(file: String, line: String)
}

class FileDelegate : FileHandler {

    override fun createFile(context: Context, file: String, asset: String) {
        file.createFileFromAsset(context, asset)
    }

    override fun loadFile(file: String): List<String> {
        if (file.doesNotExist()) {
            throw FileDoesNotExistException()
        }
        return file.readLines()
    }

    override fun appendToFile(file: String, line: String) {
        file.append(line)
    }

    private fun String.doesNotExist() = File(this).exists().not()

    private fun String.readLines() = File(this).readLines()

    private fun String.append(line: String) = File(this).appendText("\n" + line)

    private fun String.createFileFromAsset(context: Context, asset: String) {
        val output = FileOutputStream(File(this))
        context.javaClass.classLoader?.getResourceAsStream(asset)?.use { input ->
            input.copyTo(output)
        }
    }
}

