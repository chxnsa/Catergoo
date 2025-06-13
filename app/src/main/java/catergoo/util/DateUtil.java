package catergoo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String formatDate(LocalDate date) {
        if (date == null)
            return "";
        return date.format(DATE_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            return "";
        return dateTime.format(DATETIME_FORMATTER);
    }

    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean isTodayOrFuture(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }

    public static boolean isInRange(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null || start == null || end == null)
            return false;
        return !date.isBefore(start) && !date.isAfter(end);
    }

    public static LocalDate getMinBookingDate(int quantity) {
        LocalDate today = LocalDate.now();
        return quantity > 50 ? today.plusDays(2) : today.plusDays(1);
    }

    public static String generateOrderId() {
        return "ORD" + System.currentTimeMillis();
    }
}