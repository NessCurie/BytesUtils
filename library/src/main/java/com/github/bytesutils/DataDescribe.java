package com.github.bytesutils;

/**
 * 对于 byte数组 的描述 表示这段byte数组是:什么类型,长度为多少,是大端还是小端
 * 构造时如果不传入是否大小端,默认是小端
 */
public class DataDescribe {

    /**
     * 数据类型 {@link DataType}
     */
    public int type;
    /**
     * 数据长度
     */
    public int length;
    /**
     * 是否为小端
     */
    public boolean isLe;

    /**
     * 大多数数据的长度初始化时其实都是固定的,没必要在构造时手动填入
     * 除了 {@link DataType#TYPE_RAW} 可能需要传入长度,其他都不需要
     */
    public DataDescribe(int type, boolean isLe) {
        this.type = type;
        this.isLe = isLe;
        switch (type) {
            case DataType.TYPE_U8:
            case DataType.TYPE_S8:
                length = 1;
                break;
            case DataType.TYPE_U16:
            case DataType.TYPE_S16:
                length = 2;
                break;
            case DataType.TYPE_U32:
            case DataType.TYPE_S32:
                length = 4;
                break;
            case DataType.TYPE_S64:
                length = 8;
                break;
            case DataType.TYPE_FLOAT:
                length = 4;
                break;
            case DataType.TYPE_DOUBLE:
                length = 8;
                break;
            case DataType.TYPE_MUABLE0:
            case DataType.TYPE_MUABLE1:
            case DataType.TYPE_MUABLE2:
            case DataType.TYPE_MUABLE1_BE:
            case DataType.TYPE_MUABLE2_BE:
            case DataType.TYPE_TREE:
            case DataType.TYPE_RAW:
                length = 0;
                break;
        }
    }

    /**
     * 默认小端
     */
    public DataDescribe(int type) {
        this(type, true);
    }

    /**
     * @param type 数据类型
     * @param len  数据长度  除了 {@link DataType#TYPE_RAW} 可能需要传入长度,其他实际上都不需要
     * @param le   是否是小端
     */
    public DataDescribe(int type, int len, boolean le) {
        this.type = type;
        length = len;
        isLe = le;
    }

    /**
     * 默认小端
     */
    public DataDescribe(int type, int len) {
        this(type, len, true);
    }
}
