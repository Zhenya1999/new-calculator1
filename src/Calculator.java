import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(calc(input));
    }

    public static Object calc(String input) {
        String[] arr = input.replaceAll(" ", "").split("[+\\-*/]");

        if (arr.length != 2) {
            throw new IllegalArgumentException("Должно быть только 2 операнда и одна операция, пример: 2 + 2");
        }

        String n1 = arr[0];
        String n2 = arr[1];

        boolean numsIsRoman = isRomanAndValidateInput(n1, n2);

        int num1;
        int num2;

        if (numsIsRoman) {
            num1 = romanToArabic(n1);
            num2 = romanToArabic(n2);
        } else {
            num1 = Integer.parseInt(n1);
            num2 = Integer.parseInt(n2);
            validateInput(num1, num2);
        }

        int result = getResult(input, num1, num2);

        if (numsIsRoman) {
            return arabicToRoman(result);
        }

        return result;
    }

    private static int getResult(String input, Integer num1, Integer num2) {
        int result;

        if (input.contains("+")) {
            result = num1 + num2;
        } else if (input.contains("-")) {
            result = num1 - num2;
        } else if (input.contains("*")) {
            result = num1 * num2;
        } else if (input.contains("/")) {
            result = num1 / num2;
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }

    private static void validateInput(Integer num1, Integer num2) {
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Операнды должны быть в диапазоне от 1 до 10");
        }
    }

    private static boolean isRomanAndValidateInput(String roman1, String roman2) {
        if (roman1.matches("^I{0,3}|IV|V?I{0,3}|IX|X$") && roman2.matches("^I{0,3}|IV|V?I{0,3}|IX|X$")) {
            return true;
        } else if (!roman1.matches("^I{0,3}|IV|V?I{0,3}|IX|X$") && !roman2.matches("^I{0,3}|IV|V?I{0,3}|IX|X$")) {
            return false;
        }
        throw new IllegalArgumentException("Оба операнда должны быть римскими или арабскими в диапазоне от 1 до 10(I-X)");
    }

    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanValues.get(roman.charAt(i));

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }

    public static String arabicToRoman(Integer number) {
        String[] romanNumbers = {"NULL", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] romanNumbersDec = {"NULL", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

        if (number <= 0) {
            throw new IllegalArgumentException("Римская цифра должна быть больше нуля");
        } else if (number < 10) {
            return romanNumbers[number];
        } else if (number == 10) {
            return romanNumbers[(number)];
        } else if (number % 10 == 0) {
            return romanNumbersDec[(number / 10)];
        }
        return romanNumbersDec[(number / 10)] + romanNumbers[number % 10];
    }
}
