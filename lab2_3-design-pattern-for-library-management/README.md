# Thiết Kế Hệ Thống Quản Lý Thư Viện Sử Dụng Design Patterns

Hệ thống quản lý thư viện được thiết kế để hỗ trợ các chức năng cơ bản như mượn sách, trả sách, thêm sách mới, xem danh sách sách, và tìm kiếm sách. Để đảm bảo tính mở rộng và dễ bảo trì, hệ thống sử dụng các Design Pattern phù hợp. Dưới đây là giải thích chi tiết cho từng yêu cầu.

## 1. Singleton Pattern - Quản lý thư viện duy nhất

### Bước 1: Bắt đầu đơn giản - Học Singleton Pattern
- **Mục tiêu**: Hiểu Singleton là pattern đơn giản nhất trong nhóm `Creational`, dùng để đảm bảo chỉ có một `Instance` duy nhất của một lớp.
- **Ngữ cảnh**: Trong hệ thống thư viện, ta cần một `Library` duy nhất để quản lý sách

### Bước 2: Đọc lý thuyết - Hiểu mục đích và cấu trúc
- Intent (Mục đích): Đảm bảo một lớp chỉ có một `Instance` duy nhất và cung cấp một `Global access` tới `Instance` đó
- Problem (Vấn đề): Nếu có nhiều `Library`, mỗi đối tượng sẽ có danh sách sách riêng, dẫn đến dữ liệu không nhất quán trong hệ thống
- Solution (Giải pháp):
    - Tạo một lớp `Library` với một thuộc tính tĩnh `instance` để lưu trữ đối tượng duy nhất.
    - Constructor của lớp được đặt ở chế độ private để ngăn việc tạo mới đối tượng từ bên ngoài.
    - Cung cấp một phương thức tĩnh `getInstance()` để truy cập hoặc tạo đối tượng `Library` duy nhất nếu chưa tồn tại.
    - Lớp `Library` sẽ chứa danh sách sách và các phương thức như thêm sách, mượn sách, trả sách.
### Bước 3: Vẽ sơ đồ - Dùng UML để hình dung
```
+------------------------------+
|     Library                  |
+------------------------------+
| - instance: Library (static) |
| - books: List<Book>          |
+------------------------------+
| - Library()                  | (private constructor)
| + getInstance(): Library     | (static method)
| + addBook(book: Book)        |
| + getBooks(): List<Book>     |
+------------------------------+
```
#### Giải thích sơ đồ:
- `instance`: Biến tĩnh lưu thể hiện duy nhất.
- `- Library()`: Hàm tạo private, không cho phép tạo mới bên ngoài.
- `+ getInstance()` : Phương thức tĩnh để lấy thể hiện.
- Các phương thức công khi như `addBook()`, `getBooks()` để quản lý sách.

### Bước 4: Viết code - Áp dụng vào ví dụ nhỏ
#### Code không dùng Singleton (ban đầu):
```java
import java.util.ArrayList;
import java.util.List;

class Book {
    private String title;
    public Book(String title) { this.title = title; }
    public String getTitle() { return title; }
}

class Library {
    private List<Book> books;
    public Library() {
        books = new ArrayList<>();
    }
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Added book: " + book.getTitle());
    }
    public List<Book> getBooks() { return books; }
}

class Main {
    public static void main(String[] args) {
        Library lib1 = new Library();
        lib1.addBook(new Book("Harry Potter"));

        Library lib2 = new Library();
        lib2.addBook(new Book("The Hobbit"));

        System.out.println("Books in lib1: " + lib1.getBooks().size()); // 1
        System.out.println("Books in lib2: " + lib2.getBooks().size()); // 1
    }
}
```
**Kết quả** : 
```text
Added book: Harry Potter
Added book: The Hobbit
Books in lib1: 1
Books in lib2: 1
```

**Vấn đề** : lib1 và lib2 là hai thư viện riêng, không chia sẻ danh sách sách – không đáp ứng yêu cầu chỉ có một thư viện duy nhất.

#### Code dùng Singleton :
```java
import java.util.ArrayList;
import java.util.List;

class Book {
    private String title;
    public Book(String title) { this.title = title; }
    public String getTitle() { return title; }
}

class Library {
    private static Library instance;
    private List<Book> books;

    private Library() {
        books = new ArrayList<>();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Added book: " + book.getTitle());
    }

    public List<Book> getBooks() {
        return books;
    }
}

class Main {
    public static void main(String[] args) {
        Library lib1 = Library.getInstance();
        lib1.addBook(new Book("Harry Potter"));

        Library lib2 = Library.getInstance();
        lib2.addBook(new Book("The Hobbit"));

        System.out.println("Books in lib1: " + lib1.getBooks().size()); // 2
        System.out.println("Books in lib2: " + lib2.getBooks().size()); // 2
    }
}
```
**Kết quả** : 
```
Added book: Harry Potter
Added book: The Hobbit
Books in lib1: 2
Books in lib2: 2
```
**Nhận xét** : lib1 và lib2 cùng trỏ đến một Library, danh sách sách được chia sẻ.

---

## 2. Factory Method Pattern - Tạo các loại sách khác nhau
### Bước 1: Bắt đầu đơn giản - Học Factory Method Pattern
- Mục tiêu: Hiểu Factory Method là một pattern thuộc nhóm Creational, dùng để tạo đối tượng mà không cần chỉ định lớp cụ thể ngay từ đầu, thay vào đó giao việc này cho các lớp con (subclasses).
- Ngữ cảnh: Trong hệ thống thư viện, ta cần tạo các loại sách khác nhau mà không cần sửa đổi code chính của Library

### Bước 2: Đọc lý thuyết - Hiểu mục đích và cấu trúc
- Intent: Định nghĩa một interface hoặc abstract class để tạo đối tượng, nhưng để các lớp con quyết định lớp cụ thể nào sẽ được tạo.
- Problem: Nếu Library trực tiếp dùng new PaperBook() hoặc new EBook(), code sẽ cứng nhắc (hard-coded), khó mở rộng khi thêm loại sách mới (Ví dụ 3DBook)
- Solution: 
    - Tạo một interface Book với các thuộc tính cơ bản như tiêu đề, tác giả, thể loại.
    - Tạo các lớp cụ thể như PaperBook, EBook, AudioBook kế thừa từ Book.
    - Tạo một abstract class BookFactory với phương thức trừu tượng createBook().
    - Tạo các lớp con như PaperBookFactory, EBookFactory, AudioBookFactory để triển khai phương thức createBook().

### Bước 3: Vẽ sơ đồ - Dùng UML để hình dung
```
+----------------+                                                                  +----------------+
|    Book        |<---------------------------------------------------------------->|   BookFactory  |
| (Interface)    |                                                                  | (Abstract)     |     
+----------------+                                                                  +----------------+
| + getTitle()   |                                                                  | + createBook() |
| + getType()    |                                                                  +----------------+
+----------------+                                                                           ^
    ^                                                                                        |
    |                                                                 +----------------------+---------------------+
    |                                                                 |                      |                     |
    |                                                         +-----------------+    +----------------+    +-----------------+
    |                                                         | PaperBookFactory|    | EBookFactory   |    | AudioBookFactory|
    |                                                         +-----------------+    +----------------+    +-----------------+
    |                                                         | + createBook()  |    | + createBook() |    | + createBook()  |
    |                                                         +-----------------+    +----------------+    +-----------------+
+-----------------------------+--------------------+
|                             |                    |
+----------------+    +----------------+    +----------------+
| PaperBook      |    | EBook          |    | AudioBook      |
+----------------+    +----------------+    +----------------+
| + getTitle()   |    | + getTitle()   |    | + getTitle()   |
| + getType()    |    | + getType()    |    | + getType()    |
+----------------+    +----------------+    +----------------+
        
```

### Bước 4: Viết code - Áp dụng vào ví dụ nhỏ
#### Code không dùng Factory Method (ban đầu):
```java
import java.util.ArrayList;
import java.util.List;

class Library {
    private List<Object> books = new ArrayList<>();

    public void addBook(String title, String type) {
        if (type.equals("paper")) {
            books.add(new PaperBook(title));
        } else if (type.equals("ebook")) {
            books.add(new EBook(title));
        } else if (type.equals("audio")) {
            books.add(new AudioBook(title));
        }
    }

    public void displayBooks() {
        System.out.println("Books:");
        for (Object book : books) {
            System.out.println(book.toString());
        }
    }
}

class PaperBook {
    private String title;
    public PaperBook(String title) { this.title = title; }
    public String toString() { return "PaperBook: " + title; }
}

class EBook {
    private String title;
    public EBook(String title) { this.title = title; }
    public String toString() { return "EBook: " + title; }
}

class AudioBook {
    private String title;
    public AudioBook(String title) { this.title = title; }
    public String toString() { return "AudioBook: " + title; }
}

class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook("Harry Potter", "paper");
        library.addBook("Digital Fortress", "ebook");
        library.displayBooks();
    }
}
```
**Kết quả**:
```
Books:
PaperBook: Harry Potter
EBook: Digital Fortress
```
**Vấn đề**:
- Code trong addBook() dùng if-else, cứng nhắc.
- Thêm loại sách mới (như 3DBook) phải sửa trực tiếp code của Library.
- Khó bảo trì, không tuân theo nguyên tắc OCP (Open/Closed Principle).

#### Code dùng Factory Method:
```java
import java.util.ArrayList;
import java.util.List;

interface Book {
    String getTitle();
    String getType();
}

class PaperBook implements Book {
    private String title;
    public PaperBook(String title) { this.title = title; }
    public String getTitle() { return title; }
    public String getType() { return "PaperBook"; }
}

class EBook implements Book {
    private String title;
    public EBook(String title) { this.title = title; }
    public String getTitle() { return title; }
    public String getType() { return "EBook"; }
}

class AudioBook implements Book {
    private String title;
    public AudioBook(String title) { this.title = title; }
    public String getTitle() { return title; }
    public String getType() { return "AudioBook"; }
}

abstract class BookFactory {
    abstract Book createBook(String title);
}

class PaperBookFactory extends BookFactory {
    Book createBook(String title) { return new PaperBook(title); }
}

class EBookFactory extends BookFactory {
    Book createBook(String title) { return new EBook(title); }
}

class AudioBookFactory extends BookFactory {
    Book createBook(String title) { return new AudioBook(title); }
}

class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(BookFactory factory, String title) {
        Book book = factory.createBook(title);
        books.add(book);
    }

    public void displayBooks() {
        System.out.println("Books:");
        for (Book book : books) {
            System.out.println(book.getType() + ": " + book.getTitle());
        }
    }
}

class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new PaperBookFactory(), "Harry Potter");
        library.addBook(new EBookFactory(), "Digital Fortress");
        library.addBook(new AudioBookFactory(), "Dune");
        library.displayBooks();
    }
}
```
**Kết quả** :
```
Books:
PaperBook: Harry Potter
EBook: Digital Fortress
AudioBook: Dune
```
**Nhận xét**: Library không cần biết lớp cụ thể, chỉ cần gọi createBook() từ factory.

### Bước 5: Refactor - Cải thiện code ban đầu
- Code ban đầu: Dùng if-else trong Library để tạo sách.
- Refactor:
    1. Tạo interface Book và các lớp cụ thể (PaperBook, EBook, AudioBook).
    2. Tạo abstract class BookFactory với createBook().
    3. Tạo các factory cụ thể (PaperBookFactory, EBookFactory, AudioBookFactory).
    4. Chuyển logic tạo sách từ Library sang factory.
- Kết quả: Code linh hoạt hơn, dễ mở rộng.

## 3. Strategy Pattern - Tìm kiếm sách linh hoạt
   ### Ý tưởng
   Strategy Pattern cho phép thay đổi chiến lược tìm kiếm (theo tên, tác giả, thể loại) một cách linh hoạt dựa trên yêu cầu của người dùng mà không cần thay đổi mã nguồn của lớp sử dụng.

### Cách thực hiện
- Tạo một interface SearchStrategy với phương thức search(List<Book> books, String query).
- Tạo các lớp cụ thể như SearchByTitle, SearchByAuthor, SearchByGenre triển khai interface này.
- Lớp Library sẽ có một thuộc tính searchStrategy và phương thức setSearchStrategy() để thay đổi chiến lược.

### Ví dụ mã (pseudocode)
```java
interface SearchStrategy {
    List<Book> search(List<Book> books, String query);
}

class SearchByTitle implements SearchStrategy {
    public List<Book> search(List<Book> books, String query) {
        return books.stream()
                    .filter(book -> book.getTitle().contains(query))
                    .collect(Collectors.toList());
    }
}

class Library {
    private SearchStrategy searchStrategy;

    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    public List<Book> searchBooks(String query) {
        return searchStrategy.search(books, query);
    }
}
```

## 4. Observer Pattern - Thông báo sự kiện
   ### Ý tưởng
   Observer Pattern được sử dụng để thông báo cho các đối tượng quan tâm (nhân viên thư viện, người dùng) khi có sách mới hoặc sách hết hạn mượn.

### Cách thực hiện
- Tạo một interface Observer với phương thức update(String message).
- Tạo một lớp Library (chủ thể) với danh sách observers và các phương thức addObserver(), removeObserver(), notifyObservers().
- Tạo các lớp như Librarian, User triển khai Observer.
- Khi có sự kiện (thêm sách, hết hạn), gọi notifyObservers().
### Ví dụ mã (pseudocode)
```java
interface Observer {
    void update(String message);
}

class Librarian implements Observer {
    public void update(String message) {
        System.out.println("Librarian received: " + message);
    }
}

class Library {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void addBook(Book book) {
        books.add(book);
        notifyObservers("New book added: " + book.getTitle());
    }
}
```

## 5. Decorator Pattern - Mở rộng tính năng mượn sách
  ### Ý tưởng
   Decorator Pattern cho phép thêm các tính năng bổ sung (gia hạn, phiên bản đặc biệt) cho việc mượn sách mà không thay đổi lớp Book gốc.

### Cách thực hiện
- Tạo một interface Borrowable với phương thức borrow().
- Tạo lớp BasicBorrow triển khai Borrowable để xử lý mượn sách cơ bản.
- Tạo các lớp decorator như ExtendedBorrow, SpecialEditionBorrow kế thừa từ một abstract class BorrowDecorator (cũng triển khai Borrowable).
### Ví dụ mã (pseudocode)
```java
interface Borrowable {
    void borrow();
}

class BasicBorrow implements Borrowable {
    private Book book;
    public BasicBorrow(Book book) { this.book = book; }
    public void borrow() {
        System.out.println("Borrowed: " + book.getTitle());
    }
}

abstract class BorrowDecorator implements Borrowable {
    protected Borrowable borrowable;
    public BorrowDecorator(Borrowable borrowable) {
        this.borrowable = borrowable;
    }
}

class ExtendedBorrow extends BorrowDecorator {
    public ExtendedBorrow(Borrowable borrowable) { super(borrowable); }
    public void borrow() {
        borrowable.borrow();
        System.out.println("Extended borrowing period");
    }
}

// Sử dụng
Borrowable borrow = new ExtendedBorrow(new BasicBorrow(book));
borrow.borrow();
```
---

# Kết luận
- **Singleton** để quản lý một thư viện duy nhất.
- **Factory Method** để tạo các loại sách khác nhau.
- **Strategy** để linh hoạt trong tìm kiếm.
- **Observer** để thông báo sự kiện.
- **Decorator** để mở rộng tính năng mượn sách.