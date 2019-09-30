package guru.nidi.graphviz.engine;

import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FormatExtensionFilter {

    @NotNull
    public static FileChooser.ExtensionFilter asFilter(@NotNull Format format) {
        return new FileChooser.ExtensionFilter(format.name(), "*." + format.fileExtension);
    }

    @NotNull
    public static Map<FileChooser.ExtensionFilter, Format> getFilters() {
        Map<FileChooser.ExtensionFilter, Format> filters = new HashMap<>(Format.values().length);
        for (Format format : Format.values())
            filters.put(asFilter(format), format);
        return filters;
    }
}
