package pl.timsixth.boostersaddon;

import org.junit.Test;
import pl.timsixth.boostersaddon.util.TextConverter;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TextConverterTest {

    @Test
    public void shouldSplitText() {
        Object[] convert = TextConverter.convertToTime("10h");

        assertEquals("[10, HOURS]", Arrays.toString(convert));
    }

    @Test
    public void shouldMatchWithPattern() {
        Pattern pattern = Pattern.compile("^[0-9]{1,3}[hdm]");

        String text = "10h";
        String text1 = "1h";
        String text2 = "300m";

        assertTrue(pattern.matcher(text).matches());
        assertTrue(pattern.matcher(text1).matches());
        assertTrue(pattern.matcher(text2).matches());
    }

    @Test
    public void shouldNotMatchWithPattern() {
        Pattern pattern = Pattern.compile("^[0-9]{1,3}[hdm]");

        String text = "10hd";
        String text1 = "100000d";
        String text2 = "0mdbcd";

        assertFalse(pattern.matcher(text).matches());
        assertFalse(pattern.matcher(text1).matches());
        assertFalse(pattern.matcher(text2).matches());
    }
}
