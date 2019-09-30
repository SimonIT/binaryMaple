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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final FileChooser.ExtensionFilter[] TREE_EXTENSION = new FileChooser.ExtensionFilter[]{
        new FileChooser.ExtensionFilter("XML", "*.xml")
    };
    private static final Map<FileChooser.ExtensionFilter, Format> GRAPHVIZ_EXTENSIONS = FormatExtensionFilter.getFilters();

    @Setter
    private Stage stage;
    private InterfaceBinarySearchTree<Integer> tree;
    private TreeVisualizer<Integer> visualizer;

    @FXML
    private CheckBox showNullCheckBox;
    @FXML
    private ImageView graphvizImageView;
    @FXML
    private TextField valueField;
    @FXML
    private ProgressIndicator showProgress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tree = new StandardBinarySearchTree<>();

        this.visualizer = new TreeVisualizer<>(this.tree);

        updateGraphvizImage();
    }

    private void addValuesToTree(List<Integer> integers) throws BinarySearchTreeException {
        for (int i : integers) {
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

    public void loadTree(ActionEvent actionEvent) {
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
                        this.tree = (InterfaceBinarySearchTree<Integer>) xStream.fromXML(reader);
                    }
                    reader.close();
                    updateGraphvizImage();
                } catch (IOException e) {
                    Platform.runLater(() -> this.showProgress.setProgress(0));
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void saveTree(ActionEvent actionEvent) {
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

    public void saveImage(ActionEvent actionEvent) {
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

    public void convertToStandardTree(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        List<Integer> values = this.tree.traverse(Order.PREORDER);
        this.tree = new StandardBinarySearchTree<>();
        try {
            addValuesToTree(values);
        } catch (BinarySearchTreeException e) {
            e.printStackTrace();
        }
        this.visualizer.setTree(this.tree);
        updateGraphvizImage();
    }

    public void convertToRedBlackTree(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        List<Integer> values = this.tree.traverse(Order.PREORDER);
        this.tree = new RedBlackBinarySearchTree<>();
        try {
            addValuesToTree(values);
            updateGraphvizImage();
        } catch (BinarySearchTreeException e) {
            this.showProgress.setProgress(0);
            e.printStackTrace();
        }
    }

    public void convertToAvlTree(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        List<Integer> values = this.tree.traverse(Order.PREORDER);
        this.tree = new AVLBinarySearchTree<>();
        try {
            addValuesToTree(values);
            updateGraphvizImage();
        } catch (BinarySearchTreeException e) {
            this.showProgress.setProgress(0);
            e.printStackTrace();
        }
    }

    public void showNullNodes(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        this.visualizer.setShowNullNodes(this.showNullCheckBox.isSelected());
        updateGraphvizImage();
    }

    public void addValue(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        try {
            this.tree.addValue(Integer.valueOf(this.valueField.getText()));
            updateGraphvizImage();
        } catch (BinarySearchTreeException e) {
            System.out.println(e.getMessage());
            this.showProgress.setProgress(0);
        }
    }

    public void delValue(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        try {
            this.tree.delValue(Integer.valueOf(this.valueField.getText()));
            updateGraphvizImage();
        } catch (BinarySearchTreeException e) {
            System.out.println(e.getMessage());
            this.showProgress.setProgress(0);
        }
    }

    public void searchValue(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        if (this.valueField.getText().isEmpty()) {
            this.visualizer.setHighlightedNode(null);
        } else {
            this.visualizer.setHighlightedNode(this.tree.getNodeWithValue(Integer.valueOf(this.valueField.getText())));
        }
        updateGraphvizImage();
    }

    public void collapseAtValue(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        AbstractNode<Integer> collapseNode = this.tree.getNodeWithValue(Integer.valueOf(this.valueField.getText()));
        if (this.visualizer.isCollapsed(collapseNode)) {
            this.visualizer.removeCollapseNode(collapseNode);
        } else {
            this.visualizer.addCollapseNode(collapseNode);
        }
        updateGraphvizImage();
    }

    public void generateValueTimes(ActionEvent actionEvent) {
        this.showProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < Integer.parseInt(this.valueField.getText()); i++) {
                try {
                    this.tree.addValue(random.nextInt(Math.max(20, 4 * this.tree.getNodeCount())) - 2 * this.tree.getNodeCount());
                } catch (BinarySearchTreeException e) {
                    i--;
                }
            }
            updateGraphvizImage();
        }).start();
    }
}
