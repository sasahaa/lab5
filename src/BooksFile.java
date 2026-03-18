import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BooksFile {

    static class Book {
        String author, title, year;

        Book(String author, String title, String year) {
            this.author = author;
            this.title = title;
            this.year = year;
        }

        @Override
        public String toString() {
            return "Автор: " + author + ", Назва: " + title + ", Рік: " + year;
        }
    }

    // 1. Створення файлу
    public static void writeBooks(String booksFile) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedWriter writer = new BufferedWriter(new FileWriter(booksFile));
        while (true) {
            System.out.print("Введіть автора або 'стоп': ");
            String author = scanner.nextLine();
            if (author.equalsIgnoreCase("стоп")) break;

            System.out.print("Введіть назву книги: ");
            String title = scanner.nextLine();

            System.out.print("Введіть рік видання: ");
            String year = scanner.nextLine();

            writer.write(author + "," + title + "," + year);
            writer.newLine();
        }
        writer.close();
        System.out.println("Файл створено.");
    }

    // 2. Додавання записів
    public static void appendBooks(String booksFile) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedWriter writer = new BufferedWriter(new FileWriter(booksFile, true)); // append = true
        while (true) {
            System.out.print("Додати автора (або 'стоп'): ");
            String author = scanner.nextLine();
            if (author.equalsIgnoreCase("стоп")) break;

            System.out.print("Додати назву книги: ");
            String title = scanner.nextLine();

            System.out.print("Додати рік видання: ");
            String year = scanner.nextLine();

            writer.write(author + "," + title + "," + year);
            writer.newLine();
        }
        writer.close();
        System.out.println("Додано нові записи.");
    }

    // 3. Перегляд файлу
    public static void viewBooks(String booksFile) throws IOException {
        File file = new File(booksFile);
        if (!file.exists()) {
            System.out.println("Файл не існує!");
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(booksFile));
        String line;
        System.out.println("Вміст файлу:");
        int i = 1;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            System.out.println(i + ". " + "Автор: " + parts[0] + ", Назва: " + parts[1] + ", Рік: " + parts[2]);
            i++;
        }
        reader.close();
    }

    // 4. Пошук книги за назвою
    public static void findBook(String booksFile) throws IOException {
        File file = new File(booksFile);
        if (!file.exists()) {
            System.out.println("Файл не існує!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву книги для пошуку: ");
        String searchTitle = scanner.nextLine().toLowerCase();
        BufferedReader reader = new BufferedReader(new FileReader(booksFile));
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[1].toLowerCase().equals(searchTitle)) {
                System.out.println("Автор: " + parts[0] + ", Рік: " + parts[2]);
                found = true;
            }
        }
        reader.close();

        if (!found) {
            System.out.println("Книг з такою назвою немає.");
        }
    }

    // 5. Редагування записів
    public static void editBooks(String booksFile) throws IOException {
        File file = new File(booksFile);
        if (!file.exists()) {
            System.out.println("Файл не існує!");
            return;
        }

        ArrayList<Book> books = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(booksFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            books.add(new Book(parts[0], parts[1], parts[2]));
        }
        reader.close();

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }

        System.out.print("Введіть номер книги для редагування: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index < 0 || index >= books.size()) {
            System.out.println("Невірний номер!");
            return;
        }

        Book book = books.get(index);
        System.out.print("Новий автор (залиште порожнім для " + book.author + "): ");
        String author = scanner.nextLine();
        if (!author.isEmpty()) book.author = author;

        System.out.print("Нова назва (залиште порожнім для " + book.title + "): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) book.title = title;

        System.out.print("Новий рік (залиште порожнім для " + book.year + "): ");
        String year = scanner.nextLine();
        if (!year.isEmpty()) book.year = year;

        BufferedWriter writer = new BufferedWriter(new FileWriter(booksFile));
        for (Book b : books) {
            writer.write(b.author + "," + b.title + "," + b.year);
            writer.newLine();
        }
        writer.close();
        System.out.println("Редагування завершено.");
    }

    // Головне меню
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String booksFile = "books.txt";

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Створити файл книг");
            System.out.println("2. Додати книгу");
            System.out.println("3. Переглянути файл");
            System.out.println("4. Знайти книгу за назвою");
            System.out.println("5. Редагувати книгу");
            System.out.println("6. Вихід");
            System.out.print("Виберіть дію: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> writeBooks(booksFile);
                case "2" -> appendBooks(booksFile);
                case "3" -> viewBooks(booksFile);
                case "4" -> findBook(booksFile);
                case "5" -> editBooks(booksFile);
                case "6" -> {
                    System.out.println("Вихід...");
                    return;
                }
                default -> System.out.println("Невірний вибір!");
            }
        }
    }
}
