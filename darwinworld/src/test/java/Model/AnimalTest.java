package Model;

import Simulations.Settings;
import javafx.scene.media.EqualizerBand;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnimalTest {
    @Test
    public void Animal() throws Exception {
        List<Integer> l1 = List.of(1,1,1,1);
        List<Integer> l2 = List.of(2,2,2,2);
        Animal animal1 = new Animal(l1,new Settings(null,"20,15,5,1,4,15,10,15,5,0,0,4,Random,Predestination,null,Tunnel,0".split(",")));
        Animal animal2 = new Animal(l2,new Settings(null,"20,15,5,1,4,15,10,15,5,0,0,4,Random,Predestination,null,Tunnel,0".split(",")));
        Animal animal3 = new Animal(animal1,animal2,0);
        System.out.println(animal1.getGenome());
        System.out.println(animal2.getGenome());
        System.out.println(animal3.getGenome());
    }
}
