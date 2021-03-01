package Lab1;

public class Palindrome {
    public static void main(String[] args) {
        String[] words = "java Lab1.Palindrome madam racecar apple kayak song noon".split(" ");
        for (String word : words) {
            if (isPalindrome(word)) {
                System.out.println(word);
            }
        }
    }

    //Возвращает обратную строчку
    public static String reverseString(String text){
        StringBuilder reverseText = new StringBuilder();
        for(int i = text.length() - 1; i >= 0; i--){
            reverseText.append(text.charAt(i));
        }

        return reverseText.toString();
    }

    //Проверяет палиндром ли слово
    public static boolean isPalindrome(String text){
        return text.equals(reverseString(text));
    }
}
