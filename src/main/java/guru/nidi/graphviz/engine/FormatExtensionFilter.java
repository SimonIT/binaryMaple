package guru.nidi.graphviz.engine;

import javafx.stage.FileChooser;
import org.apache.commons.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Needs to be in {@link guru.nidi.graphviz.engine} because {@link guru.nidi.graphviz.engine.Format#fileExtension} is package-private and we need it for the {@link javafx.stage.FileChooser.ExtensionFilter}
 */
public class FormatExtensionFilter {

    /**
     * @param format a graphviz file format {@link guru.nidi.graphviz.engine.Format}
     * @return javafx ExtensionFilter {@link javafx.stage.FileChooser.ExtensionFilter}
     */
    @NotNull
    public static FileChooser.ExtensionFilter formatAsFilter(@NotNull Format format) {
        return new FileChooser.ExtensionFilter(WordUtils.capitalize(format.name().toLowerCase().replace("_", " ")), String.format("*.%s", format.fileExtension));
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
