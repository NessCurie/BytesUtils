package com.github.bytesutils;

import java.io.UnsupportedEncodingException;

/**
 * 用来解析byte数组
 */
public class BytesReader {

    private static final String TABLE = "0123456789ABCDEF";
    private int position = 0;
    private byte[] data;

    public BytesReader(byte[] bytes) {
        this.data = bytes;
    }

    public boolean isLeftoverAllFF() {
        boolean allFF = true;
        if (data != null && position < data.length) {
            byte[] rst = new byte[data.length - position];
            System.arraycopy(data, position, rst, 0, data.length - position);
            for (byte datum : rst) {
                if (datum != -1) {
                    allFF = false;
                    break;
                }
            }
        }
        return allFF;
    }

    public boolean isNotLeftoverAllFF() {
        return !isLeftoverAllFF();
    }

    /**
     * 返回 byte数组中当前的索引位置到索引加上指定长度位置的byte数据, 之后索引会向后移动指定长度个位置
     *
     * @param length 获取的长度
     */
    public byte[] getBytes(int length) {
        byte[] rst = null;
        if (data != null && position + length <= data.length) {
            rst = new byte[length];
            System.arraycopy(data, position, rst, 0, length);
            position += length;
        }
        return rst;
    }

    /**
     * 从byte数组中当前的索引位置 将1个字节 转为 有符号8位,使用byte进行接收,索引会向后移动1个位置
     */
    public byte getS8() {
        byte rst = 0;
        if (data != null && position + 1 <= data.length) {
            rst = data[position];
            position += 1;
        }
        return rst;
    }

    /**
     * 从byte数组中当前的索引位置 将1个字节 转为 无符号8位,使用short进行接收,索引会向后移动1个位置
     */
    public short getU8() {
        short rst = 0;
        if (data != null && position + 1 <= data.length) {
            rst = data[position] < 0 ? (short) (256 + data[position]) : (short) (data[position]);
            position += 1;
        }
        return rst;
    }

    /**
     * 从byte数组中当前的索引位置 将2个字节 转为 有符号16位,使用short进行接收,索引会向后移动2个位置
     *
     * @param isLe 这段数据是否是小端
     */
    public short getS16(boolean isLe) {
        short rst = 0;
        if (data != null && position + 2 <= data.length) {
            byte[] tmp = new byte[2];
            System.arraycopy(data, position, tmp, 0, 2);
            position += 2;
            rst = isLe ? EndianUtils.le2Short(tmp) : EndianUtils.be2Short(tmp);
        }
        return rst;
    }

    /**
     * 默认小端 从byte数组中当前的索引位置 将2个字节 转为 有符号16位,使用short进行接收,索引会向后移动2个位置
     */
    public short getS16() {
        return getS16(true);
    }

    /**
     * 从byte数组中当前的索引位置 将2个字节 转为 无符号16位,使用int进行接收,索引会向后移动2个位置
     *
     * @param isLe 这段数据是否是小端
     */
    public int getU16(boolean isLe) {
        int rst = 0;
        if (data != null && position + 2 <= data.length) {
            byte[] tmp = new byte[2];
            System.arraycopy(data, position, tmp, 0, 2);
            position += 2;
            rst = isLe ? EndianUtils.le2U16(tmp) : EndianUtils.be2U16(tmp);
        }
        return rst;
    }

    /**
     * 默认小端 从byte数组中当前的索引位置 将2个字节 转为 无符号16位,使用int进行接收,索引会向后移动2个位置
     */
    public int getU16() {
        return getU16(true);
    }

    /**
     * 从byte数组中当前的索引位置 将4个字节 转为 有符号32位,使用int进行接收,索引会向后移动4个位置
     *
     * @param isLe 这段数据是否是小端
     */
    public int getS32(boolean isLe) {
        int rst = 0;
        if (data != null && position + 4 <= data.length) {
            byte[] tmp = new byte[4];
            System.arraycopy(data, position, tmp, 0, 4);
            position += 4;
            rst = isLe ? EndianUtils.le2Int(tmp) : EndianUtils.be2Int(tmp);
        }
        return rst;
    }

    /**
     * 默认小端 从byte数组中当前的索引位置 将4个字节 转为 有符号32位,使用int进行接收,索引会向后移动4个位置
     */
    public int getS32() {
        return getS32(true);
    }

    /**
     * 从byte数组中当前的索引位置 将4个字节 转为 无符号32位,使用long进行接收,索引会向后移动4个位置
     *
     * @param isLe 这段数据是否是小端
     */
    public long getU32(boolean isLe) {
        long rst = 0;
        if (data != null && position + 4 <= data.length) {
            byte[] tmp = new byte[4];
            System.arraycopy(data, position, tmp, 0, 4);
            position += 4;
            rst = isLe ? EndianUtils.le2U32(tmp) : EndianUtils.be2U32(tmp);
        }
        return rst;
    }

    /**
     * 默认小端 从byte数组中当前的索引位置 将4个字节 转为 无符号32位,使用long进行接收,索引会向后移动4个位置
     */
    public long getU32() {
        return getU32(true);
    }

    /**
     * 从byte数组中当前的索引位置 将8个字节 转为 有符号64位,使用long进行接收,索引会向后移动8个位置
     *
     * @param isLe 这段数据是否是小端
     */
    public long getS64(boolean isLe) {
        long rst = 0;
        if (data != null && position + 8 <= data.length) {
            byte[] tmp = new byte[8];
            System.arraycopy(data, position, tmp, 0, 8);
            position += 8;
            rst = isLe ? EndianUtils.le2Long(tmp) : EndianUtils.be2Long(tmp);
        }
        return rst;
    }

    /**
     * 默认为小端 从byte数组中当前的索引位置 将8个字节 转为 有符号64位,使用long进行接收,索引会向后移动8个位置
     */
    public long getS64() {
        return getS64(true);
    }

    /**
     * 从byte数组中当前的索引位置 将4个字节 转为 float,索引会向后移动4个位置
     *
     * @param isLe 这段数据是否是小端
     */
    public float getFloat(boolean isLe) {
        float rst = 0f;
        if (data != null && position + 4 <= data.length) {
            byte[] tmp = new byte[4];
            System.arraycopy(data, position, tmp, 0, 4);
            position += 4;
            rst = isLe ? EndianUtils.le2Float(tmp) : EndianUtils.be2Float(tmp);
        }
        return rst;
    }

    /**
     * 默认小端 从byte数组中当前的索引位置 将4个字节 转为 float,索引会向后移动4个位置
     */
    public float getFloat() {
        return getFloat(true);
    }

    /**
     * 从byte数组中当前的索引位置 将4个字节 转为 double,索引会向后移动8个位置
     *
     * @param isLe 这段数据是否是小端
     */
    public double getDouble(boolean isLe) {
        double rst = 0f;
        if (data != null && position + 4 <= data.length) {
            byte[] tmp = new byte[4];
            System.arraycopy(data, position, tmp, 0, 4);
            position += 4;
            rst = isLe ? EndianUtils.le2Double(tmp) : EndianUtils.be2Double(tmp);
        }
        return rst;
    }

    /**
     * 默认小端 从byte数组中当前的索引位置 将8个字节 转为 double,索引会向后移动8个位置
     */
    public double getDouble() {
        return getDouble(true);
    }

    /**
     * 从当前的索引位置到索引加上指定长度位置的byte数据转化为String字符串,之后索引移动指定的长度
     * 如果传入的长度过长,会将剩下的所有数据转化为字符串,之后索引移动到末尾
     */
    public String getString(int length, String charSet) {
        String rst = null;
        if (null != data) {
            if (position + length > data.length) {
                length = data.length - position;
            }
            byte[] tmp = new byte[length];
            System.arraycopy(data, position, tmp, 0, length);
            position += length;
            try {
                rst = new String(tmp, charSet);
            } catch (UnsupportedEncodingException ignored) {
            }
        }
        return rst;
    }

    /**
     * 默认使用GBK编码
     */
    public String getString(int length) {
        return getString(length, "GBK");
    }

    /**
     * 获取当前索引位置
     */
    public int getPos() {
        return position;
    }

    /**
     * 将索引移动到指定位置
     */
    public boolean setPos(int pos) {
        if (null != data && pos < data.length) {
            position = pos;
            return true;
        }
        return false;
    }

    /**
     * 将索引向后移动length个位置
     *
     * @param length 移动长度
     * @return 是否成功
     */
    public boolean movePos(int length) {
        if (null != data && position + length < data.length) {
            position += length;
            return true;
        }
        return false;
    }

    /**
     * 获取剩余的长度
     */
    public int residue() {
        int rst = 0;
        if (null != data) {
            rst = data.length - position;
        }
        return rst;
    }

    /**
     * 将从position开始的剩下的数据全部返回
     */
    public byte[] getResidue() {
        byte[] rst = null;
        if (data != null && position < data.length) {
            rst = new byte[data.length - position];
            System.arraycopy(data, position, rst, 0, data.length - position);
            position = data.length;
        }
        return rst;
    }

    /**
     * 查看表示长度的1字节的内容 在有符号时是多少,索引不会移动
     */
    public byte spyS8() {
        if (data != null && position < data.length) {
            return data[position];
        }
        return 0;
    }

    /**
     * 查看表示长度的1字节的内容 在无符号时是多少,索引不会移动
     * 针对{@link DataType#TYPE_MUABLE0}
     */
    public short spyU8() {
        if (data != null && position < data.length) {
            return data[position] < 0 ? (short) (256 + data[position]) : (short) (data[position]);
        }
        return 0;
    }

    /**
     * 查看表示长度的2字节的内容 在有符号时是多少,索引不会移动
     *
     * @param isLe 这段数据是大端还是小端
     */
    public short spyS16(boolean isLe) {
        if (data != null && position + 1 < data.length) {
            byte[] tmp = new byte[2];
            System.arraycopy(data, position, tmp, 0, 2);
            return isLe ? EndianUtils.le2Short(tmp) : EndianUtils.be2Short(tmp);
        }
        return 0;
    }

    /**
     * 查看表示长度的2字节的内容 在无符号时是多少,索引不会移动
     * 针对{@link DataType#TYPE_MUABLE1} 和 {@link DataType#TYPE_MUABLE1_BE}
     *
     * @param isLe 这段数据是大端还是小端
     */
    public int spyU16(boolean isLe) {
        if (data != null && position + 1 < data.length) {
            byte[] tmp = new byte[2];
            System.arraycopy(data, position, tmp, 0, 2);
            return isLe ? EndianUtils.le2U16(tmp) : EndianUtils.be2U16(tmp);
        }
        return 0;
    }

    /**
     * 查看表示长度的4字节的内容 在有符号时是多少,索引不会移动
     *
     * @param isLe 这段数据是大端还是小端
     */
    public int spyS32(boolean isLe) {
        if (data != null && position + 3 < data.length) {
            byte[] tmp = new byte[4];
            System.arraycopy(data, position, tmp, 0, 4);
            return isLe ? EndianUtils.le2Int(tmp) : EndianUtils.be2Int(tmp);
        }
        return 0;
    }

    /**
     * 查看表示长度的4字节的内容 在无符号时是多少,索引不会移动
     * 针对{@link DataType#TYPE_MUABLE2} 和 {@link DataType#TYPE_MUABLE2_BE}
     *
     * @param isLe 这段数据是大端还是小端
     */
    public long spyU32(boolean isLe) {
        if (data != null && position + 3 < data.length) {
            byte[] tmp = new byte[4];
            System.arraycopy(data, position, tmp, 0, 4);
            return isLe ? EndianUtils.le2U32(tmp) : EndianUtils.be2U32(tmp);
        }
        return 0;
    }

    /**
     * 查看表示长度的8字节的内容 在有符号时是多少,索引不会移动
     *
     * @param isLe 这段数据是大端还是小端
     */
    public long spyS64(boolean isLe) {
        if (data != null && position + 7 < data.length) {
            byte[] tmp = new byte[8];
            System.arraycopy(data, position, tmp, 0, 8);
            return isLe ? EndianUtils.le2Long(tmp) : EndianUtils.be2Long(tmp);
        }
        return 0;
    }

    @Override
    public String toString() {
        return toString(data);
    }

    /**
     * 提供的静态的可以将byte数组打印为16进制的字符串的工具方法
     */
    public static String toString(byte[] src) {
        if (src != null) {
            StringBuilder rst = new StringBuilder();
            for (byte aSrc : src) {
                short val = aSrc < 0 ? (short) (256 + aSrc) : aSrc;
                rst.append(TABLE.substring(((val >> 4) & 0x0f), (((val >> 4) & 0x0f) + 1)))
                        .append(TABLE.substring((val & 0x0f), (val & 0x0f) + 1))
                        .append(" ");
            }
            return rst.toString();
        } else {
            return "null";
        }
    }
}
