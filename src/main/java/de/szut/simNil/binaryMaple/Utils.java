package de.szut.simNil.binaryMaple;

import guru.nidi.graphviz.engine.Format;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * @param format a graphviz file format {@link guru.nidi.graphviz.engine.Format}
     * @return javafx ExtensionFilter {@link javafx.stage.FileChooser.ExtensionFilter}
     */
    @NotNull
    public static FileChooser.ExtensionFilter formatAsFilter(@NotNull Format format) {
        return new FileChooser.ExtensionFilter(format.name().toLowerCase().replace("_", " "), String.format("*.%s", format.fileExtension));
    }

    /**
     * @return a Map with the {@link javafx.stage.FileChooser.ExtensionFilter} corresponding  {@link guru.nidi.graphviz.engine.Format}
     */
    @NotNull
    public static Map<FileChooser.ExtensionFilter, Format> getFilters() {
        Format[] formats = Format.values();
        Map<FileChooser.ExtensionFilter, Format> filters = new HashMap<>(formats.length);
        for (Format format : formats)
            filters.put(formatAsFilter(format), format);
        return filters;
    }
}
