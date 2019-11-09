package de.szut.simNil.binaryMaple;

import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static String makeBeautifulMultiline(String inline) {
        return makeMultiline(inline, (int) Math.ceil(Math.sqrt(inline.length() / 0.2)));
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
        int lines = (int) Math.ceil(inline.length() / (double) charsPerLine);
        StringBuilder builder = new StringBuilder(inline.length() + lines - 1);
        for (int i = 0; i < inline.length(); i += charsPerLine) {
            if (i + charsPerLine < inline.length()) {
                builder.append(inline, i, i + charsPerLine);
                builder.append(StringUtils.LF);
            } else {
                builder.append(inline, i, inline.length());
            }
        }
        return builder.toString();
    }
}
