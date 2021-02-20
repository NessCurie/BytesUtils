package com.github.bytesutils;

/**
 * 大小端的工具类
 * 提供将各种数据转化为大端或者小端的 byte数组 或者 将byte数组转化为各种数据
 * <p>
 * 大端 Big-Endian : 数据的高字节保存在内存的低地址中 数据的低字节保存在内存的高地址中.(低地址存放高位)
 * 比如 0x12345678 0x12为高字节,如果内存地址为0x4000开始 则 0x12存放在0x4000 0x4001地址存放0x34 ...
 * 作为byte数组时 高字节在前,低字节在后, 即:0x12 放在 byte数组的0位,0x34 放在 1位 ...
 * <p>
 * 小端 Little-Endian: 数据的高字节保存在内存的高地址中，而数据的低字节保存在内存的低地址中.(低地址存放低位)
 * 比如 0x12345678 0x78为低字节,如果内存地址为0x4000开始 则 0x78存放在0x4000 0x4001地址存放0x56 ...
 * 作为byte数组时 低字节在前,高字节在后, 即:0x78 存放在byte数组的0位 0x56 放在 1 位 ...
 * <p>
 * 大小端是针对需要占用多个连续字节的数据,比如short int long
 * 而比如字符串是字符数组，字符是占1个字节，不需要进行大小端转换.
 * <p>
 * windows,linux,unix的字节序为小端 java则无论平台变化,都是大端
 * 实际使用中的大小端需要根据与终端通信的协议进行区分.比如:金龙和陕汽等大部分都为小端,北奔的为大端
 */
public final class EndianUtils {

    /**
     * short转化为小端byte数组
     */
    public static byte[] toLe(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * short转为大端byte数组
     */
    public static byte[] toBe(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * int转化为小端byte数组
     */
    public static byte[] toLe(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (i & 0xff);
        b[1] = (byte) (i >> 8 & 0xff);
        b[2] = (byte) (i >> 16 & 0xff);
        b[3] = (byte) (i >> 24 & 0xff);
        return b;
    }

    /**
     * int转化为大端byte数组
     */
    public static byte[] toBe(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * long转化为小端byte数组
     */
    public static byte[] toLe(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        b[4] = (byte) (n >> 32 & 0xff);
        b[5] = (byte) (n >> 40 & 0xff);
        b[6] = (byte) (n >> 48 & 0xff);
        b[7] = (byte) (n >> 56 & 0xff);
        return b;
    }

    /**
     * long转化为大端byte数组
     */
    public static byte[] toBe(long n) {
        byte[] b = new byte[8];
        b[7] = (byte) (n & 0xff);
        b[6] = (byte) (n >> 8 & 0xff);
        b[5] = (byte) (n >> 16 & 0xff);
        b[4] = (byte) (n >> 24 & 0xff);
        b[3] = (byte) (n >> 32 & 0xff);
        b[2] = (byte) (n >> 40 & 0xff);
        b[1] = (byte) (n >> 48 & 0xff);
        b[0] = (byte) (n >> 56 & 0xff);
        return b;
    }

    /**
     * float转化为小端byte数组
     */
    public static byte[] toLe(float f) {
        return toLe(Float.floatToRawIntBits(f));
    }

    /**
     * float转化为大端byte数组
     */
    public static byte[] toBe(float f) {
        return toBe(Float.floatToRawIntBits(f));
    }

    /**
     * double转化为小端byte数组
     */
    public static byte[] toLe(double d) {
        return toLe(Double.doubleToRawLongBits(d));
    }

    /**
     * double转化为大端byte数组
     */
    public static byte[] toBe(double d) {
        return toBe(Double.doubleToRawLongBits(d));
    }

    /**
     * 大端byte数组转化为short
     */
    public static short be2Short(byte[] b) {
        int s = 0;
        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }
        s = s << 8;
        if (b[1] >= 0) {
            s = s + b[1];
        } else {
            s = s + 256 + b[1];
        }
        return (short) s;
    }

    /**
     * 小端byte数组转化为short
     */
    public static short le2Short(byte[] b) {
        int s = 0;
        if (b[1] >= 0) {
            s = s + b[1];
        } else {
            s = s + 256 + b[1];
        }
        s = s << 8;
        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }
        return (short) s;
    }

    /**
     * 大端byte数组转化为int
     */
    public static int be2Int(byte[] b) {
        int s = 0;
        if (4 != b.length) return s;
        for (int i = 0; i < 3; i++) {
            if (b[i] >= 0) {
                s = s + b[i];
            } else {
                s = s + 256 + b[i];
            }
            s = s << 8;
        }
        if (b[3] >= 0) {
            s = s + b[3];
        } else {
            s = s + 256 + b[3];
        }
        return s;
    }

    /**
     * 小端byte数组转化为int
     */
    public static int le2Int(byte[] b) {
        int s = 0;
        if (4 != b.length) return s;
        for (int i = 0; i < 3; i++) {
            if (b[3 - i] >= 0) {
                s = s + b[3 - i];
            } else {
                s = s + 256 + b[3 - i];
            }
            s = s << 8;
        }
        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }
        return s;
    }

    /**
     * 大端byte数组转化为long
     */
    public static long be2Long(byte[] b) {
        long s = 0;
        if (8 != b.length) return s;
        for (int i = 0; i < 7; i++) {
            if (b[i] >= 0) {
                s = s + b[i];
            } else {
                s = s + 256 + b[i];
            }
            s = s << 8;
        }
        if (b[3] >= 0) {
            s = s + b[3];
        } else {
            s = s + 256 + b[3];
        }
        return s;
    }

    /**
     * 小端byte数组转化为long
     */
    public static long le2Long(byte[] b) {
        long s = 0;
        if (8 != b.length) return s;
        for (int i = 0; i < 7; i++) {
            if (b[7 - i] >= 0) {
                s = s + b[7 - i];
            } else {
                s = s + 256 + b[7 - i];
            }
            s = s << 8;
        }
        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }
        return s;
    }

    /**
     * 大端byte数组转化为float
     */
    public static float be2Float(byte[] b) {
        int i = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8 | (b[3] & 0xff);
        return Float.intBitsToFloat(i);
    }

    /**
     * 小端byte数组转化为float
     */
    public static float le2Float(byte[] b) {
        int i = ((((b[3] & 0xff) << 8 | (b[2] & 0xff)) << 8) | (b[1] & 0xff)) << 8 | (b[0] & 0xff);
        return Float.intBitsToFloat(i);
    }

    /**
     * 大端byte数组转化为double
     */
    public static double be2Double(byte[] b) {
        long h = (b[0] & 0xff) << 8;
        h = (h | (b[1] & 0xff)) << 8;
        h = (h | (b[2] & 0xff)) << 8;
        h = (h | (b[3] & 0xff)) << 8;
        h = (h | (b[4] & 0xff)) << 8;
        h = (h | (b[5] & 0xff)) << 8;
        h = (h | (b[6] & 0xff)) << 8;
        h = (h | (b[7] & 0xff));
        return Double.longBitsToDouble(h);
    }

    /**
     * 小端byte数组转化为double
     */
    public static double le2Double(byte[] b) {
        long l = (b[7] & 0xff) << 8;
        l = (l | (b[6] & 0xff)) << 8;
        l = (l | (b[5] & 0xff)) << 8;
        l = (l | (b[4] & 0xff)) << 8;
        l = (l | (b[3] & 0xff)) << 8;
        l = (l | (b[2] & 0xff)) << 8;
        l = (l | (b[1] & 0xff)) << 8;
        l = (l | (b[0] & 0xff));
        return Double.longBitsToDouble(l);
    }

    /**
     * 无符号16位转化为小端byte数组
     */
    public static byte[] u16ToLe(int n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 无符号16位 转化为大端byte数组
     */
    public static byte[] u16ToBe(int n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 无符号32位 转化为小端byte数组
     */
    public static byte[] u32ToLe(long n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 无符号32位 转化为大端byte数组
     */
    public static byte[] u32ToBe(long n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 大端byte数组转化为无符号16位(java使用int进行接收)
     */
    public static int be2U16(byte[] b) {
        short s16 = be2Short(b);
        return s16 < 0 ? 65536 + s16 : s16;
    }

    /**
     * 小端byte数组转化为无符号16位(java使用int进行接收)
     */
    public static int le2U16(byte[] b) {
        short s16 = le2Short(b);
        return s16 < 0 ? 65536 + s16 : s16;
    }

    /**
     * 大端byte数组转化为无符号32位(java使用long进行接收)
     */
    public static long be2U32(byte[] b) {
        int s32 = be2Int(b);
        return s32 < 0 ? 4294967296L + s32 : s32;
    }

    /**
     * 小端byte数组转化为无符号32位(java使用long进行接收)
     */
    public static long le2U32(byte[] b) {
        int s32 = le2Int(b);
        return s32 < 0 ? 4294967296L + s32 : s32;
    }

    /**
     * 将byte数组中的元素倒序排列
     */
    public static byte[] reverseBytes(byte[] b) {
        int length = b.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[length - i - 1] = b[i];
        }
        return result;
    }

    /**
     * 将int类型的值转换为字节序颠倒过来对应的int值
     * 因为java默认是大端,转化为小端即颠倒了,再将其当作java的大端byte数组转化为int数即为结果
     */
    public static int reverseInt(int i) {
        return be2Int(toLe(i));
    }

    /**
     * 将short类型的值转换为字节序颠倒过来对应的short值
     */
    public static short reverseShort(short s) {
        return be2Short(toLe(s));
    }

    /**
     * 将 float类型的值转换为字节序颠倒过来对应的float值
     */
    public static float reverseFloat(float f) {
        return be2Float(toLe(f));
    }
}
