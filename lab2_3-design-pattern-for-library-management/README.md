# Thiết Kế Hệ Thống Quản Lý Thư Viện Sử Dụng Design Patterns

Hệ thống quản lý thư viện được thiết kế để hỗ trợ các chức năng cơ bản như mượn sách, trả sách, thêm sách mới, xem danh sách sách, và tìm kiếm sách. Để đảm bảo tính mở rộng và dễ bảo trì, hệ thống sử dụng các Design Pattern phù hợp. Dưới đây là giải thích chi tiết cho từng yêu cầu.

## Content
[1. Singleton Pattern - Quản lý thư viện duy nhất](#1-singleton-pattern---quản-lý-thư-viện-duy-nhất)

[2. Factory Method Pattern - Tạo các loại sách khác nhau](#2-factory-method-pattern---tạo-các-loại-sách-khác-nhau)

[3. Strategy Pattern - Tìm kiếm sách linh hoạt](#3-strategy-pattern---tìm-kiếm-sách-linh-hoạt)

[4. Observer Pattern - Thông báo sự kiện](#4-observer-pattern---thông-báo-sự-kiện)

[5. Decorator Pattern - Mở rộng tính năng mượn sách](#5-decorator-pattern---mở-rộng-tính-năng-mượn-sách)


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
   Strategy Pattern là một mẫu thiết kế hành vi (Behavioral Design Pattern) cho phép bạn định nghĩa một tập hợp các thuật toán (chiến lược), đóng gói chúng và khiến chúng có thể thay thế lẫn nhau. Trong trường hợp tìm kiếm sách, ta có thể áp dụng để linh hoạt thay đổi cách tìm kiếm (theo tên, tác giả, thể loại) mà không cần sửa đổi mã nguồn lớp chính.
### Bước 1: Bắt đầu đơn giản
Hãy tưởng tượng bạn có một thư viện sách và muốn tìm kiếm sách theo nhiều tiêu chí khác nhau. Nếu không dùng Strategy Pattern, bạn có thể viết một lớp với nhiều câu lệnh `if-else` để xử lý từng loại tìm kiếm. Nhưng cách này không linh hoạt và khó mở rộng. Với `Strategy Pattern`, ta tách biệt các chiến lược tìm kiếm thành các lớp riêng biệt
### Bước 2: Đọc lý thuyết - Hiểu mục đích và cấu trúc
#### Mục đích: 
- Cho phép thay đổi thuật toán(chiến lược) tại thời điểm chạy (runtime) mà không làm ảnh hưởng đến lớp sử dụng.
- Tăng tính tái sử dụng và dễ bảo trì
#### Cấu trúc:
1. Context: Lớp chính sử dụng chiến lược (ví dụ: lớp quản lý thư viện sách)
2. Strategy: Một interface hoặc abstract class định nghĩa phương thức chung cho các chiến lược (ví dụ: phương thức tìm kiếm.).
3. Concrete Strategies: Các lớp cụ thể triển khai từng chiến lược (tìm theo tên, tác giả, thể loại)

### Bước 3: Vẽ sơ đồ UML
```
+----------------+        +-----------------+
|   BookSearcher |<>----->| SearchStrategy  |
|  (Context)     |        |  (Interface)    |
|                |        +-----------------+
| - strategy     |        | + search()      |
| + setStrategy()|        +-----------------+
| + searchBooks()|               /|\
+----------------+                |
                                  |
        +-------------------------+--------------+
        |                         |              |
+----------------+  +----------------+  +----------------+
| SearchByTitle  |  | SearchByAuthor |  | SearchByGenre  |
| (Concrete)     |  | (Concrete)     |  | (Concrete)     |
+----------------+  +----------------+  +----------------+
| + search()     |  | + search()     |  | + search()     |
+----------------+  +----------------+  +----------------+
```
- `BookSearcher` là Context, chưa một tham chiếu đến `SearchStrategy`
-  `SearchStrategy` là interface định nghĩa phương thức `search()`
- `SeachByTitle`, `SearchByAuthor`, `SearchByGenre` là các chiến lược cụ thể
### Bước 4: Viết code
#### Không dùng Strategy Pattern
```java
class Book {
    String title;
    String author;
    String genre;

    Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}

class BookSearcher {
    public List<Book> searchBooks(List<Book> books, String query, String searchType) {
        List<Book> result = new ArrayList<>();
        if (searchType.equals("title")) {
            for (Book book : books) {
                if (book.title.contains(query)) {
                    result.add(book);
                }
            }
        } else if (searchType.equals("author")) {
            for (Book book : books) {
                if (book.author.contains(query)) {
                    result.add(book);
                }
            }
        } else if (searchType.equals("genre")) {
            for (Book book : books) {
                if (book.genre.contains(query)) {
                    result.add(book);
                }
            }
        }
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Book> books = Arrays.asList(
            new Book("Java 101", "John Doe", "Programming"),
            new Book("Python Basics", "Jane Smith", "Programming")
        );
        BookSearcher searcher = new BookSearcher();
        System.out.println(searcher.searchBooks(books, "Java", "title"));
    }
}
```
=> Nếu thêm tiêu chí tìm kiếm mới (ví dụ: theo năm xuất bản), bạn phải sửa đổi lớp `BookSearcher`, vi phạm nguyên tắc Open/Closed

#### Dùng Strategy Pattern
```java
class Book {
    String title;
    String author;
    String genre;

    Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}

// Interface Strategy
interface SearchStrategy {
    List<Book> search(List<Book> books, String query);
}

// Concrete Strategy 1
class SearchByTitle implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.title.contains(query)) {
                result.add(book);
            }
        }
        return result;
    }
}

// Concrete Strategy 2
class SearchByAuthor implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.author.contains(query)) {
                result.add(book);
            }
        }
        return result;
    }
}

// Concrete Strategy 3
class SearchByGenre implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.genre.contains(query)) {
                result.add(book);
            }
        }
        return result;
    }
}

// Context
class BookSearcher {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Book> searchBooks(List<Book> books, String query) {
        return strategy.search(books, query);
    }
}

public class Main {
    public static void main(String[] args) {
        List<Book> books = Arrays.asList(
            new Book("Java 101", "John Doe", "Programming"),
            new Book("Python Basics", "Jane Smith", "Programming")
        );

        BookSearcher searcher = new BookSearcher();

        // Tìm theo tiêu đề
        searcher.setStrategy(new SearchByTitle());
        System.out.println("Search by title: " + searcher.searchBooks(books, "Java"));

        // Tìm theo tác giả
        searcher.setStrategy(new SearchByAuthor());
        System.out.println("Search by author: " + searcher.searchBooks(books, "Jane"));

        // Tìm theo thể loại
        searcher.setStrategy(new SearchByGenre());
        System.out.println("Search by genre: " + searcher.searchBooks(books, "Programming"));
    }
}
```
**Ưu điểm:** 
- Linh hoạt: Thêm chiến lước mới (ví dụ: `SearchByYear`) chỉ tạo lớp mới mà không sửa `BookSearcher`
- Dễ bảo trì: Mỗi chiến lước được tách biệt, giảm sự phụ thuộc.
- Có thể thay đổi chiến lược tại runtime bằng cách gọi `setStrategy()`.

## 4. Observer Pattern - Thông báo sự kiện
### Ý tưởng
Observer Pattern là một mẫu thiết kế hành vi (Behavioral Design Pattern) cho phép một đối tượng (Subject) thông báo tự động đến nhiều đối tượng khác (Observers) khi trạng thái của nó thay đổi. Trong trường hợp này:

Subject: Thư viện hoặc danh sách sách.
Observers: Nhân viên thư viện, người dùng muốn nhận thông báo.
Sự kiện: Thêm sách mới, sách hết hạn mượn.
Mục tiêu là các đối tượng quan tâm sẽ tự động nhận thông báo mà không cần phải kiểm tra thủ công liên tục.

### Bước 1: Bắt đầu đơn giản
Hãy tưởng tượng một thư viện muốn thông bảo cho nhân viên và người dùng khi có sách mới được thêm vào hoặc khi một cuốn sách quá hạn mượn. Nếu không dùng Observer Pattern, bạn có thể phải gọi thủ công từng phương thức thông báo cho từng đối tượng, dẫn đến mã nguồn lặp lại và khó mở rộng.

### Bước 2: Đọc lý thuyết - Hiểu mục đích và cấu trúc
#### Mục đích: Thiết lập mối quan hệ một - nhiều (one - to - many) giữa Subject và Observers. Khi Subject thay đổi, tất cả các Observers được thông báo tự động.
#### Cấu trúc:
1. Object: Lưu danh sách Observers, có phương thức để thêm/xóa Observer và thông báo
2. Observer: Interface định nghĩa phương thức cập nhật (update())
3. Concrete Subject: Lớp cụ thể quản lý trạng thái (ví dụ: danh sách sách)
4. Concrete Observers: Các lớp cụ thể nhận thông bảo (nhân viên, người dùng)

### Bước 3: Vẽ sơ đồ UML
```
+----------------+        +-----------------+
|   Library      |<>----->|   Observer      |
|  (Subject)     |        |  (Interface)    |
|                |        +-----------------+
| - observers    |        | + update()      |
| + addObserver()|        +-----------------+
| + removeObserver()|          /|\
| + notifyObservers()|          |
+----------------+              |
                                |
        +-----------------------+--------------------+
        |                                           |
+----------------+                          +----------------+
| Librarian      |                          | LibraryUser    |
| (Concrete)     |                          | (Concrete)     |
+----------------+                          +----------------+
| + update()     |                          | + update()     |
+----------------+                          +----------------+
```
- Library là Subject, quản lý danh sách Observers.
- Observer là interface với phương thức update().
- Librarian và LibraryUser là Concrete Observers.
### Bước 4: Viết code
```java
import java.util.ArrayList;
import java.util.List;

// Interface Observer
interface Observer {
    void update(String message);
}

// Concrete Observer 1: Nhân viên thư viện
class Librarian implements Observer {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Nhân viên " + name + " nhận thông báo: " + message);
    }
}

// Concrete Observer 2: Người dùng thư viện
class LibraryUser implements Observer {
    private String name;

    public LibraryUser(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Người dùng " + name + " nhận thông báo: " + message);
    }
}

// Subject: Thư viện
class Library {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // Sự kiện: Thêm sách mới
    public void addNewBook(String bookTitle) {
        String message = "Sách mới được thêm: " + bookTitle;
        notifyObservers(message);
    }

    // Sự kiện: Sách quá hạn
    public void bookOverdue(String bookTitle) {
        String message = "Sách quá hạn: " + bookTitle;
        notifyObservers(message);
    }
}

// Main để thử nghiệm
public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Tạo các Observer
        Observer librarian = new Librarian("John");
        Observer user1 = new LibraryUser("Alice");
        Observer user2 = new LibraryUser("Bob");

        // Đăng ký các Observer
        library.addObserver(librarian);
        library.addObserver(user1);
        library.addObserver(user2);

        // Sự kiện xảy ra
        library.addNewBook("Design Patterns");
        library.bookOverdue("Java 101");
    }
}
```
**Kết quả**:
```
Nhân viên John nhận thông báo: Sách mới được thêm: Design Patterns
Người dùng Alice nhận thông báo: Sách mới được thêm: Design Patterns
Người dùng Bob nhận thông báo: Sách mới được thêm: Design Patterns
Nhân viên John nhận thông báo: Sách quá hạn: Java 101
Người dùng Alice nhận thông báo: Sách quá hạn: Java 101
Người dùng Bob nhận thông báo: Sách quá hạn: Java 101
```
**Phân tích**:
1. Ưu điểm:
- Tính linh hoạt: Dễ dàng thêm hoặc xóa Observer (nhân viên, người dùng) mà không cần sửa mã nguồn của Library.
- Tự động hóa: Observers được thông báo ngay khi có sự kiện, không cần kiểm tra thủ công.
- Tái sử dụng: Có thể áp dụng cho các hệ thống thông báo khác.
2. Nhược điểm:
- Nếu có quá nhiều Observer, việc thông báo có thể tốn tài nguyên.
- Observer cần được quản lý cẩn thận để tránh rò rỉ bộ nhớ (memory leak) nếu không xóa khi không cần thiết.

## 5. Decorator Pattern - Mở rộng tính năng mượn sách
### Ý tưởng
Decorator Pattern là một mẫu thiết kế cấu trúc (Structural Design Pattern) cho phép thêm chức năng mới vào một đối tượng hiện có mà không cần sửa đổi mã nguồn của lớp gốc. Trong trường hợp này:

Đối tượng gốc: Quy trình mượn sách cơ bản.
Tính năng bổ sung: Gia hạn thời gian mượn, cung cấp phiên bản đặc biệt (ví dụ: sách có chữ ký tác giả).
Mục tiêu: Mở rộng tính năng mà không làm thay đổi lớp Book hoặc logic mượn sách cơ bản.
### Bước 1: Bắt đầu đơn giản
Giả sử bạn có một hệ thống mượn sách cơ bản. Nếu muốn thêm tính năng như gia hạn hoặc cung cấp sách phiên bản đặc biệt, cách thông thường là thêm thuộc tính hoặc phương thức vào lớp Book. Tuy nhiên, điều này vi phạm nguyên tắc Open/Closed (mở để mở rộng, đóng để sửa đổi). Decorator Pattern giải quyết vấn đề bằng cách bọc (wrap) đối tượng gốc với các lớp bổ sung.
### Bước 2: Đọc lý thuyết - Hiểu mục đích và cấu trúc
#### Mục đích: gắn thêm trách nhiệm hoặc tính năng cho đối tượng tại runtime mà không cần kế thừa trực tiếp.
#### Cấu trúc:
1. Component: Interface hoặc lớp trừu tượng định nghĩa hành vi cơ bản (ví dụ: mượn sách).
2. Concrete Component: Lớp cụ thể thực hiện hành vi cơ bản (mượn sách thông thường).
3. Decorator: Lớp trừu tượng bọc Component, thêm tính năng chung.
4. Concrete Decorator: Các lớp cụ thể thêm tính năng cụ thể (gia hạn, phiên bản đặc biệt).
### Bước 3: Vẽ sơ đồ UML:
```
+----------------+        +-----------------+
|   BookBorrow   |<-------|   BookDecorator |
|  (Component)   |        |   (Decorator)   |
|                |        +-----------------+
| + borrow()     |<>----->| - book: BookBorrow |
+----------------+        | + borrow()      |
       /|\                +-----------------+
        |                        /|\
        |                         |
+----------------+   +------------+------------+
| SimpleBook     |   | ExtendedBorrowTime | SpecialEdition |
| (Concrete)     |   | (Concrete Decorator)| (Concrete Decorator)|
+----------------+   +---------------------+ +----------------+
| + borrow()     |   | + borrow()         | | + borrow()     |
+----------------+   +---------------------+ +----------------+
```
- BookBorrow: Interface định nghĩa hành vi mượn sách.
- SimpleBook: Lớp cơ bản thực hiện mượn sách thông thường.
- BookDecorator: Lớp trừu tượng bọc BookBorrow.
- ExtendedBorrowTime, SpecialEdition: Các Decorator cụ thể thêm tính năng.

### Bước 4: Viết code
```java
// Interface Component
interface BookBorrow {
    String borrow();
}

// Concrete Component: Mượn sách cơ bản
class SimpleBook implements BookBorrow {
    private String title;

    public SimpleBook(String title) {
        this.title = title;
    }

    @Override
    public String borrow() {
        return "Mượn sách: " + title + " (thời gian mượn: 7 ngày)";
    }
}

// Decorator trừu tượng
abstract class BookDecorator implements BookBorrow {
    protected BookBorrow book;

    public BookDecorator(BookBorrow book) {
        this.book = book;
    }

    @Override
    public String borrow() {
        return book.borrow();
    }
}

// Concrete Decorator 1: Gia hạn thời gian mượn
class ExtendedBorrowTime extends BookDecorator {
    public ExtendedBorrowTime(BookBorrow book) {
        super(book);
    }

    @Override
    public String borrow() {
        return super.borrow() + " + Gia hạn thêm 7 ngày";
    }
}

// Concrete Decorator 2: Phiên bản đặc biệt
class SpecialEdition extends BookDecorator {
    public SpecialEdition(BookBorrow book) {
        super(book);
    }

    @Override
    public String borrow() {
        return super.borrow() + " (Phiên bản đặc biệt có chữ ký tác giả)";
    }
}

// Main để thử nghiệm
public class Main {
    public static void main(String[] args) {
        // Mượn sách cơ bản
        BookBorrow simpleBook = new SimpleBook("Java 101");
        System.out.println(simpleBook.borrow());

        // Mượn sách với gia hạn
        BookBorrow extendedBook = new ExtendedBorrowTime(new SimpleBook("Python Basics"));
        System.out.println(extendedBook.borrow());

        // Mượn sách phiên bản đặc biệt
        BookBorrow specialBook = new SpecialEdition(new SimpleBook("Design Patterns"));
        System.out.println(specialBook.borrow());

        // Kết hợp cả gia hạn và phiên bản đặc biệt
        BookBorrow extendedSpecialBook = new ExtendedBorrowTime(
            new SpecialEdition(new SimpleBook("C++ Advanced"))
        );
        System.out.println(extendedSpecialBook.borrow());
    }
}
```
```
Mượn sách: Java 101 (thời gian mượn: 7 ngày)
Mượn sách: Python Basics (thời gian mượn: 7 ngày) + Gia hạn thêm 7 ngày
Mượn sách: Design Patterns (thời gian mượn: 7 ngày) (Phiên bản đặc biệt có chữ ký tác giả)
Mượn sách: C++ Advanced (thời gian mượn: 7 ngày) (Phiên bản đặc biệt có chữ ký tác giả) + Gia hạn thêm 7 ngày
```
#### Phân tích
**Ưu điểm:**
- Linh hoạt: Có thể kết hợp nhiều tính năng (gia hạn + phiên bản đặc biệt) tại runtime.
- Không sửa lớp gốc: Lớp SimpleBook không bị thay đổi khi thêm tính năng mới.
- Tái sử dụng: Các Decorator có thể được dùng cho nhiều loại sách khác nhau.
**Nhược điểm:**
- Có thể tạo ra nhiều lớp nhỏ nếu có quá nhiều tính năng bổ sung.
- Việc gỡ lỗi có thể phức tạp hơn do cấu trúc bọc lồng nhau.

# Kết luận
- **Singleton Pattern**: Đảm bảo chỉ có một thể hiện duy nhất của thư viện trong toàn bộ hệ thống, giúp quản lý tập trung và tránh xung đột dữ liệu. Điều này rất hữu ích khi cần một điểm truy cập toàn cục cho các tài nguyên chung.
- **Factory Method Pattern**: Cung cấp cơ chế tạo các loại sách khác nhau (sách giấy, sách điện tử, sách audio) mà không cần thay đổi mã nguồn chính. Nó cho phép mở rộng dễ dàng khi thêm loại sách mới, tuân thủ nguyên tắc Open/Closed.
- **Strategy Pattern**: Mang lại sự linh hoạt trong việc tìm kiếm sách theo nhiều tiêu chí (tên, tác giả, thể loại) tại thời điểm chạy, mà không cần sửa đổi lớp chính. Điều này giúp hệ thống dễ thích nghi với các yêu cầu tìm kiếm mới.
- **Observer Pattern**: Tự động thông báo cho các đối tượng quan tâm (nhân viên, người dùng) khi có sự kiện xảy ra, như sách mới được thêm hoặc sách quá hạn. Nó tạo ra một cơ chế thông báo hiệu quả và giảm sự phụ thuộc thủ công.
- **Decorator Pattern**: Cho phép mở rộng tính năng mượn sách (gia hạn, phiên bản đặc biệt) mà không làm thay đổi lớp gốc, mang lại sự linh hoạt trong việc tùy chỉnh trải nghiệm mượn sách cho người dùng.