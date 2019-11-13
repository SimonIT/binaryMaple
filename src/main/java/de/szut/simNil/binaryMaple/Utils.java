package de.szut.simNil.binaryMaple;

import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static String makeBeautifulMultiline(String inline) {
        return makeMultiline(inline, (int) Math.ceil(Math.sqrt(inline.codePointCount(0, inline.length()) / 0.2)));
    }

    /**
     * Makes an inline string to multiline text with an equal char count in one line and line count
     * It uses <i>LF</i> as line separator
     *
     * @param inline       string
     * @param charsPerLine how much chars should be in one line
     * @return multiline string
     */
    public static String makeMultiline(String inline, int charsPerLine) {
        int len = inline.codePointCount(0, inline.length());
        int lines = (int) Math.ceil(len / (double) charsPerLine);
        StringBuilder builder = new StringBuilder(len + lines - 1);
        for (int i = 0; i < len; i += charsPerLine) {
            if (i + charsPerLine < len) {
                builder.append(inline,inline.offsetByCodePoints(0, i), inline.offsetByCodePoints(0, i+charsPerLine));
                builder.append('\n');
            } else {
                builder.append(inline,inline.offsetByCodePoints(0, i), inline.offsetByCodePoints(0, len));
            }
        }
        return builder.toString();
    }
}
