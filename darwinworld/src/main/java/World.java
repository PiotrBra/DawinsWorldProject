import UI.App;
import javafx.application.Application;

public class World {
    //public static void main(String[] args) {
        /*File file = new File("C:/Users/Piotr/Desktop/study/DarvinWorld/darwinworld/src/main/resources/Statistics/1.txt");
        if (file.exists()) {
            System.out.println("Can Read: " + file.canRead());
            System.out.println("Can Write: " + file.canWrite());
            System.out.println("Can Execute: " + file.canExecute());
        } else {
            System.out.println("File does not exist.");
        }
    }*/
    public static void main(String[] args){
        Application.launch(App.class, args);
    }
}
