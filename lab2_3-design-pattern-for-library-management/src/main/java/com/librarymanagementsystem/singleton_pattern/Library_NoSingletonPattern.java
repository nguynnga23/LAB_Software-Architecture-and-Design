package com.librarymanagementsystem.singleton_pattern;

import java.util.ArrayList;
import java.util.List;

// Lớp Book đơn giản để minh họa
class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// Lớp Library không dùng Singleton
public class Library_NoSingletonPattern {
    // Danh sách sách trong thư viện
    private List<Book> books;

    // Hàm tạo public, cho phép tạo nhiều instance
    public Library_NoSingletonPattern() {
        books = new ArrayList<>();
        System.out.println("New Library instance created");
    }

    // Các phương thức quản lý sách
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Added book: " + book.getTitle());
    }

    public List<Book> getBooks() {
        return books;
    }

    public int getBookCount() {
        return books.size();
    }
}

// Class Main để kiểm tra
class Main {
    public static void main(String[] args) {
        // Tạo instance đầu tiên của Library
        Library_NoSingletonPattern library1 = new Library_NoSingletonPattern();
        library1.addBook(new Book("Harry Potter"));

        // Tạo instance thứ hai của Library
        Library_NoSingletonPattern library2 = new Library_NoSingletonPattern();
        library2.addBook(new Book("The Hobbit"));

        // Kiểm tra số lượng sách trong mỗi library
        System.out.println("Total books in library1: " + library1.getBookCount()); // 1
        System.out.println("Total books in library2: " + library2.getBookCount()); // 1

        // Kiểm tra xem library1 và library2 có phải cùng một đối tượng không
        System.out.println("library1 == library2: " + (library1 == library2)); // false
    }
}