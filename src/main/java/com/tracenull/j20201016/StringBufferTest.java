package com.tracenull.j20201016;

/**
 * Java StringBuffer 和 StringBuilder 类
 * https://www.runoob.com/java/java-stringbuffer.html
 */
public class StringBufferTest {
    public static void main(String[] args) {
        StringBuffer sBuffer = new StringBuffer("菜鸟教程官网：");
        sBuffer.append("www");
        sBuffer.append(".runoob");
        sBuffer.append(".com");

        System.out.println(sBuffer);
    }
}