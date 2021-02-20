package com.github.bytesutilssample

import com.github.bytesutils.ByteArrayList


fun main() {
    val bytesLe = ByteArrayList()
            .add(byteArrayOf(1))
            .add(1)
            .add(1f)
            .add(1.0)
            .add(0, 1)
            .toArray()

    val bytesBe = ByteArrayList()
            .add(1, false)
            .add(1f, false)
            .add(1.0, false)
            .toArray()
}