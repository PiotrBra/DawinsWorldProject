package UI;
import Model.Animal;
import Observers.Controller;
import Simulations.Settings;
import Simulations.SimulationEngine;
import Simulations.Statistics;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class StartApp {
    private final Stage stage;
    private final BorderPane borderPane;
    private final Button startButton = new Button("Create Simulation");
    private final Button exitButton = new Button("EXIT");
    private final Button buttonEndTracking = new Button("START/STOP");
    private final SimulationEngine engine;
    private final Thread engineThread;
    private final CreateMap newMap;
    private Animal followingAnimal = null;
    private final ElementInformation boxAboutAnimal;
    private final StageWithCharts charts;

    public StartApp(Settings parameters) throws FileNotFoundException {

        this.stage = new Stage();
        this.borderPane = new BorderPane();
        this.boxAboutAnimal = new ElementInformation(stage, this);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        Scene sceneMain = new Scene(borderPane);
        stage.setScene(sceneMain);
        stage.show();
        stage.getIcons().add(new Image(new FileInputStream(String.valueOf(Paths.get(System.getProperty("user.dir"),"darwinworld","src","main","resources","world.png")))));
        stage.setTitle("Darwin's World");

        Label tittle = new Label("Darwin's World simulation ");
        tittle.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 22pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        borderPane.setTop(tittle);
        BorderPane.setAlignment(tittle, Pos.CENTER);
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        BorderPane.setMargin(tittle, new Insets(20, 0, 20, 0));

        engine = new SimulationEngine(parameters);
        this.charts = new StageWithCharts(stage, engine);

        this.newMap = new CreateMap(engine, stage, this);

        new Controller(engine, this);
        this.engineThread = new Thread(() -> {
            try {
                while (true) {
                    engine.run();
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        });

        stage.setOnCloseRequest(event -> engineThread.interrupt());

        GridPane gridPane = newMap.getGridPane();
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
        startApp();
    }

    private void startApp() {
        startButton.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84)");
        borderPane.setCenter(startButton);

        startButton.setOnAction(actionEvent -> {
            engine.changeStatus();
            engineThread.start();
            addButtons();
        });
    }

    private void addButtons() {
        HBox buttons = new HBox();
        buttons.setSpacing(300);

        HBox centerButtons = new HBox(exitButton, buttonEndTracking);
        centerButtons.setSpacing(15);
        exitButton.setOnAction(action -> {
            engineThread.interrupt();
            stage.close();
        });
        buttonEndTracking.setOnAction(action -> engine.changeStatus());

        buttons.getChildren().addAll(centerButtons);
        buttons.setAlignment(Pos.CENTER);
        borderPane.setBottom(buttons);
        BorderPane.setAlignment(buttons, Pos.CENTER);
        BorderPane.setMargin(buttons, new Insets(10, 0, 10, 0));
        exitButton.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
        buttonEndTracking.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 15 pt; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);");
    }

    private VBox uploadStats() {
        Statistics stats = engine.getStats();
        stats.updateStats();

        String labelStyle = "-fx-font-family: 'Bauhaus 93'; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);";

        Label title = new Label("MAP STATISTICS");

        Label worldDays = new Label("Number Day: " + stats.getWorldDays());
        worldDays.setStyle(labelStyle);

        Label numberOfAliveAnimals = new Label("Number of Alive Animals: " + stats.getNumberAnimals());
        numberOfAliveAnimals.setStyle(labelStyle);

        Label numberOfGrass = new Label("Number of grass: " + stats.getNumberGrass());
        numberOfGrass.setStyle(labelStyle);

        Label numberOfDeadAnimals = new Label("Number of Dead Animals: " + stats.getNumberDeadAnimals());
        numberOfDeadAnimals.setStyle(labelStyle);

        Label avgEnergy = new Label("Average of energy: " + stats.getAvgEnergy());
        avgEnergy.setStyle(labelStyle);

        Label avgLifeDaysDeadAnimal = new Label("Average of life: " + stats.getAvgLife());
        avgLifeDaysDeadAnimal.setStyle(labelStyle);

        Label avgChildren = new Label("Average of Children: " + stats.getAvgChildren());
        avgChildren.setStyle(labelStyle);

        Label dominantGenotype = new Label("Dominant Genotype: " + stats.getDominantGenotype());
        dominantGenotype.setStyle(labelStyle);

        Label freePosition = new Label("Free position: " + stats.getFreePositionQuantity());
        freePosition.setStyle(labelStyle);

        VBox statistics = new VBox(15);
        statistics.getChildren().addAll(title, worldDays, numberOfAliveAnimals, numberOfGrass, numberOfDeadAnimals, avgEnergy, avgLifeDaysDeadAnimal, avgChildren, dominantGenotype, freePosition);
        statistics.setAlignment(Pos.TOP_CENTER);

        title.setStyle("-fx-font-family: 'Calibri'; -fx-text-fill: rgb(176,176,176); -fx-background-color: rgba(9,1,1,0.84);-fx-font-weight: bold;");
        title.setFont(new Font(15));

        statistics.setStyle(String.valueOf(new Insets(0, 1, 1, 50)));
        return statistics;
    }

    public void updateInfo() {
        if (followingAnimal != null) {
            boxAboutAnimal.creativeInfo(followingAnimal);
        }
    }

    public void uploadMap() {
        Platform.runLater(() -> {
            charts.updateCharts();

            VBox stat = uploadStats();
            stat.setAlignment(Pos.CENTER);
            stat.setMaxHeight(stage.getHeight() / 1.5);
            stat.setStyle("-fx-background-color: rgba(9,1,1,0.84);");

            stat.setOnMouseClicked(event -> charts.chartsShow());

            newMap.createMap();
            GridPane gridPane = newMap.getGridPane();
            gridPane.setGridLinesVisible(true);

            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(gridPane, stat);
            hbox.setAlignment(Pos.CENTER);

            updateInfo();
            borderPane.setCenter(hbox);
        });
    }

    /*public int getDominantGenotype() {
        return engine.getStats().getDominantGenotype();
    }
*/
    public void setFollowingAnimal(Animal followingAnimal) {
        this.followingAnimal = followingAnimal;
    }
}
