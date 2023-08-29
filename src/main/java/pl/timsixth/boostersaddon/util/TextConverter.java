package pl.timsixth.boostersaddon.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

import static pl.timsixth.boostersaddon.util.NumberUtil.isInt;

@UtilityClass
public class TextConverter {
    /**
     * Converts string to number of minutes,hours or days and time unit.
     * For example 12h means twelve hours
     *
     * @param value string to convert
     * @return array of objects, first element is number, second one is time unit.
     */
    public static Object[] convertToTime(String value) {
        String secondValue = "";
        int firstValue;

        StringBuilder stringBuilder = new StringBuilder();

        for (char sign : value.toCharArray()) {
            if (!isInt(String.valueOf(sign))) {
                secondValue = String.valueOf(sign);
            } else {
                stringBuilder.append(sign);
            }
        }

        firstValue = Integer.parseInt(stringBuilder.toString());

        return new Object[]{firstValue, matchIdentifierLetter(secondValue)};
    }

    private static TimeUnit matchIdentifierLetter(String letter) {
        switch (letter) {
            case "d":
                return TimeUnit.DAYS;
            case "m":
                return TimeUnit.MINUTES;
            case "h":
                return TimeUnit.HOURS;
        }
        return TimeUnit.MINUTES;
    }
}
