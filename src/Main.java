import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число: ");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число: ");
        int secondNumber = new Scanner(System.in).nextInt();
        int sum = firstNumber + secondNumber;
        int diff = firstNumber - secondNumber;
        int multiplication = firstNumber * secondNumber;
        double division = (double) firstNumber / secondNumber;
        System.out.println("Сумма = "+ sum);
        System.out.println("Разность = " + diff);
        System.out.println("Произведение = " + multiplication);
        System.out.println("Частное = " + division);
    }
}
