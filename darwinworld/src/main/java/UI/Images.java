package UI;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class Images {
    public Image grassImage;
    public Image tunnelImage;

    public Images() {
        try {
            this.tunnelImage = new Image (new FileInputStream(String.valueOf(Paths.get(System.getProperty("user.dir"),"darwinworld","src","main","resources","tunel.png"))));
            this.grassImage = new Image(new FileInputStream(String.valueOf(Paths.get(System.getProperty("user.dir"),"darwinworld","src","main","resources","trawa.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
