package Lab1;

/**
 * Лабораторные работа N1 по дисциплине "кроссплатформенные приложения"
 *
 * @author maxim kozlov bst - 1902
 * @version dated 01.03.2021
 */

public class Primes {
    public static void main(String[] args){
        for(int i = 2;i<100;i++)
            if (isPrime(i)) System.out.print(i + " ");
    }

    //Проверяет являеться ли число простым
    public static boolean isPrime(int n) {
        for (int i = 2; i < n ; i++)
            if (n % i == 0) return false;
        return true;
    }
}
