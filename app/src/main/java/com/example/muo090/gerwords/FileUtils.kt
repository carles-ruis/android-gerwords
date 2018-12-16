package com.example.muo090.gerwords

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.*

const val GERMAN_DICTIONARY_ASSET: String = "assets/germanwords.txt"
const val ENGLISH_DICTIONARY_ASSET = "assets/englishwords.txt"
const val GERMAN_DICTIONARY_FILENAME = "germanwords.txt"
const val ENGLISH_DICTIONARY_FILENAME = "englishwords.txt"

class FileUtils(val context: Context) {

    fun exists(filename: String) = File(getInternalFilename(filename)).exists()

    fun read(filename: String) = File(getInternalFilename(filename)).readLines()

    fun append(filename: String, question: String, solution: String) = File(getInternalFilename(filename)).appendText("\n" + question + " " + solution)

    fun createFile(filename: String) {
        val asset = if (GERMAN_DICTIONARY_FILENAME.equals(filename)) GERMAN_DICTIONARY_ASSET else ENGLISH_DICTIONARY_ASSET
        val inputFile = this.javaClass.classLoader?.getResourceAsStream(asset)
        val outputFile = context.openFileOutput(filename, MODE_PRIVATE)
        copyFile(inputFile!!, outputFile)
        inputFile.close()
        outputFile.close()
    }

    private fun copyFile(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(1024)
        var length = input.read(buffer)
        while (length > 0) {
            output.write(buffer, 0, length)
            length = input.read(buffer);
        }
    }

    private fun getInternalFilename(filename: String): String = context.filesDir.path + "/" + filename

}
