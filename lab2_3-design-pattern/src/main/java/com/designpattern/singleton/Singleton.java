package com.designpattern.singleton;

public final class Singleton {
    private static Singleton instance; // Biến tĩnh để lưu instance duy nhất
    public String value; // Moi truong du lieu cong khai

    private Singleton(String value) {
        try{
            Thread.sleep(1000); // Gia lap thoi gian khoi tao lau
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.value = value; // Gan gia tri dau vao cho truong value
    }

    public static Singleton getInstance(String value) { // Phuong thuc truy cap instance
        if (instance == null) { // Kiem tra neu chua co instance
            instance = new Singleton(value); // Tao moi instance
        }
        return instance; // Tra ve instance
    }
}
