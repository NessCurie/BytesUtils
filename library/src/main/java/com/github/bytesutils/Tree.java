package com.github.bytesutils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 可以有无限分支的树,提供了方便的向树中设置各种数据 或者 从树中获取各种数据 的方法
 * <p>
 * 使用方式:
 * 1.根据描述的索引调用对应的set方法,设置完数据后,使用 {@link Tree#toByteArray()} 可以获取总的byte数组
 * <p>
 * 2.使用 {@link Tree#parserByteArray(byte[])}或{@link Tree#parserByteArray(BytesReader)}
 * 方法,将byte数组根据描述解析为树结构,再根据描述的索引调用对应的get方法,可以从byte数组中获取对应数据
 * <p>
 * 节点的描述中已经表示了该节点的数据类型、部分数据类型和大小端
 * 所以当设置指定的位置的数据与描述中的位置不匹配时不会进行设置,对应的位置会为null
 * 所以当获取指定的位置的数据与描述中的位置不匹配时会默认返回0
 * 也不再需要关心大小端
 */
public class Tree {
    /**
     * 节点 根据des中的顺序存储着对应的数据
     */
    private HashMap<Integer, Tree> node = new HashMap<>();
    /**
     * 节点的描述 表示节点{@link Tree#node}下存储的是哪些些数据
     */
    private DataDescribe[] des;
    /**
     * 代表当前节点下的所有内容的数据
     */
    private byte[] data;

    /**
     * 根据节点的描述初始化本类的描述
     */
    public Tree(DataDescribe[] des) {
        this.des = des;
    }

    /**
     * 会将传入的多个子Tree依次放入 des描述的指定位置
     */
    public Tree(DataDescribe[] des, Tree... child) {
        this.des = des;
        int index = -1;
        for (Tree tree : child) {
            for (int i = index + 1; i < des.length; i++) {
                if (des[i].type == DataType.TYPE_TREE) {
                    index = i;
                    node.put(index, tree);
                    break;
                }
            }
        }
    }

    private Tree(byte b) {
        data = new byte[]{b};
    }

    private Tree(byte[] src) {
        data = src;
    }

    private Tree(short val, boolean isU8, boolean le) {
        if (isU8) {
            data = new byte[]{(byte) (val & 0xff)};
        } else {
            data = le ? EndianUtils.toLe(val) : EndianUtils.toBe(val);
        }
    }

    private Tree(int val, boolean isU16, boolean le) {
        if (isU16) {
            data = le ? EndianUtils.u16ToLe(val) : EndianUtils.u16ToBe(val);
        } else {
            data = le ? EndianUtils.toLe(val) : EndianUtils.toBe(val);
        }
    }

    private Tree(long val, boolean isU32, boolean le) {
        if (isU32) {
            data = le ? EndianUtils.u32ToLe(val) : EndianUtils.u32ToBe(val);
        } else {
            data = le ? EndianUtils.toLe(val) : EndianUtils.toBe(val);
        }
    }

    private Tree(float val, boolean le) {
        data = le ? EndianUtils.toLe(val) : EndianUtils.toBe(val);
    }

    private Tree(double val, boolean le) {
        data = le ? EndianUtils.toLe(val) : EndianUtils.toBe(val);
    }

    private Tree(int type, String val, String charSet) {
        try {
            byte[] src = val.getBytes(charSet);
            ByteArrayList list = new ByteArrayList();
            switch (type) {
                case DataType.TYPE_MUABLE0:
                    if (src.length <= 255) {
                        list.add((byte) src.length);
                        list.add(src);
                    }
                    break;
                case DataType.TYPE_MUABLE1:
                    if (src.length <= 65535) {
                        list.add((short) src.length, true);
                        list.add(src);
                    }
                    break;
                case DataType.TYPE_MUABLE2:
                    list.add(src.length, true);
                    list.add(src);
                    break;
                case DataType.TYPE_MUABLE1_BE:
                    if (src.length <= 65535) {
                        list.add((short) src.length, false);
                        list.add(src);
                    }
                    break;
                case DataType.TYPE_MUABLE2_BE:
                    list.add(src.length, false);
                    list.add(src);
                    break;
            }
            data = list.toArray();
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    /**
     * 设置有符号8位到本节点的指定位置
     */
    public Tree setS8(int index, byte val) {
        if (index < des.length && des[index].type == DataType.TYPE_S8) {
            node.put(index, new Tree(val));
        }
        return this;
    }

    /**
     * 设置无符号8位到本节点的指定位置
     */
    public Tree setU8(int index, short val) {
        if (index < des.length && des[index].type == DataType.TYPE_U8) {
            node.put(index, new Tree(val, true, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置有符号16位到本节点的指定位置
     */
    public Tree setS16(int index, short val) {
        if (index < des.length && des[index].type == DataType.TYPE_S16) {
            node.put(index, new Tree(val, false, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置无符号16位到本节点的指定位置
     */
    public Tree setU16(int index, int val) {
        if (index < des.length && des[index].type == DataType.TYPE_U16) {
            node.put(index, new Tree(val, true, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置有符号32位到本节点的指定位置
     */
    public Tree setS32(int index, int val) {
        if (index < des.length && des[index].type == DataType.TYPE_S32) {
            node.put(index, new Tree(val, false, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置无符号32位到本节点的指定位置
     */
    public Tree setU32(int index, long val) {
        if (index < des.length && des[index].type == DataType.TYPE_U32) {
            node.put(index, new Tree(val, true, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置有符号64位到本节点的指定位置
     */
    public Tree setS64(int index, long val) {
        if (index < des.length && des[index].type == DataType.TYPE_S64) {
            node.put(index, new Tree(val, false, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置float类型的数据到本节点的指定位置
     */
    public Tree setFloat(int index, float val) {
        if (index < des.length && des[index].type == DataType.TYPE_FLOAT) {
            node.put(index, new Tree(val, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置double类型的数据到本节点的指定位置
     */
    public Tree setDouble(int index, double val) {
        if (index < des.length && des[index].type == DataType.TYPE_DOUBLE) {
            node.put(index, new Tree(val, des[index].isLe));
        }
        return this;
    }

    /**
     * 设置byte数组到本节点的指定位置
     */
    public Tree setBytes(int index, byte[] val) {
        if (index < des.length && des[index].type == DataType.TYPE_RAW) {
            node.put(index, new Tree(val));
        }
        return this;
    }

    /**
     * 设置字符串数据到本节点的指定位置
     */
    public Tree setString(int index, String val, String charSet) {
        if (index < des.length && (des[index].type == DataType.TYPE_MUABLE0 ||
                des[index].type == DataType.TYPE_MUABLE1 ||
                des[index].type == DataType.TYPE_MUABLE2 ||
                des[index].type == DataType.TYPE_MUABLE1_BE ||
                des[index].type == DataType.TYPE_MUABLE2_BE)) {
            node.put(index, new Tree(des[index].type, val, charSet));
        }
        return this;
    }

    /**
     * 设置字符串数据到本节点的指定位置
     */
    public Tree setString(int index, String val) {
        return setString(index, val, "GBK");
    }

    /**
     * 设置子节点到本节点的指定位置
     */
    public Tree setChildTree(int index, Tree val) {
        if (index < des.length && des[index].type == DataType.TYPE_TREE) {
            node.put(index, val);
        }
        return this;
    }

    public Tree setChildTree(int index, DataDescribe[] des) {
        return setChildTree(index, new Tree(des));
    }

    /**
     * 从本节点中指定位置获取无符号8位
     */
    public short getU8(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_U8) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return field.data[0] < 0 ? (short) (256 + field.data[0]) : (short) (field.data[0]);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取有符号8位
     */
    public byte getS8(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_S8) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return field.data[0];
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取无符号16位
     */
    public int getU16(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_U16) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return des[index].isLe ? EndianUtils.le2U16(field.data) : EndianUtils.be2U16(field.data);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取有符号16位
     */
    public short getS16(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_S16) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return des[index].isLe ? EndianUtils.le2Short(field.data) : EndianUtils.be2Short(field.data);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取无符号32位
     */
    public long getU32(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_U32) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return des[index].isLe ? EndianUtils.le2U32(field.data) : EndianUtils.be2U32(field.data);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取有符号32位
     */
    public int getS32(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_S32) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return des[index].isLe ? EndianUtils.le2Int(field.data) : EndianUtils.be2Int(field.data);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取有符号64位
     */
    public long getS64(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_S64) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return des[index].isLe ? EndianUtils.le2Long(field.data) : EndianUtils.be2Long(field.data);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取float类型数据
     */
    public float getFloat(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_FLOAT) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return des[index].isLe ? EndianUtils.le2Float(field.data) : EndianUtils.be2Float(field.data);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取double类型数据
     */
    public double getDouble(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_DOUBLE) {
            Tree field = node.get(index);
            if (field != null && field.data != null) {
                return des[index].isLe ? EndianUtils.le2Double(field.data) : EndianUtils.be2Double(field.data);
            }
        }
        return 0;
    }

    /**
     * 从本节点中指定位置获取byte数组
     */
    public byte[] getBytes(int index) {
        if (index < des.length && des[index].type == DataType.TYPE_RAW) {
            Tree field = node.get(index);
            if (field != null) {
                return field.data;
            }
        }
        return null;
    }

    /**
     * 从本节点中指定位置使用指定字符集获取string数据
     */
    public String getString(int index, String charSet) {
        if (charSet != null && charSet.length() != 0) {
            if (index < des.length && (des[index].type == DataType.TYPE_MUABLE0 ||
                    des[index].type == DataType.TYPE_MUABLE1 ||
                    des[index].type == DataType.TYPE_MUABLE2 ||
                    des[index].type == DataType.TYPE_MUABLE1_BE ||
                    des[index].type == DataType.TYPE_MUABLE2_BE)) {
                Tree field = node.get(index);
                if (field != null && field.data != null) {
                    BytesReader reader = new BytesReader(field.data);
                    switch (des[index].type) {
                        case DataType.TYPE_MUABLE0:
                            reader.getU8();
                            break;
                        case DataType.TYPE_MUABLE1:
                            reader.getU16(true);
                            break;
                        case DataType.TYPE_MUABLE2:
                            reader.getU32(true);
                            break;
                        case DataType.TYPE_MUABLE1_BE:
                            reader.getU16(false);
                            break;
                        case DataType.TYPE_MUABLE2_BE:
                            reader.getU32(false);
                            break;
                    }
                    try {
                        byte[] data = reader.getResidue();
                        if (data != null) {
                            return new String(data, charSet);
                        }
                    } catch (UnsupportedEncodingException ignored) {
                    }
                }
            }
        }
        return null;
    }

    /**
     * 从本节点中指定位置使用默认的GBK字符集获取string数据
     */
    public String getString(int index) {
        return getString(index, "GBK");
    }

    /**
     * 从本节点中指定位置获取子节点
     */
    public Tree getChildTree(int index) {
        return node.get(index);
    }

    /**
     * 将包含的所有内容转化为byte数组
     */
    public byte[] toByteArray() {
        ByteArrayList list = new ByteArrayList();
        for (int index = 0; index < des.length; index++) {
            Tree field = node.get(index);
            DataDescribe dataDescribe = des[index];
            switch (dataDescribe.type) {
                case DataType.TYPE_U8:
                case DataType.TYPE_S8:
                case DataType.TYPE_U16:
                case DataType.TYPE_S16:
                case DataType.TYPE_U32:
                case DataType.TYPE_S32:
                case DataType.TYPE_S64:
                case DataType.TYPE_FLOAT:
                case DataType.TYPE_DOUBLE:
                    if (field == null || field.data == null) {
                        list.addSize(dataDescribe.length);
                    } else {
                        list.add(field.data);
                    }
                    break;
                case DataType.TYPE_MUABLE0:
                    if (field == null || field.data == null) {
                        list.add((byte) 0);
                    } else {
                        list.add(field.data);
                    }
                    break;
                case DataType.TYPE_MUABLE1:
                    if (field == null || field.data == null) {
                        list.add((short) 0, true);
                    } else {
                        list.add(field.data);
                    }
                    break;
                case DataType.TYPE_MUABLE2:
                    if (field == null || field.data == null) {
                        list.add(0, true);
                    } else {
                        list.add(field.data);
                    }
                    break;
                case DataType.TYPE_MUABLE1_BE:
                    if (field == null || field.data == null) {
                        list.add((short) 0, false);
                    } else {
                        list.add(field.data);
                    }
                    break;
                case DataType.TYPE_MUABLE2_BE:
                    if (field == null || field.data == null) {
                        list.add(0, false);
                    } else {
                        list.add(field.data);
                    }
                    break;
                case DataType.TYPE_TREE:
                    if (field != null) {
                        list.add(field.toByteArray());
                    }
                    break;
                case DataType.TYPE_RAW:
                    if (field == null || field.data == null) {
                        list.addSize(dataDescribe.length);
                    } else {
                        if (dataDescribe.length != 0 && field.data.length != dataDescribe.length) {
                            list.add(Arrays.copyOf(field.data, dataDescribe.length));
                        } else {
                            list.add(field.data);
                        }
                    }
                    break;
                default:
                    throw new IllegalMonitorStateException("don't have the type " + dataDescribe.type);
            }
        }
        return list.toArray();
    }

    /**
     * 将byte数组 解析为 根据结构描述{@link Tree#des}的 树
     */
    public Tree parserByteArray(byte[] src) {
        if (src != null) {
            return parserByteArray(new BytesReader(src));
        }
        return this;
    }

    /**
     * 将BytesReader对象 解析为 根据结构描述{@link Tree#des}的 树
     * <p>
     */
    public Tree parserByteArray(BytesReader reader) {
        if (reader != null) {
            for (int index = 0; index < des.length; index++) {
                DataDescribe dataDescribe = des[index];
                switch (dataDescribe.type) {
                    case DataType.TYPE_U8:
                    case DataType.TYPE_S8:
                    case DataType.TYPE_U16:
                    case DataType.TYPE_S16:
                    case DataType.TYPE_U32:
                    case DataType.TYPE_S32:
                    case DataType.TYPE_S64:
                    case DataType.TYPE_FLOAT:
                    case DataType.TYPE_DOUBLE:
                        node.put(index, new Tree(reader.getBytes(dataDescribe.length)));
                        break;
                    case DataType.TYPE_MUABLE0:
                        node.put(index, new Tree(reader.getBytes(1 + reader.spyU8())));
                        break;
                    case DataType.TYPE_MUABLE1:
                        node.put(index, new Tree(reader.getBytes(2 + reader.spyU16(true))));
                        break;
                    case DataType.TYPE_MUABLE2:
                        node.put(index, new Tree(reader.getBytes(4 + (int) reader.spyU32(true))));
                        break;
                    case DataType.TYPE_MUABLE1_BE:
                        node.put(index, new Tree(reader.getBytes(2 + reader.spyU16(false))));
                        break;
                    case DataType.TYPE_MUABLE2_BE:
                        node.put(index, new Tree(reader.getBytes(4 + (int) reader.spyU32(false))));
                        break;
                    case DataType.TYPE_TREE:
                        Tree field = node.get(index);
                        if (field != null) field.parserByteArray(reader);
                        break;
                    case DataType.TYPE_RAW:
                        node.put(index, new Tree(dataDescribe.length > 0 ?
                                reader.getBytes(dataDescribe.length) : reader.getResidue()));
                        break;
                    default:
                        throw new IllegalMonitorStateException("don't have the type " + dataDescribe.type);
                }
            }
        }
        return this;
    }
}
