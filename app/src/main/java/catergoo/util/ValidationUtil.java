package catergoo.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    // constants
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_DIGIT_COUNT = 3;
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return username.length() >= MIN_USERNAME_LENGTH;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }

        long digitCount = password.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .filter(s -> DIGIT_PATTERN.matcher(s).matches())
                .count();

        return digitCount >= MIN_DIGIT_COUNT;
    }

    public static boolean isValidQuantity(int quantity, int minOrder) {
        return quantity >= minOrder;
    }

    public static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty() && address.length() >= 10;
    }

    public static String getUsernameErrorMessage() {
        return "Username harus minimal " + MIN_USERNAME_LENGTH + " karakter";
    }

    public static String getPasswordErrorMessage() {
        return "Password harus minimal " + MIN_PASSWORD_LENGTH + " karakter dan mengandung minimal " + MIN_DIGIT_COUNT
                + " angka";
    }
}