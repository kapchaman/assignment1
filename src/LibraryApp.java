import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private List<Book> books = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        app.run();
    }

    public void run() {
        boolean running = true;

        // main program loop
        while (running) {
            printMenu();
            System.out.print("choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    showBooks();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    searchByTitle();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    deleteBook();
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Wrong option. Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nwelcome to Library App!");
        System.out.println("1. print all books");
        System.out.println("2. add new book");
        System.out.println("3. search books by title");
        System.out.println("4. borrow a book");
        System.out.println("5. return a book");
        System.out.println("6. delete a book by id");
        System.out.println("7. quit");
    }

    // prints all books
    private void showBooks() {
        if (books.isEmpty()) {
            System.out.println("no books in the library");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void addBook() {
        System.out.print("enter title: ");
        String title = scanner.nextLine();

        System.out.print("enter author: ");
        String author = scanner.nextLine();

        System.out.print("enter year: ");
        int year = readInt();

        try {
            Book book = new Book(title, author, year);
            books.add(book);
            System.out.println("book was added.");
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void searchByTitle() {
        System.out.print("enter part of the title: ");
        String text = scanner.nextLine().toLowerCase();

        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(text)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("nothing found.");
        }
    }

    private void borrowBook() {
        System.out.print("Enter book id: ");
        int id = readInt();

        Book book = findBook(id);

        if (book == null) {
            System.out.println("Book not found.");
        } else if (book.isAvailable()) {
            book.markAsBorrowed();
            System.out.println("Book borrowed.");
        } else {
            System.out.println("This book is already borrowed.");
        }
    }

    private void returnBook() {
        System.out.print("Enter book id: ");
        int id = readInt();

        Book book = findBook(id);

        if (book == null) {
            System.out.println("Book not found.");
        } else if (!book.isAvailable()) {
            book.markAsReturned();
            System.out.println("Book returned.");
        } else {
            System.out.println("This book was not borrowed.");
        }
    }

    private void deleteBook() {
        System.out.print("Enter book id: ");
        int id = readInt();

        Book book = findBook(id);

        if (book == null) {
            System.out.println("Book not found.");
        } else {
            books.remove(book);
            System.out.println("Book removed from library.");
        }
    }

    // searches book by id
    private Book findBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // reads integer safely from console
    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a number: ");
            scanner.nextLine();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
}