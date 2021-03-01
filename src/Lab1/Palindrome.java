package Lab1;

public class Palindrome {
    public static void main(String[] args) {
        args = "java Lab1.Palindrome madam racecar apple kayak song noon".split(" ");
        for (int i = 0; i < args.length; i++) {
            if (isPalindrome(args[i])) {
                System.out.println(args[i]);
            }
        }
    }

    //Возвращает обратную строчку
    public static String reverseString(String text){
        String reverseText = "";
        for(int i = text.length() - 1; i >= 0; i--){
            reverseText += text.charAt(i);
        }

        return reverseText;
    }

    //Проверяет палиндром ли слово
    public static boolean isPalindrome(String text){
        return text.equals(reverseString(text));
    }
}
