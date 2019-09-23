package guru.nidi.graphviz.engine;

import javafx.stage.FileChooser;

import java.util.HashMap;
import java.util.Map;

public class FormatExtensionFilter {

    public static FileChooser.ExtensionFilter asFilter(Format format) {
        return new FileChooser.ExtensionFilter(format.name(), "*." + format.fileExtension);
    }

    public static Map<FileChooser.ExtensionFilter, Format> getFilters() {
        Map<FileChooser.ExtensionFilter, Format> filters = new HashMap<>(Format.values().length);
        for (Format format : Format.values())
            filters.put(asFilter(format), format);
        return filters;
    }
}
