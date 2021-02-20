package com.github.bytesutilssample

import com.github.bytesutils.ByteArrayList
import com.github.bytesutils.DataDescribe
import com.github.bytesutils.DataType
import com.github.bytesutils.Tree


fun main() {
    val stringLength = "123".toByteArray(charset("UTF-8")).size

    val publicTreeDescribes = arrayOf(DataDescribe(DataType.TYPE_U8), DataDescribe(DataType.TYPE_U8))

    val describes = arrayOf(DataDescribe(DataType.TYPE_TREE),
            DataDescribe(DataType.TYPE_U8), DataDescribe(DataType.TYPE_S32),
            DataDescribe(DataType.TYPE_FLOAT), DataDescribe(DataType.TYPE_U32),
            DataDescribe(DataType.TYPE_MUABLE0, stringLength))

    val array = ByteArrayList()
            .add(1.toByte())
            .add(2.toByte())
            .add(3.toByte())
            .add(1)
            .add(3.0f)
            .add(32)
            .add(stringLength.toByte())
            .add("123", "UTF-8")
            .toArray()

    val paresTree = Tree(describes)
            .setChildTree(0, publicTreeDescribes)
    paresTree.parserByteArray(array)
    val childTree = paresTree.getChildTree(0)
    val childTreeIndex0 = childTree.getU8(0)
    val childTreeIndex1 = childTree.getU8(1)
    val index0 = paresTree.getU8(1)
    val index1 = paresTree.getS32(2)
    val index2 = paresTree.getFloat(3)
    val index3 = paresTree.getU32(4)
    val index4 = paresTree.getString(5, "UTF-8")
    println("childTreeIndex0 = $childTreeIndex0 childTreeIndex1 = $childTreeIndex1")
    println("index0 = $index0 index1 = $index1 index2 = $index2 index3 = $index3 index4 = $index4")

    val publicTree = Tree(publicTreeDescribes)
            .setU8(0, 1)
            .setU8(1, 2)

    val buildTree = Tree(describes)
            .setChildTree(0, publicTree)
            .setU8(1, 3)
            .setS32(2, 1)
            .setFloat(3, 3.0f)
            .setU32(4, 32L)
            .setString(5, "123", "UTF-8")
    val buildTreeResult = buildTree.toByteArray()


    paresTree.parserByteArray(buildTreeResult)
    val treeResultChildTree = paresTree.getChildTree(0)
    val treeResultChildTreeIndex0 = treeResultChildTree.getU8(0)
    val treeResultChildTreeIndex1 = treeResultChildTree.getU8(1)
    val treeResultIndex0 = paresTree.getU8(1)
    val treeResultIndex1 = paresTree.getS32(2)
    val treeResultIndex2 = paresTree.getFloat(3)
    val treeResultIndex3 = paresTree.getU32(4)
    val treeResultIndex4 = paresTree.getString(5, "UTF-8")

    println("treeResultChildTreeIndex0 = $treeResultChildTreeIndex0 treeResultChildTreeIndex1 = $treeResultChildTreeIndex1")
    println("treeResultIndex0 = $treeResultIndex0 treeResultIndex1 = $treeResultIndex1 " +
            "treeResultIndex2 = $treeResultIndex2 treeResultIndex3 = $treeResultIndex3 " +
            "treeResultIndex4 = $treeResultIndex4")
}
