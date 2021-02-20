package com.github.bytesutils;

/**
 * 数据的类型,表示一段byte数组是什么类型的数据
 */
public interface DataType {
    /**
     * 无符号8位 java用short接收
     */
    int TYPE_U8 = 0;
    /**
     * 有符号8位 byte
     */
    int TYPE_S8 = 1;
    /**
     * 无符号16位 java用int接收
     */
    int TYPE_U16 = 2;
    /**
     * 有符号16位 short
     */
    int TYPE_S16 = 3;
    /**
     * 无符号32位 java用long接收
     */
    int TYPE_U32 = 4;
    /**
     * 有符号32位 int
     */
    int TYPE_S32 = 5;
    /**
     * 无符号64位 目前不支持
     */
    int TYPE_U64 = 6;
    /**
     * 有符号64位 long
     */
    int TYPE_S64 = 7;
    /**
     * float
     */
    int TYPE_FLOAT = 8;
    /**
     * double
     */
    int TYPE_DOUBLE = 9;
    /**
     * 可变数据类型1 第一字节为无符号8位 表示后续的内容长度 长度后续紧跟内容
     */
    int TYPE_MUABLE0 = 10;
    /**
     * 可变数据类型2 前两字节为小端无符号16位 表示后续的内容长度 长度后续紧跟内容
     */
    int TYPE_MUABLE1 = 11;
    /**
     * 可变数据类型3 前四字节为小端无符号32位 表示后续的内容长度 长度后续紧跟内容
     */
    int TYPE_MUABLE2 = 12;
    /**
     * 可变数据类型4 前两字节为大端无符号16位 表示后续的内容长度 长度后续紧跟内容
     */
    int TYPE_MUABLE1_BE = 13;
    /**
     * 可变数据类型5 前四字节为大端无符号32位 表示后续的内容长度 长度后续紧跟内容
     */
    int TYPE_MUABLE2_BE = 14;
    /**
     * 树结构类型
     */
    int TYPE_TREE = 100;
    /**
     * 原始数据类型 byte[]
     * 当初始化时指定了长度时在获取时会获取对应长度的数据,若未指定长度在获取时会获取剩余的所有数据
     */
    int TYPE_RAW = 200;
}
