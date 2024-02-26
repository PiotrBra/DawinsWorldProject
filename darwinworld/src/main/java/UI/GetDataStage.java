package UI;

import Model.OptionReader;
import Simulations.Settings;
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
import java.nio.file.Paths;

public class GetDataStage {
    private final Stage stage;
    private final BorderPane borderPane = new BorderPane();

    public GetDataStage() throws FileNotFoundException {
        this.stage = new Stage();
        stage.getIcons().add(new Image(new FileInputStream(String.valueOf(Paths.get(System.getProperty("user.dir"),"darwinworld","src","main","resources","world.png")))));
        stage.setTitle("About unusual adventures with evolution");
        stage.setScene(new Scene(borderPane, 880, 500));
        stage.show();

        Label tittle = new Label("Here you can choose the configuration of simulation! ");
        tittle.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 22pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        borderPane.setTop(tittle);
        BorderPane.setAlignment(tittle, Pos.CENTER);
        BorderPane.setMargin(tittle, new Insets(20, 0, 20, 0));

        initGetData();
    }

    private void initGetData() {
        TextField name = new TextField("My new configuration");
        TextField numberOfTunnels = new TextField("0");
        TextField mapWidth = new TextField("20");
        TextField mapHeight = new TextField("15");
        TextField startGrassQuantity = new TextField("5");
        TextField eatingGrassEnergy = new TextField("1");
        TextField startAnimalsQuantity = new TextField("15");
        TextField startAnimalsEnergy = new TextField("10");
        TextField animalFullEnergy = new TextField("15");
        TextField reproductionEnergy = new TextField("5");
        TextField minimalMutationNumber = new TextField("2");
        TextField maximalMutationNumber = new TextField("9");
        TextField genLength = new TextField("8");
        TextField grassPerDay = new TextField("8");
        ChoiceBox<String> animalMoving = new ChoiceBox<>();
        animalMoving.getItems().addAll("Predestination", "Craziness");
        ChoiceBox<String> mutationVariant = new ChoiceBox<>();
        mutationVariant.getItems().addAll("Random", "Correction");
        ChoiceBox<String> mapVariant = new ChoiceBox<>();
        mapVariant.getItems().addAll("Earth", "Tunnel");


        Button getParameter = new Button("CONFIRM");
        getParameter.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");

        VBox listTextFieldRight = new VBox(10);
        listTextFieldRight.getChildren().addAll(name, mapWidth, mapHeight, startGrassQuantity, eatingGrassEnergy, grassPerDay, startAnimalsQuantity, startAnimalsEnergy, animalFullEnergy);

        VBox listTextFieldLeft = new VBox(10);
        listTextFieldLeft.getChildren().addAll(reproductionEnergy, minimalMutationNumber, maximalMutationNumber, genLength, animalMoving, mutationVariant, mapVariant, numberOfTunnels);

        Label nameLabel = new Label("Name: ");
        nameLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label mapWidthLabel = new Label("Width: ");
        mapWidthLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label mapHeightLabel = new Label("Height: ");
        mapHeightLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label startGrassQuantityLabel = new Label("Start Quantity of Grass: ");
        startGrassQuantityLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label eatingGrassEnergyLabel = new Label("Energy from eating grass: ");
        eatingGrassEnergyLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label grassPerDayLabel = new Label("How many grass grow per day: ");
        grassPerDayLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label startAnimalsQuantityLabel = new Label("Start Quantity of Animals: ");
        startAnimalsQuantityLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label startAnimalsEnergyLabel = new Label("Start energy of Animals: ");
        startAnimalsEnergyLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label animalFullEnergyLabel = new Label("Full Animals energy: ");
        animalFullEnergyLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label reproductionEnergyLabel = new Label("Energy to reproduction: ");
        reproductionEnergyLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label minimalMutationNumberLabel = new Label("Minimal mutation number: ");
        minimalMutationNumberLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label maximalMutationNumberLabel = new Label("Maximal mutation number: ");
        maximalMutationNumberLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label genLengthLabel = new Label("Gen length: ");
        genLengthLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        Label numberOfTunnelsLabel = new Label("Number Of Tunnel: ");
        numberOfTunnelsLabel.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        String labelStyle = "-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);";
        Label animalMovingLabel = new Label("Animal Moving:");
        animalMovingLabel.setStyle(labelStyle);
        Label mutationVariantLabel = new Label("Mutation Variant:");
        mutationVariantLabel.setStyle(labelStyle);
        Label mapVariantLabel = new Label("Map Variant:");
        mapVariantLabel.setStyle(labelStyle);

        VBox listOfLabelLeft = new VBox(18);
        VBox listOfLabelRight = new VBox(18);
        listOfLabelLeft.getChildren().addAll(reproductionEnergyLabel, minimalMutationNumberLabel, maximalMutationNumberLabel, genLengthLabel/*, movementDetailsLabel*/, animalMovingLabel, mutationVariantLabel, mapVariantLabel, numberOfTunnelsLabel);
        listOfLabelRight.getChildren().addAll(nameLabel, mapWidthLabel, mapHeightLabel, startGrassQuantityLabel, eatingGrassEnergyLabel, grassPerDayLabel, startAnimalsQuantityLabel, startAnimalsEnergyLabel, animalFullEnergyLabel);


        HBox inputList = new HBox(10);
        inputList.getChildren().addAll(listOfLabelRight, listTextFieldRight, listOfLabelLeft, listTextFieldLeft);
        inputList.setAlignment(Pos.TOP_CENTER);


        VBox confirm = new VBox(getParameter);
        VBox.setVgrow(getParameter, Priority.ALWAYS);
        VBox.setMargin(getParameter, new Insets(60, 0, 200, 0));
        confirm.setAlignment(Pos.TOP_CENTER);

        borderPane.setCenter(inputList);
        borderPane.setBottom(confirm);
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        getParameter.setOnAction(action -> {
            String configName;
            configName = name.getText();
            if (configName.contains(",") || configName.isEmpty() || configName.charAt(0) == ' ' || configName.charAt(configName.length()-1)  == ' ') {
                try {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("error");
                    alert.setHeaderText("INCORRECT CONFIG NAME");
                    alert.setContentText("Please, make sure your name is correct");
                    alert.showAndWait();
                    throw new Exception("Incorrect config name");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                if (OptionReader.find(configName) != null) {
                    throw new Exception("this configuration already exist, use another name");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            String[] textFieldValues = new String[17];
            textFieldValues[0] = mapWidth.getText();
            textFieldValues[1] = mapHeight.getText();
            textFieldValues[2] = startGrassQuantity.getText();
            textFieldValues[3] = eatingGrassEnergy.getText();
            textFieldValues[4] = grassPerDay.getText();
            textFieldValues[5] = startAnimalsQuantity.getText();
            textFieldValues[6] = startAnimalsEnergy.getText();
            textFieldValues[7] = animalFullEnergy.getText();
            textFieldValues[8] = reproductionEnergy.getText();
            textFieldValues[9] = minimalMutationNumber.getText();
            textFieldValues[10] = maximalMutationNumber.getText();
            textFieldValues[11] = genLength.getText();
            textFieldValues[13] = animalMoving.getValue();
            textFieldValues[12] = mutationVariant.getValue();
            textFieldValues[15] = mapVariant.getValue();
            textFieldValues[16] = numberOfTunnels.getText();
            Settings parameter;
            try {
                parameter = new Settings(configName, textFieldValues);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("error");
                alert.setHeaderText("INCORRECT INPUT DATA");
                alert.setContentText("Please, check your settings and try again");
                alert.showAndWait();
                throw new RuntimeException(e);
            }

            try {
                OptionReader.add(configName, textFieldValues);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                new StartApp(parameter);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            stage.close();
        });
    }
}
