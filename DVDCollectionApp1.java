import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

public class DVDCollectionApp1 extends Application {
    private DVDCollection model;
    private DVDCollectionAppView1 view;

    public DVDCollectionApp1() {
        model = DVDCollection.example1();
    }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();

        // Create the view
        view = new DVDCollectionAppView1();
        view.update(model, 0);

        //Event Handlers for ListViews
        view.getTitleList().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                view.getYearList().getSelectionModel().select(newVal.intValue());
                view.getLengthList().getSelectionModel().select(newVal.intValue());
            }
        });

        view.getYearList().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                view.getTitleList().getSelectionModel().select(newVal.intValue());
                view.getLengthList().getSelectionModel().select(newVal.intValue());
            }
        });

        view.getLengthList().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                view.getTitleList().getSelectionModel().select(newVal.intValue());
                view.getYearList().getSelectionModel().select(newVal.intValue());
            }
        });

        // Event Handler for Add Button
        view.getButtonPane().getAddButton().setOnAction(e -> {
            String title = JOptionPane.showInputDialog("Please enter a title:");
            int year = Integer.parseInt(JOptionPane.showInputDialog("Please enter a year:"));
            int length = Integer.parseInt(JOptionPane.showInputDialog("Please enter a duration:"));
            model.add(new DVD(title, year, length));
            view.update(model, -1);
        });

        // Event Handler for Delete Button
        view.getButtonPane().getDeleteButton().setOnAction(e -> {
            String selectedDVD = view.getList().getSelectionModel().getSelectedItem();
            if (selectedDVD != null) {
                model.remove(selectedDVD);
                view.update(model, -1);
            }
        });

        aPane.getChildren().addAll(view);

        primaryStage.setTitle("My DVD Collection");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public interface DVDView {
        public void update(DVDCollection model, int selectedDVD);
    }
}
