package Tasks;

public class Task2 {
    /** Task 1 **/


    /** Task 2 **/
    public static int points(int p2, int p3) {
        return p2 * 2 + p3 * 3;
    }

    /** Task 3 **/
    public static int footballPoints(int win, int draw, int lose) {
        return win * 3 + draw;
    }

    /** Task 4 **/
    public static boolean divisibleByFive(int digit) {
        return digit % 5 == 0;
    }

    /** Task 5 **/
    public static boolean and(boolean a, boolean b) {
        return a && b;
    }

    /** Task 6 **/
    public static int howManyWalls(int n, int w, int h) {
        return n / (w * h);
    }

    /** Task 7 **/
    public static int squared(int b) {
        return b * b;
    }

    /** Task 8 **/
    public static boolean profitableGamble(double prob, int prize, int pay) {
        return prob * prize > pay;
    }

    /** Task 9 **/
    public static int frames(int frames, int minute) {
        return frames * minute * 60;
    }

    /** Task 10 **/
    public static int mod(int a, int b) {
        while (a - b > 0) a -= b;
        return a;
    }
}
