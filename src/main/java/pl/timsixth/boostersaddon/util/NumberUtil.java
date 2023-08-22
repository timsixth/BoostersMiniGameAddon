package pl.timsixth.boostersaddon.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtil {

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
