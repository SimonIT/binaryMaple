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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class AbstractController<T extends Comparable<T>> implements Initializable {

    private static final FileChooser.ExtensionFilter[] TREE_EXTENSION = new FileChooser.ExtensionFilter[]{
        new FileChooser.ExtensionFilter("XML", "*.xml")
    };
    private static final Map<FileChooser.ExtensionFilter, Format> GRAPHVIZ_EXTENSIONS = FormatExtensionFilter.getFilters();

    private static final String standardTreeMessage = "Dieser Baum ist einfach gestrickt, kann aber ganz schön listig werden.";
    private static final Image standardImage = new Image(AbstractController.class.getResource("normal.png").toString());
    private static final String redBlackTreeMessage = "Als dieser Baum noch jung war, konnte er sich nie für eine Farbe entscheiden, sodass am Ende alle Knoten dunkelrot waren.";
    private static final Image redBlackImage = new Image(AbstractController.class.getResource("redblack.png").toString());
    private static final String avlTreeMessage = "Von seinen Freunden wird er liebevoll ApVeL-Baum genannt.";
    private static final Image avlImage = new Image(AbstractController.class.getResource("avl.png").toString());
    protected InterfaceBinarySearchTree<T> tree;
    @FXML
    RadioButton standardTree;
    @FXML
    RadioButton redBlackTree;
    @FXML
    RadioButton avlTree;
    @FXML
    CheckBox showNullCheckBox;
    @FXML
    CheckBox showLeafsGreenCheckBox;
    @FXML
    CheckBox showGrassCheckBox;
    @Setter
    Main main;
    @FXML
    private TextField valueField;
    @FXML
    private ComboBox<AbstractController> valueTypes;
    @Setter
    private Stage stage;
    private TreeVisualizer<T> visualizer;
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
        this.tree = new StandardBinarySearchTree<>();

        this.visualizer = new TreeVisualizer<>(this.tree);

        this.valueTypes.setItems(main.controllers);
        this.valueTypes.getSelectionModel().select(this);
        this.valueTypes.valueProperty().addListener((observableValue, abstractControllerSingleSelectionModel, t1) -> {
            try {
                main.changeController(t1);
            } catch (IOException e) {
                System.exit(-1);
            }
        });

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
                } catch (BinarySearchTreeException e) {
                    e.printStackTrace();
                }
                this.visualizer.setTree(this.tree);
                updateGraphvizImage();
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
                    this.showProgress.setProgress(0);
                    e.printStackTrace();
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
                    e.printStackTrace();
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
    }

    private void addValuesToTree(List<T> Ts) throws BinarySearchTreeException {
        for (T i : Ts) {
            this.tree.addValue(i);
        }
    }

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
                    this.showProgress.setProgress(0);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Fehler beim Erstellen des Bildes!");
                    alert.setContentText("Es scheint, als hätte Java zu wenig Arbeitsspeicher zur Verfügung um das Graphviz zu erstellen.");
                    alert.show();
                });
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    public void loadTree() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().setAll(TREE_EXTENSION);
        File file = chooser.showOpenDialog(this.stage);
        if (file != null) {
            this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    if (chooser.getSelectedExtensionFilter().equals(TREE_EXTENSION[0])) {
                        XStream xStream = new XStream(new StaxDriver());
                        this.tree = (InterfaceBinarySearchTree<T>) xStream.fromXML(reader);
                    }
                    reader.close();
                    this.standardTree.setSelected(this.tree instanceof StandardBinarySearchTree);
                    this.redBlackTree.setSelected(this.tree instanceof RedBlackBinarySearchTree);
                    this.avlTree.setSelected(this.tree instanceof AVLBinarySearchTree);
                    updateGraphvizImage();
                } catch (IOException e) {
                    Platform.runLater(() -> this.showProgress.setProgress(0));
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void saveTree() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().setAll(TREE_EXTENSION);
        File file = chooser.showSaveDialog(this.stage);
        if (file != null) {
            this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            new Thread(() -> {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    if (chooser.getSelectedExtensionFilter().equals(TREE_EXTENSION[0])) {
                        XStream xStream = new XStream(new StaxDriver());
                        xStream.toXML(this.tree, writer);
                    }
                    writer.close();
                } catch (IOException e) {
                    Platform.runLater(() -> this.showProgress.setProgress(0));
                    e.printStackTrace();
                }
                Platform.runLater(() -> this.showProgress.setProgress(1));
            }).start();
        }
    }

    public void saveImage() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(GRAPHVIZ_EXTENSIONS.keySet());
        File file = chooser.showSaveDialog(this.stage);
        if (file != null) {
            try {
                this.visualizer.saveGraphviz(file, GRAPHVIZ_EXTENSIONS.get(chooser.getSelectedExtensionFilter()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    abstract T getInput(String input);

    public void addValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        try {
            this.tree.addValue(getInput(this.valueField.getText()));
            updateGraphvizImage();
        } catch (BinarySearchTreeException e) {
            System.out.println(e.getMessage());
            this.showProgress.setProgress(0);
        }
    }

    public void delValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        try {
            this.tree.delValue(getInput(this.valueField.getText()));
            updateGraphvizImage();
        } catch (BinarySearchTreeException e) {
            System.out.println(e.getMessage());
            this.showProgress.setProgress(0);
        }
    }

    public void searchValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        if (this.valueField.getText().isEmpty()) {
            this.visualizer.setHighlightedNode(null);
        } else {
            this.visualizer.setHighlightedNode(this.tree.getNodeWithValue(getInput(this.valueField.getText())));
        }
        updateGraphvizImage();
    }

    public void collapseAtValue() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        AbstractNode<T> collapseNode = this.tree.getNodeWithValue(getInput(this.valueField.getText()));
        if (this.visualizer.isCollapsed(collapseNode)) {
            this.visualizer.removeCollapseNode(collapseNode);
        } else {
            this.visualizer.addCollapseNode(collapseNode);
        }
        updateGraphvizImage();
    }

    abstract T getRandomValue();

    public void generateValueTimes() {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        new Thread(() -> {
            for (int i = 0; i < Integer.parseInt(this.valueField.getText()); i++) {
                try {
                    this.tree.addValue(getRandomValue());
                } catch (BinarySearchTreeException e) {
                    i--;
                }
            }
            updateGraphvizImage();
        }).start();
    }

    @Override
    public abstract String toString();
}
