import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        String path;
        while (true) { //while (true) - бесконечный цикл
            System.out.print("Введите путь к файлу: ");
            path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists(); //метод exists проверяет, существует ли файл или папка
            boolean isDirectory = file.isDirectory();//метод isDirectory проверяет, является ли объект папкой
            if (!fileExists) {
                System.out.println("Файл не существует. Попробуйте снова.");
                continue;
            }
            if (isDirectory) {
                System.out.println("Указанный путь ведёт к папке, а не к файлу. Попробуйте снова.");
                continue;
            }
            count++;
            System.out.println("Путь указан верно!");
            System.out.println("Это файл номер " + count);
            break;
        }

        int linesCount = 0;
        int maxLength = 1024;
        int minLength = 0;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                linesCount++;
                if (length > 1024) {
                    throw new RuntimeException("Строка превышает максимальную длину в 1024 символа");
                }
                if (length < maxLength) {
                    maxLength = length;
                }
                if (length > minLength) {
                    minLength = length;
                }
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Строк: " + linesCount);
        System.out.println("Максимальная длина строки: " + minLength);
        System.out.println("Минимальная длина строки: " + maxLength);
    }
}