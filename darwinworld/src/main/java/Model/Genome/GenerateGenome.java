package Model.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateGenome {
    public static List<Integer> generateGenome(int len){
        ArrayList<Integer> genome = new ArrayList<>();
        Random r = new Random();
        for (int i=0; i<len;i++){
            genome.add(r.nextInt(8));
        }
        return genome;
    }
}
