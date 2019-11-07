package de.szut.simNil.binaryMaple.gui;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import de.szut.simNil.binaryMaple.AbstractNode;
import de.szut.simNil.binaryMaple.BinarySearchTreeException;
import de.szut.simNil.binaryMaple.InterfaceBinarySearchTree;
import de.szut.simNil.binaryMaple.Order;
import de.szut.simNil.binaryMaple.avl.AVLBinarySearchTree;
import de.szut.simNil.binaryMaple.rb.RedBlackBinarySearchTree;
import de.szut.simNil.binaryMaple.standard.StandardBinarySearchTree;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.FormatExtensionFilter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class AbstractController<T extends Comparable<T>> implements Initializable {

    /**
     * File extensions for saving and loading the tree
     */
    private static final FileChooser.ExtensionFilter[] TREE_EXTENSIONS = new FileChooser.ExtensionFilter[]{
        new FileChooser.ExtensionFilter("XML", "*.xml")
    };
    /**
     * The image file extensions mapped with the format supported by graphviz
     */
    private static final Map<FileChooser.ExtensionFilter, Format> GRAPHVIZ_EXTENSIONS = FormatExtensionFilter.getFilters();

    private static final String standardTreeMessage = "Dieser Baum ist einfach gestrickt, kann aber ganz schön listig werden.";
    private static final Image standardImage = new Image(AbstractController.class.getResource("normal.png").toString());
    private static final String redBlackTreeMessage = "Als dieser Baum noch jung war, konnte er sich nie für eine Farbe entscheiden, sodass am Ende alle Knoten dunkelrot waren.";
    private static final Image redBlackImage = new Image(AbstractController.class.getResource("redblack.png").toString());
    private static final String avlTreeMessage = "Von seinen Freunden wird er liebevoll ApVeL-Baum genannt.";
    private static final Image avlImage = new Image(AbstractController.class.getResource("avl.png").toString());

    /**
     * all implemented key shortcuts
     */
    private static final KeyCombination[] combinations = new KeyCombination[]{
        new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN), // Add
        new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN), // Delete
        new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN), // Search
        new KeyCodeCombination(KeyCode.K, KeyCombination.ALT_DOWN), // Collapse
        new KeyCodeCombination(KeyCode.Z, KeyCombination.ALT_DOWN), // Random
        new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN), // Standard
        new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN), // Red Black
        new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN), // AVL
        new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN), // Terminal
        new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN), // Leafs
        new KeyCodeCombination(KeyCode.G, KeyCombination.ALT_DOWN), // Grass
    };

    /**
     * out tree
     */
    protected InterfaceBinarySearchTree<T> tree = new StandardBinarySearchTree<>();

    /**
     * logger for logging exceptions
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * For creating the graphviz
     */
    private TreeVisualizer<T> visualizer = new TreeVisualizer<>(this.tree);

    /**
     * for getting the stage
     */
    @Setter
    private Main main;
    @FXML
    private RadioButton standardTree;
    @FXML
    private RadioButton redBlackTree;
    @FXML
    private RadioButton avlTree;
    @FXML
    private CheckBox showNullCheckBox;
    @FXML
    private CheckBox showLeafsGreenCheckBox;
    @FXML
    private CheckBox showGrassCheckBox;
    @FXML
    private TextField valueField;
    @FXML
    private ComboBox<AbstractController> valueTypes;
    @FXML
    private ImageView graphvizImageView;
    @FXML
    private ProgressIndicator showProgress;
    @FXML
    private Label treeMessage;
    @FXML
    private ImageView treeImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.valueTypes.setItems(this.main.getControllers());
        this.valueTypes.getSelectionModel().select(this);
        this.valueTypes.valueProperty().addListener((observableValue, abstractControllerSingleSelectionModel, t1) -> {
            try {
                this.main.changeController(t1);
            } catch (IOException e) {
                this.logger.error("Failed changing the controller", e);
                System.exit(-1);
            }
        });

        this.standardTree.setSelected(this.tree instanceof StandardBinarySearchTree);
        this.redBlackTree.setSelected(this.tree instanceof RedBlackBinarySearchTree);
        this.avlTree.setSelected(this.tree instanceof AVLBinarySearchTree);

        updateGraphvizImage();
        this.treeMessage.setText(standardTreeMessage);
        this.treeImage.setImage(standardImage);

        this.standardTree.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                this.treeMessage.setText(standardTreeMessage);
                this.treeImage.setImage(standardImage);

                List<T> values = this.tree.traverse(Order.PREORDER);
                this.tree = new StandardBinarySearchTree<>();
                try {
                    addValuesToTree(values);
                    updateGraphvizImage();
                } catch (BinarySearchTreeException e) {
                    warn("Fehler beim Übertragen der Werte", "Es konnten nicht alle Werte zum Standard Baum übertragen werden", e);
                }
            }
        });

        this.redBlackTree.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                this.treeMessage.setText(redBlackTreeMessage);
                this.treeImage.setImage(redBlackImage);

                List<T> values = this.tree.traverse(Order.PREORDER);
                this.tree = new RedBlackBinarySearchTree<>();
                try {
                    addValuesToTree(values);
                    updateGraphvizImage();
                } catch (BinarySearchTreeException e) {
                    warn("Fehler beim Übertragen der Werte", "Es konnten nicht alle Werte zum AVL Baum übertragen werden", e);
                }
            }
        });

        this.avlTree.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                this.treeMessage.setText(avlTreeMessage);
                this.treeImage.setImage(avlImage);

                List<T> values = this.tree.traverse(Order.PREORDER);
                this.tree = new AVLBinarySearchTree<>();
                try {
                    addValuesToTree(values);
                    updateGraphvizImage();
                } catch (BinarySearchTreeException e) {
                    this.showProgress.setProgress(0);
                    warn("Fehler beim Übertragen der Werte", "Es konnten nicht alle Werte zum Standard Baum übertraben werden", e);
                }
            }
        });

        this.showNullCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            this.visualizer.setShowNullNodes(t1);
            updateGraphvizImage();
        });

        this.showGrassCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            this.visualizer.setWithGrass(t1);
            updateGraphvizImage();
        });

        this.showLeafsGreenCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            this.visualizer.setHighlightLeafs(t1);
            updateGraphvizImage();
        });

        this.main.getStage().addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (combinations[0].match(keyEvent)) {
                addValue();
                keyEvent.consume();
            } else if (combinations[1].match(keyEvent)) {
                delValue();
                keyEvent.consume();
            } else if (combinations[2].match(keyEvent)) {
                searchValue();
                keyEvent.consume();
            } else if (combinations[3].match(keyEvent)) {
                collapseAtValue();
                keyEvent.consume();
            } else if (combinations[4].match(keyEvent)) {
                generateValueTimes();
                keyEvent.consume();
            } else if (combinations[5].match(keyEvent)) {
                standardTree.setSelected(true);
                keyEvent.consume();
            } else if (combinations[6].match(keyEvent)) {
                redBlackTree.setSelected(true);
                keyEvent.consume();
            } else if (combinations[7].match(keyEvent)) {
                avlTree.setSelected(true);
                keyEvent.consume();
            } else if (combinations[8].match(keyEvent)) {
                showNullCheckBox.setSelected(!showNullCheckBox.selectedProperty().getValue());
                keyEvent.consume();
            } else if (combinations[9].match(keyEvent)) {
                showLeafsGreenCheckBox.setSelected(!showLeafsGreenCheckBox.selectedProperty().getValue());
                keyEvent.consume();
            } else if (combinations[10].match(keyEvent)) {
                showGrassCheckBox.setSelected(!showGrassCheckBox.selectedProperty().getValue());
                keyEvent.consume();
            }
        });
    }

    /**
     * adds a values to the tree
     *
     * @param Ts all values
     * @throws BinarySearchTreeException if a value is already present
     */
    private void addValuesToTree(List<T> Ts) throws BinarySearchTreeException {
        for (T i : Ts) {
            this.tree.addValue(i);
        }
    }

    /**
     * creates a new graphviz
     */
    private void updateGraphvizImage() {
        new Thread(() -> {
            this.visualizer.setTree(this.tree);
            this.visualizer.createGraphviz();
            try {
                Image graphviz = this.visualizer.getGraphvizImage();
                Platform.runLater(() -> {
                    this.graphvizImageView.setImage(this.tree.getNodeCount() > 0 ? graphviz : null);
                    this.showProgress.setProgress(1);
                });
            } catch (OutOfMemoryError e) {
                Platform.runLater(() -> {
                    this.graphvizImageView.setImage(null);
                    warn("Fehler beim Erstellen des Bildes!", "Es scheint, als hätte Java zu wenig Arbeitsspeicher zur Verfügung um das Graphviz zu erstellen.", e);
                });
            }
        }).start();
    }

    /**
     * loads a tree from a file
     */
    @SuppressWarnings("unchecked")
    @FXML
    private void loadTree() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().setAll(TREE_EXTENSIONS);
        File file = chooser.showOpenDialog(this.main.getStage());
        if (file != null) {
            this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    InterfaceBinarySearchTree<T> loadedTree = null;
                    AbstractController<T> newController = null;
                    if (chooser.getSelectedExtensionFilter().equals(TREE_EXTENSIONS[0])) {
                        XStream xStream = new XStream(new StaxDriver());
                        loadedTree = (InterfaceBinarySearchTree<T>) xStream.fromXML(reader);
                    }
                    reader.close();
                    if (loadedTree != null && loadedTree.getRoot().getValue() != null) {
                        for (AbstractController<T> controller : this.main.getControllers()) {
                            if (loadedTree.getRoot().getValue().getClass().equals(controller.getType())) {
                                newController = controller;
                                break;
                            }
                        }
                    } else {
                        newController = this;
                    }
                    if (newController == null) {
                        throw new RuntimeException("No Controller found for this tree");
                    }
                    newController.tree = loadedTree;
                    AbstractController<T> finalNewController = newController;
                    Platform.runLater(() -> {
                        try {
                            this.main.changeController(finalNewController);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    Platform.runLater(() -> warn("Fehler beim Lesen der Datei", String.format("Die Datei %s konnte nicht eingelesen werden", file), e));
                }
            }).start();
        }
    }

    /**
     * saves the tree as file
     */
    @FXML
    private void saveTree() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().setAll(TREE_EXTENSIONS);
        File file = chooser.showSaveDialog(this.main.getStage());
        if (file != null) {
            this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            new Thread(() -> {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    if (chooser.getSelectedExtensionFilter().equals(TREE_EXTENSIONS[0])) {
                        XStream xStream = new XStream(new StaxDriver());
                        xStream.toXML(this.tree, writer);
                    }
                    writer.close();
                } catch (IOException e) {
                    Platform.runLater(() -> warn("Fehler beim Speichern der Datei", String.format("Die Datei %s konnte nicht gespeichert werden", file), e));
                }
                Platform.runLater(() -> this.showProgress.setProgress(1));
            }).start();
        }
    }

    /**
     * saves the graphviz as image
     */
    @FXML
    private void saveImage() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(GRAPHVIZ_EXTENSIONS.keySet());
        File file = chooser.showSaveDialog(this.main.getStage());
        if (file != null) {
            try {
                this.visualizer.saveGraphviz(file, GRAPHVIZ_EXTENSIONS.get(chooser.getSelectedExtensionFilter()));
            } catch (IOException e) {
                warn("Fehler beim Speichern des Bildes", String.format("Das Bild konnte nicht als Datei %s  espeichert werden", file), e);
            }
        }
    }

    /**
     * @param input input from the text field
     * @return parsed input
     */
    abstract T getInput(String input);

    /**
     * adds a value to the tree
     */
    @FXML
    private void addValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        try {
            this.tree.addValue(getInput(this.valueField.getText()));
            updateGraphvizImage();
        } catch (NumberFormatException e) {
            warn("Falsche Eingabe", "Es scheint, als hätten Sie keine Zahl eingegeben", e);
        } catch (BinarySearchTreeException e) {
            warn("Falsche Eingabe", "Es ist ein Fehler beim Hinzufügen des Wertes aufgetreten", e);
        }
    }

    /**
     * removes a value from the tree
     */
    @FXML
    private void delValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        try {
            this.tree.delValue(getInput(this.valueField.getText()));
            updateGraphvizImage();
        } catch (NumberFormatException e) {
            warn("Falsche Eingabe", "Es scheint, als hätten Sie keine Zahl eingegeben", e);
        } catch (BinarySearchTreeException e) {
            warn("Falsche Eingabe", "Es ist ein Fehler beim Löschen des Wertes aufgetreten", e);
        }
    }

    /**
     * searches a value and highlights it
     */
    @FXML
    private void searchValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        if (this.valueField.getText().isEmpty()) {
            this.visualizer.setHighlightedNode(null);
        } else {
            try {
                this.visualizer.setHighlightedNode(this.tree.getNodeWithValue(getInput(this.valueField.getText())));
            } catch (NumberFormatException e) {
                warn("Falsche Eingabe", "Es scheint, als hätten Sie keine Zahl eingegeben", e);
            }
        }
        updateGraphvizImage();
    }

    /**
     * collapse at a specific node
     */
    @FXML
    private void collapseAtValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        try {
            AbstractNode<T> collapseNode = this.tree.getNodeWithValue(getInput(this.valueField.getText()));
            if (this.visualizer.isCollapsed(collapseNode)) {
                this.visualizer.removeCollapseNode(collapseNode);
            } else {
                this.visualizer.addCollapseNode(collapseNode);
            }
            updateGraphvizImage();
        } catch (NumberFormatException e) {
            warn("Falsche Eingabe", "Es scheint, als hätten Sie keine Zahl eingegeben", e);
        }
    }

    /**
     * @return a random value
     */
    abstract T getRandomValue();

    /**
     * generates random values input times
     */
    @FXML
    private void generateValueTimes() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        new Thread(() -> {
            try {
                for (int i = 0; i < Integer.parseInt(this.valueField.getText()); i++) {
                    try {
                        this.tree.addValue(getRandomValue());
                    } catch (BinarySearchTreeException e) {
                        i--;
                    }
                }
                updateGraphvizImage();
            } catch (NumberFormatException e) {
                Platform.runLater(() -> warn("Falsche Eingabe", "Es scheint, als hätten Sie keine Zahl eingegeben", e));
            }
        }).start();
    }

    /**
     * required for the combobox
     *
     * @return controller name
     */
    @Override
    public abstract String toString();

    public abstract Class<?> getType();

    /**
     * shows a warning alert and logs it
     *
     * @param heading alert heading, not logged
     * @param text    the content
     * @param e       an exception to log
     */
    private void warn(String heading, String text, Throwable e) {
        this.showProgress.setProgress(0);
        this.logger.warn(text, e);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(heading);
        alert.setContentText(String.format("%s\n\n\n%s", text, e.toString()));
        alert.show();
    }
}
