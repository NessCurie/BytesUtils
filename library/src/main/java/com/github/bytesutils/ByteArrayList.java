package com.github.bytesutils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 用途: byte的ArrayList,提供了方便的可以放入各种类型数据的方法
 */
public class ByteArrayList {

    private int size = 0;
    private byte[] elementData;

    public ByteArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        elementData = new byte[initialCapacity];
    }

    public ByteArrayList() {
        this(10);
    }

    public ByteArrayList(byte[] initData) {
        if ((size = initData.length) != 0) {
            elementData = initData;
        }
    }

    public int size() {
        return size;
    }

    public ByteArrayList add(byte element) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = element;
        return this;
    }

    public ByteArrayList add(int index, byte element) {
        if (index < 0)
            throw new IllegalArgumentException("Index Error:" + index + ", Size: " + size);

        if (index > size) {
            ensureCapacityInternal(index + 1);
            elementData[index] = element;
            size = index + 1;
        } else {
            ensureCapacityInternal(size + 1);
            System.arraycopy(elementData, index, elementData, index + 1, size - index);
            elementData[index] = element;
            size++;
        }
        return this;
    }

    public ByteArrayList add(byte[] src) {
        int numNew = src.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(src, 0, elementData, size, numNew);
        size += numNew;
        return this;
    }

    public ByteArrayList add(int index, byte[] src) {
        if (index < 0)
            throw new IllegalArgumentException("Index Error:" + index + ", Size: " + size);

        int numNew = src.length;
        if (index > size) {
            ensureCapacityInternal(index + numNew);
            System.arraycopy(src, 0, elementData, index, numNew);
            size = index + src.length;
        } else {
            ensureCapacityInternal(size + numNew);
            System.arraycopy(elementData, index, elementData, index + numNew, size - index);
            System.arraycopy(src, 0, elementData, index, numNew);
            size += numNew;
        }
        return this;
    }

    public ByteArrayList add(short s, boolean isLe) {
        return add(isLe ? EndianUtils.toLe(s) : EndianUtils.toBe(s));
    }

    public ByteArrayList add(short s) {
        return add(s, true);
    }

    public ByteArrayList add(int i, boolean isLe) {
        return add(isLe ? EndianUtils.toLe(i) : EndianUtils.toBe(i));
    }

    public ByteArrayList add(int i) {
        return add(i, true);
    }

    public ByteArrayList add(long l, boolean isLe) {
        return add(isLe ? EndianUtils.toLe(l) : EndianUtils.toBe(l));
    }

    public ByteArrayList add(long l) {
        return add(l, true);
    }

    public ByteArrayList add(float f, boolean isLe) {
        return add(isLe ? EndianUtils.toLe(f) : EndianUtils.toBe(f));
    }

    public ByteArrayList add(float f) {
        return add(f, true);
    }

    public ByteArrayList add(double d, boolean isLe) {
        return add(isLe ? EndianUtils.toLe(d) : EndianUtils.toBe(d));
    }

    public ByteArrayList add(double d) {
        return add(d, true);
    }

    public ByteArrayList add(String s, Charset charSet) {
        if (s != null && s.length() != 0 && charSet != null) {
            return add(s.getBytes(charSet));
        }
        return this;
    }

    public ByteArrayList add(String s, String charSet) {
        if (s != null && s.length() != 0 && charSet != null && charSet.length() != 0) {
            try {
                return add(s.getBytes(charSet));
            } catch (UnsupportedEncodingException ignored) {
            }
        }
        return this;
    }

    public ByteArrayList add(String s) {
        return add(s, "GBK");
    }

    public byte get(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        return elementData[index];
    }

    public byte remove(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        byte oldValue = elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = 0;

        return oldValue;
    }

    public void clear() {
        for (int i = 0; i < size; i++) elementData[i] = 0;
        size = 0;
    }

    /**
     * 添加空数据
     */
    public void addSize(int addSize) {
        ensureCapacityInternal(size + addSize);
        size += addSize;
    }

    /**
     * 设置size的大小,若大于当前会扩充空数据 若小于当前会截取
     */
    public void setSize(int newSize) {
        if (newSize > size) {
            ensureCapacityInternal(newSize);
        } else {
            for (int i = newSize; i < size; i++) {
                elementData[i] = 0;
            }
        }
        size = newSize;
    }

    public byte[] toArray() {
        byte[] rst = new byte[size];
        System.arraycopy(elementData, 0, rst, 0, size);
        return rst;
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (minCapacity - elementData.length > 0) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    @Override
    public String toString() {
        return BytesReader.toString(toArray());
    }
}
