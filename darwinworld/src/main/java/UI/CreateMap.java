package UI;

import Maps.AbstractWorldMap;
import Maps.MapSquare;
import Model.Animal;
import Model.Vector2d;
import Model.WorldElement;
import Simulations.Settings;
import Simulations.SimulationEngine;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.Math.min;

public class CreateMap {
    private final SimulationEngine engine;
    private final GridPane gridPane;
    private final Settings parameters;
    private final double size;
    private final Images images = new Images();
    private final StartApp app;

    public CreateMap(SimulationEngine engine, Stage stage, StartApp app) {

        this.app = app;
        this.engine = engine;
        this.parameters = engine.getSettings();
        this.gridPane = new GridPane();

        double sizeScene = stage.getHeight();
        int width = parameters.getMapWidth();
        int height = parameters.getMapHeight();
        this.size = Math.max(width, height);

        for (int i = 0; i < parameters.getMapWidth(); i++) {
            this.gridPane.getColumnConstraints().add(new ColumnConstraints(sizeScene / (0.8 * size)));
        }
        for (int i = 0; i < parameters.getMapHeight(); i++) {
            this.gridPane.getRowConstraints().add(new RowConstraints(sizeScene / (1.5 * size)));
        }
        createMap();
    }

    public void createMap() {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        puttingObjects();
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

    private void puttingObjects() {
        AbstractWorldMap map = parameters.getMap();
        List<Vector2d> mapContain = parameters.getMap().getPreferred();

        ImageView imageView;
        Map<Vector2d, MapSquare> MapSquare = map.getElements();
        int freePosition = 0;

        for (int i = 0; i < parameters.getMapWidth(); i++) {
            for (int j = 0; j < parameters.getMapHeight(); j++) {
                StackPane grasses = new StackPane();
                if (mapContain.contains(new Vector2d(i, j))) {
                    grasses.setStyle("-fx-background-color: rgba(0,255,20,0.84)");
                    gridPane.add(grasses, i, j);
                } else {
                    grasses.setStyle("-fx-background-color: rgb(0,101,4)");
                    gridPane.add(grasses, i, j);
                }

                Vector2d position = new Vector2d(i, j);
                MapSquare square = MapSquare.get(position);
                if (square != null && square.getObjects().size() != 0) {

                    HBox hbox = new HBox(5);
                    hbox.setAlignment(Pos.CENTER);

                    int howMany = square.getObjects().size();
                    for (Animal animal : square.getObjects()) {
                        double imageHeight = 500 / (1.5 * size);
                        double imageWidth = 600 / (1.5 * size);
                        Circle animalVis = new Circle(imageHeight/2);
                        animalVis.setFill(Color.hsb(0,1.0,(double) (animal.getEnergy() > 0 ? animal.getEnergy() : 0 )/ (double) animal.getSettings().getAnimalFullEnergy()));
                        Label posit = new Label(position.toString());
                        posit.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #b0b0b0; -fx-background-color: rgba(9,1,1,0.84);");
                        posit.setFont(Font.font(20 / (0.2 * size)));

                        VBox box = new VBox(3, animalVis, posit);
                        box.setAlignment(Pos.CENTER);

                        gridPane.add(box,i,j);
                        GridPane.setHalignment(box, Pos.CENTER.getHpos());
                    }

                    gridPane.add(hbox, i, j);
                    GridPane.setHalignment(hbox, Pos.CENTER.getHpos());

                } else {
                    freePosition += 1;

                    if (Objects.requireNonNull(square).didGrassGrow()) {
                        Label posit = new Label(position.toString());
                        posit.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #b0b0b0; -fx-background-color: rgba(9,1,1,0.84);");
                        posit.setFont(Font.font(20 / (0.2 * size)));

                        imageView = new ImageView(images.grassImage);
                        double imageHeight = 500 / (1.5 * size);
                        double imageWidth = 600 / (1.5 * size);
                        imageView.setFitHeight(imageHeight);
                        imageView.setFitWidth(imageWidth);

                        VBox box = new VBox(3, imageView, posit);
                        box.setAlignment(Pos.CENTER);

                        gridPane.add(box, i, j);
                        GridPane.setHalignment(box, Pos.CENTER.getHpos());
                    }else if (Objects.requireNonNull(square).isTunnel()){
                        Label posit = new Label(position.toString());
                        posit.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #b0b0b0; -fx-background-color: rgba(9,1,1,0.84);");
                        posit.setFont(Font.font(20 / (0.2 * size)));

                        imageView = new ImageView(images.tunnelImage);
                        double imageHeight = 500 / (1.5 * size);
                        double imageWidth = 600 / (1.5 * size);
                        imageView.setFitHeight(imageHeight);
                        imageView.setFitWidth(imageWidth);

                        VBox box = new VBox(3, imageView, posit);
                        box.setAlignment(Pos.CENTER);

                        gridPane.add(box, i, j);
                        GridPane.setHalignment(box, Pos.CENTER.getHpos());
                    }
                }
            }
            engine.setFreePositionQuantity(freePosition);
            gridPane.setAlignment(Pos.CENTER);
        }
    }

    private void setButtonOnAction(StackPane till, Animal animal, SimulationEngine engine) {
        till.setOnMouseClicked(event -> {
            if (!engine.isActive()) {
                app.setFollowingAnimal(animal);
                app.updateInfo();
                //System.out.println("stop");
            }
        });
    }
}
