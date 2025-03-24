# Thiết Kế Hệ Thống Quản Lý Thư Viện Sử Dụng Design Patterns

Hệ thống quản lý thư viện được thiết kế để hỗ trợ các chức năng cơ bản như mượn sách, trả sách, thêm sách mới, xem danh sách sách, và tìm kiếm sách. Để đảm bảo tính mở rộng và dễ bảo trì, hệ thống sử dụng các Design Pattern phù hợp. Dưới đây là giải thích chi tiết cho từng yêu cầu.

## Content
[1. Singleton Pattern - Quản lý thư viện duy nhất](#1-singleton-pattern---quản-lý-thư-viện-duy-nhất)

[2. Factory Method Pattern - Tạo các loại sách khác nhau](#2-factory-method-pattern---tạo-các-loại-sách-khác-nhau)

[3. Strategy Pattern - Tìm kiếm sách linh hoạt](#3-strategy-pattern---tìm-kiếm-sách-linh-hoạt)

[4. Observer Pattern - Thông báo sự kiện](#4-observer-pattern---thông-báo-sự-kiện)

[5. Decorator Pattern - Mở rộng tính năng mượn sách](#5-decorator-pattern---mở-rộng-tính-năng-mượn-sách)

[6. State Pattern](#6-state-pattern)

[7. Adapter Pattern](#7-adapter-pattern)

[8. Composite Pattern](#8-composite-pattern)

[9. Chain of Responsibility Pattern](#9-chain-of-responsibility-pattern)


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
![Library UML Diagram](./uml/singleton_uml.png)

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
![Library UML Diagram](./uml/factorymethod_uml.png)

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
![Library UML Diagram](./uml/strategy_uml.png)
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
![Library UML Diagram](./uml/observer_uml.png)
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
![Library UML Diagram](./uml/decorator_uml.png)
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

## 6. State Pattern
### Bước 1: Bắt đầu đơn giản
Hệ thống sẽ có một lớp Order đại diện cho đơn hàng và các trạng thái khác nhau như New, Processing, Delivered, Cancelled. Mỗi trạng thái sẽ có hành vi riêng.
### Bước 2: Đọc lý thuyết
- State Pattern: Cho phép một đối tượng thay đổi hành vi khi trạng thái nội tại thay đổi. Nó bao gồm:
- Context: Đối tượng chính (ở đây là Order).
- State: Interface hoặc abstract class định nghĩa hành vi chung.
- ConcreteState: Các lớp cụ thể triển khai hành vi cho từng trạng thái.
### Bước 3: Vẽ sơ đồ UML
![Library UML Diagram](./uml/state_uml.png)
- OrderState là interface, được các lớp NewState, ProcessingState, DeliveredState, CancelledState triển khai (dùng mũi tên ^ để chỉ "implements").
- Order chứa một tham chiếu đến OrderState (dùng -----> để biểu thị quan hệ composition).
### Bước 4: Viết code
```java
// State Interface
interface OrderState {
    void handle(Order order);
}

// Context
class Order {
    private OrderState state;

    public Order() {
        this.state = new NewState(); // Trạng thái ban đầu
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void handleOrder() {
        state.handle(this);
    }
}

// Concrete States
class NewState implements OrderState {
    public void handle(Order order) {
        System.out.println("Kiểm tra thông tin đơn hàng.");
        order.setState(new ProcessingState());
    }
}

class ProcessingState implements OrderState {
    public void handle(Order order) {
        System.out.println("Đóng gói và vận chuyển.");
        order.setState(new DeliveredState());
    }
}

class DeliveredState implements OrderState {
    public void handle(Order order) {
        System.out.println("Cập nhật trạng thái đơn hàng là đã giao.");
    }
}

class CancelledState implements OrderState {
    public void handle(Order order) {
        System.out.println("Hủy đơn hàng và hoàn tiền.");
    }
}

// Main
public class OrderManagement {
    public static void main(String[] args) {
        Order order = new Order();
        order.handleOrder(); // New -> Processing
        order.handleOrder(); // Processing -> Delivered
        order.handleOrder(); // Delivered

        Order cancelledOrder = new Order();
        cancelledOrder.setState(new CancelledState());
        cancelledOrder.handleOrder(); // Cancelled
    }
}
```

## 7. Adapter Pattern
### Bước 1: Bắt đầu đơn giản
- Hệ thống đích (Target) yêu cầu dữ liệu JSON.
- Hệ thống hiện có (Adaptee) chỉ cung cấp/cần dữ liệu XML.
- Tạo một Adapter để chuyển đổi qua lại giữa XML và JSON.
### Bước 2: Đọc lý thuyết
- Adapter Pattern: Chuyển đổi giao diện của một lớp thành giao diện mà client mong muốn. Bao gồm:
- Target: Giao diện mà client sử dụng (ở đây là JsonService).
- Adaptee: Hệ thống hiện có cần thích nghi (ở đây là XmlService).
- Adapter: Lớp trung gian thực hiện chuyển đổi (ở đây là XmlToJsonAdapter).
### Bước 3: Vẽ sơ đồ UML
![Library UML Diagram](./uml/adapter_uml.png)
- JsonService: Giao diện Target định nghĩa phương thức processJson().
- XmlService: Adaptee, cung cấp phương thức processXml().
- XmlToJsonAdapter: Adapter, triển khai JsonService và chứa một tham chiếu đến - XmlService (dùng -----> để biểu thị composition). Nó chuyển đổi dữ liệu giữa XML và JSON.
### Bước 4: Viết code
```java
// Target Interface
interface JsonService {
    void processJson(String jsonData);
}

// Adaptee
class XmlService {
    public void processXml(String xmlData) {
        System.out.println("Processing XML data: " + xmlData);
    }

    public String generateXml() {
        return "<data><name>John</name><age>30</age></data>";
    }
}

// Adapter
class XmlToJsonAdapter implements JsonService {
    private XmlService xmlService;

    public XmlToJsonAdapter(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @Override
    public void processJson(String jsonData) {
        // Chuyển đổi JSON sang XML (giả lập)
        String xmlData = convertJsonToXml(jsonData);
        xmlService.processXml(xmlData);
    }

    public String getJsonFromXml() {
        // Chuyển đổi XML sang JSON (giả lập)
        String xmlData = xmlService.generateXml();
        return convertXmlToJson(xmlData);
    }

    // Hàm giả lập chuyển đổi JSON sang XML
    private String convertJsonToXml(String jsonData) {
        System.out.println("Converting JSON to XML: " + jsonData);
        return "<converted>" + jsonData + "</converted>";
    }

    // Hàm giả lập chuyển đổi XML sang JSON
    private String convertXmlToJson(String xmlData) {
        System.out.println("Converting XML to JSON: " + xmlData);
        return "{\"converted\": \"" + xmlData + "\"}";
    }
}

// Main
public class AdapterDemo {
    public static void main(String[] args) {
        // Tạo đối tượng Adaptee
        XmlService xmlService = new XmlService();

        // Tạo Adapter
        JsonService adapter = new XmlToJsonAdapter(xmlService);

        // Client gửi JSON, Adapter chuyển sang XML
        adapter.processJson("{\"name\": \"John\", \"age\": 30}");

        // Client yêu cầu JSON từ XML
        String jsonResult = ((XmlToJsonAdapter) adapter).getJsonFromXml();
        System.out.println("Received JSON: " + jsonResult);
    }
}
```
```
Converting JSON to XML: {"name": "John", "age": 30}
Processing XML data: <converted>{"name": "John", "age": 30}</converted>
Converting XML to JSON: <data><name>John</name><age>30</age></data>
Received JSON: {"converted": "<data><name>John</name><age>30</age></data>"}
```
- Adapter Pattern giúp kết nối hai hệ thống không tương thích (XML và JSON) mà không cần thay đổi mã nguồn của hệ thống hiện có (XmlService).
- Code dễ mở rộng: Nếu cần thêm định dạng mới (ví dụ YAML), chỉ cần tạo một Adapter khác.
- Trong thực tế, bạn có thể sử dụng thư viện như Jackson hoặc Gson để chuyển đổi JSON/XML chính xác hơn thay vì giả lập như ở đây.

## 8. Composite Pattern
### Bước 1: Bắt đầu đơn giản
- Tạo một giao diện chung cho cả thư mục và tập tin (hiển thị thông tin).
- Thư mục có thể thêm/xóa thành phần con, còn tập tin thì không.
### Bước 2: Đọc lý thuyết
- Composite Pattern: Tổ chức các đối tượng thành cấu trúc cây để biểu diễn mối quan hệ phần-toàn (part-whole). Bao gồm:
- Component: Giao diện hoặc lớp trừu tượng định nghĩa hành vi chung (ở đây là FileSystemComponent).
- Leaf: Đối tượng đơn giản không chứa con (ở đây là File).
- Composite: Đối tượng phức tạp chứa các thành phần con (ở đây là Directory).
### Bước 3: Vẽ sơ đồ UML
![Library UML Diagram](./uml/composite_uml.png)
- FileSystemComponent: Giao diện chung với các phương thức displayInfo(), addComponent(), removeComponent(). Các phương thức thêm/xóa mặc định ném ngoại lệ (không áp dụng cho Leaf).
- File: Leaf, chỉ chứa thông tin cơ bản (tên, kích thước) và triển khai displayInfo().
- Directory: Composite, chứa danh sách các thành phần con (children) và triển khai tất cả phương thức, bao gồm quản lý con.

### Bước 4: Viết code
```java
import java.util.ArrayList;
import java.util.List;

// Component
interface FileSystemComponent {
    void displayInfo();
    default void addComponent(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot add component.");
    }
    default void removeComponent(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot remove component.");
    }
}

// Leaf
class File implements FileSystemComponent {
    private String name;
    private long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void displayInfo() {
        System.out.println("File: " + name + ", Size: " + size + " bytes");
    }
}

// Composite
class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> children;

    public Directory(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public void addComponent(FileSystemComponent component) {
        children.add(component);
    }

    @Override
    public void removeComponent(FileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public void displayInfo() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent child : children) {
            child.displayInfo();
        }
    }
}

// Main
public class FileSystemDemo {
    public static void main(String[] args) {
        // Tạo các file
        FileSystemComponent file1 = new File("document.txt", 1200);
        FileSystemComponent file2 = new File("image.jpg", 5400);

        // Tạo thư mục con
        Directory subDir = new Directory("SubFolder");
        subDir.addComponent(new File("note.txt", 300));

        // Tạo thư mục gốc
        Directory rootDir = new Directory("Root");
        rootDir.addComponent(file1);
        rootDir.addComponent(file2);
        rootDir.addComponent(subDir);

        // Hiển thị thông tin
        rootDir.displayInfo();

        // Thử xóa một file
        rootDir.removeComponent(file1);
        System.out.println("\nAfter removing document.txt:");
        rootDir.displayInfo();
    }
}
```
```
Directory: Root
File: document.txt, Size: 1200 bytes
File: image.jpg, Size: 5400 bytes
Directory: SubFolder
File: note.txt, Size: 300 bytes

After removing document.txt:
Directory: Root
File: image.jpg, Size: 5400 bytes
Directory: SubFolder
File: note.txt, Size: 300 bytes
```
- Composite Pattern cho phép xử lý thư mục và tập tin theo cách thống nhất thông qua giao diện chung FileSystemComponent.
- Thư mục (Directory) quản lý các thành phần con đệ quy, trong khi tập tin (File) chỉ cung cấp thông tin cơ bản.
- Code dễ mở rộng (ví dụ: thêm loại tập tin mới hoặc thư mục đặc biệt) và tuân theo nguyên tắc Open/Closed.
- Ứng dụng này cũng phù hợp cho giao diện người dùng (UI), nơi các thành phần như nút, hộp thoại có thể được nhóm lại thành các thành phần phức tạp hơn.

## 9. Chain of Responsibility Pattern
### Phân tích và lựa chọn Pattern
- Chain of Responsibility Pattern: Phù hợp vì:
    - Yêu cầu đăng nhập cần qua nhiều bước kiểm tra tuần tự (kiểm tra tài khoản → kiểm tra quyền → xác thực 2 yếu tố).
    - Mỗi bước có thể xử lý yêu cầu hoặc chuyển tiếp đến bước tiếp theo nếu cần.
Cho phép linh hoạt thêm hoặc bớt bước kiểm tra mà không thay đổi logic chính.
### Bước 1: Bắt đầu đơn giản
- Tạo một chuỗi các xử lý (handler) cho các bước: kiểm tra tài khoản, kiểm tra quyền, xác thực 2 yếu tố.
- Mỗi handler quyết định xem có xử lý yêu cầu hay chuyển tiếp đến handler tiếp theo.
### Bước 2: Đọc lý thuyết
- Chain of Responsibility Pattern: Cho phép truyền yêu cầu qua một chuỗi các đối tượng xử lý, mỗi đối tượng có thể xử lý yêu cầu hoặc chuyển tiếp. Bao gồm:
- Handler: Giao diện hoặc lớp trừu tượng định nghĩa phương thức xử lý và liên kết đến handler tiếp theo.
- ConcreteHandler: Các lớp cụ thể triển khai logic xử lý cho từng bước.
- Client: Gửi yêu cầu vào chuỗi.
### Bước 3: Vẽ sơ đồ UML
![Library UML Diagram](./uml/chainofresponsibility_uml.png)
- AuthHandler: Giao diện định nghĩa handleRequest() và setNext() để thiết lập chuỗi.
- AccountChecker, PermissionChecker, TwoFactorChecker: Concrete Handlers, mỗi lớp xử lý một bước và có tham chiếu đến handler tiếp theo (dùng -----> để biểu thị liên kết chuỗi).
- Mỗi handler có thể dừng chuỗi (nếu thất bại) hoặc chuyển tiếp đến handler tiếp theo.
### Bước 4: Viết code
```java
// Handler Interface
interface AuthHandler {
    void handleRequest(LoginRequest request);
    void setNext(AuthHandler next);
}

// Login Request (dữ liệu đầu vào)
class LoginRequest {
    private String username;
    private String password;
    private String role;
    private String twoFactorCode;

    public LoginRequest(String username, String password, String role, String twoFactorCode) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.twoFactorCode = twoFactorCode;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getTwoFactorCode() { return twoFactorCode; }
}

// Concrete Handler 1: Kiểm tra tài khoản
class AccountChecker implements AuthHandler {
    private AuthHandler next;

    @Override
    public void setNext(AuthHandler next) {
        this.next = next;
    }

    @Override
    public void handleRequest(LoginRequest request) {
        if (request.getUsername().equals("admin") && request.getPassword().equals("12345")) {
            System.out.println("Account verified.");
            if (next != null) {
                next.handleRequest(request);
            }
        } else {
            System.out.println("Account check failed: Invalid username or password.");
        }
    }
}

// Concrete Handler 2: Kiểm tra quyền
class PermissionChecker implements AuthHandler {
    private AuthHandler next;

    @Override
    public void setNext(AuthHandler next) {
        this.next = next;
    }

    @Override
    public void handleRequest(LoginRequest request) {
        if (request.getRole().equals("admin")) {
            System.out.println("Permission granted.");
            if (next != null) {
                next.handleRequest(request);
            }
        } else {
            System.out.println("Permission check failed: Insufficient role.");
        }
    }
}

// Concrete Handler 3: Xác thực 2 yếu tố
class TwoFactorChecker implements AuthHandler {
    private AuthHandler next;

    @Override
    public void setNext(AuthHandler next) {
        this.next = next;
    }

    @Override
    public void handleRequest(LoginRequest request) {
        if (request.getTwoFactorCode().equals("XYZ789")) {
            System.out.println("Two-factor authentication successful.");
            if (next != null) {
                next.handleRequest(request);
            } else {
                System.out.println("Login successful!");
            }
        } else {
            System.out.println("Two-factor check failed: Invalid code.");
        }
    }
}

// Main
public class LoginSystemDemo {
    public static void main(String[] args) {
        // Tạo các handler
        AuthHandler accountChecker = new AccountChecker();
        AuthHandler permissionChecker = new PermissionChecker();
        AuthHandler twoFactorChecker = new TwoFactorChecker();

        // Thiết lập chuỗi
        accountChecker.setNext(permissionChecker);
        permissionChecker.setNext(twoFactorChecker);

        // Tạo yêu cầu đăng nhập
        LoginRequest validRequest = new LoginRequest("admin", "12345", "admin", "XYZ789");
        LoginRequest invalidRequest = new LoginRequest("user", "wrong", "guest", "ABC123");

        // Xử lý yêu cầu
        System.out.println("Valid login attempt:");
        accountChecker.handleRequest(validRequest);

        System.out.println("\nInvalid login attempt:");
        accountChecker.handleRequest(invalidRequest);
    }
}
```
```
Valid login attempt:
Account verified.
Permission granted.
Two-factor authentication successful.
Login successful!

Invalid login attempt:
Account check failed: Invalid username or password.
```
- Chain of Responsibility Pattern cho phép tách biệt logic của từng bước kiểm tra (tài khoản, quyền, 2FA) thành các handler riêng lẻ.
- Linh hoạt: Dễ dàng thêm bước mới (ví dụ: kiểm tra IP) bằng cách thêm handler mới vào chuỗi mà không sửa đổi code hiện có.
- Mỗi handler tự quyết định có chuyển tiếp yêu cầu hay không, giúp xử lý tuần tự và dừng lại khi cần (nếu thất bại).
- Ứng dụng thực tế: Có thể tích hợp với cơ sở dữ liệu hoặc dịch vụ xác thực thực sự thay vì logic giả lập như ở đây.

