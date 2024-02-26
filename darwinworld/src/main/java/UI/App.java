package UI;

import Model.OptionReader;
import Simulations.Settings;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class App extends Application {
    private final BorderPane border = new BorderPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(new FileInputStream(String.valueOf(Paths.get(System.getProperty("user.dir"),"darwinworld","src","main","resources","world.png")))));
        primaryStage.setTitle("Darwin's World");
        primaryStage.alwaysOnTopProperty();
        primaryStage.setScene(new Scene(border, 880, 460));
        primaryStage.show();
    }

    private void initBorder() {
        Label tittle = new Label("Press Start to config your rules!");
        tittle.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 26 pt; -fx-text-fill: #b0b0b0; -fx-background-color: rgba(9,1,1,0.84);");
        border.setTop(tittle);
        BorderPane.setAlignment(tittle, Pos.CENTER);
        BorderPane.setMargin(tittle, new Insets(20, 0, 20, 0));
    }

    private void initGetDate() throws FileNotFoundException {
        ChoiceBox<String> confVariant = new ChoiceBox<>();
        confVariant.getItems().add("My Configuration");
        confVariant.getItems().addAll(OptionReader.names());
        confVariant.setValue("My Configuration");

        Button getParameter = new Button("CONFIRM");
        String buttonStyle = "-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: #b0b0b0; -fx-background-color: rgba(9,1,1,0.84);";
        getParameter.setStyle(buttonStyle);

        Button exitButton = new Button("EXIT");
        exitButton.setStyle(buttonStyle);

        Label choiceLabel = new Label("Your choice: ");
        choiceLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: #b0b0b0; -fx-background-color: rgba(9,1,1,0.84);");

        HBox inputList = new HBox(10, choiceLabel, confVariant);
        inputList.setAlignment(Pos.CENTER);

        HBox confirm = new HBox(50, getParameter, exitButton);
        BorderPane.setMargin(confirm, new Insets(10, 0, 60, 0));

        border.setCenter(inputList);
        border.setBottom(confirm);
        confirm.setAlignment(Pos.BOTTOM_CENTER);
        border.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        getParameter.setOnAction(action -> {
            try {
                String items = confVariant.getValue();
                if (items.equals("My Configuration")) {
                    new GetDataStage();
                } else {
                    String[] headers = OptionReader.names();
                    for (String name : headers) {
                        if (items.equals(name)) {
                            String[] parameters = OptionReader.find(name);
                            if (parameters != null) {
                                Settings settings = new Settings(name, parameters);
                                new StartApp(settings);
                            } else {
                                throw new Exception("wrong configuration");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        exitButton.setOnAction(action -> System.exit(0));
    }

    @Override
    public void init() throws IOException {
        initBorder();
        initGetDate();
    }
}

