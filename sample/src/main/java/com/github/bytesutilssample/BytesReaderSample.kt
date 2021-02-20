package com.github.bytesutilssample

import com.github.bytesutils.ByteArrayList
import com.github.bytesutils.BytesReader

fun main() {
    val array = ByteArrayList()
            .add(1)
            .add(3.0f)
            .add(32L)
            .add("123", "UTF-8")
            .toArray()

    val reader = BytesReader(array)
    val index1 = reader.s32
    val index2 = reader.float
    val index3 = reader.s64
    val stringLength = "123".toByteArray(charset("UTF-8")).size
    val index4 = reader.getString(stringLength, "UTF-8")
    println("index1 = $index1 index2 = $index2 index3 = $index3 index4 = $index4")
}