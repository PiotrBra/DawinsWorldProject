package Model;

import Maps.MoveValidator;
import Model.Genome.Gene;
import Model.Genome.Genome;
import Observers.ElementChangeObserver;
import Simulations.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement,Comparable<Animal> {
    private Vector2d position;
    private int energy;
    private Directions orientation = Directions.N;
    private Genome genome = new Genome();
    public Gene currGene;
    private Settings settings;
    private final int birthday;
    public final Animal father1;
    public final Animal father2;
    public Animal(Vector2d pos, Settings settings, int day, List<Integer> gens,int energy){
        this.position = pos;
        this.settings = settings;
        this.birthday = day;
        for (int i = 0; i < gens.size(); i++){
            genome.addGene(gens.get(i));
        }
        currGene = genome.getHead();
        this.energy = energy;
        this.father1 = null;
        this.father2 = null;
    }
    public Animal(List<Integer> gens,Settings s) throws Exception {
        this.position = null;
        this.settings = s;
        this.birthday = 0;
        for (int i = 0; i < gens.size(); i++){
            genome.addGene(gens.get(i));
        }
        currGene = genome.getHead();
        this.energy = 0;
        this.father1 = null;
        this.father2 = null;
    }
    public Animal(Animal father1, Animal father2, int day){
        this.position = father1.getPosition();
        this.settings = father1.getSettings();
        this.orientation = Directions.N;
        this.birthday = day;
        this.father1 = father1;
        this.father2 = father2;
        this.energy = 2 * settings.getReproductionLostEnergy() <= settings.getAnimalFullEnergy() ? 2 * settings.getReproductionLostEnergy() : settings.getAnimalFullEnergy();
        this.genome = new Genome();
        Random r = new Random();
        boolean left = r.nextBoolean();
        int domGenesLen = (int) (0.75 * settings.getGenLength());
        int subGenesLen = (int) (0.25 * settings.getGenLength());
        int numMutations = r.nextInt(settings.getMaximalMutationNumber()+1) + settings.getMinimalMutationNumber();
        List<Integer> mutationPoints = new ArrayList<>();
        for (int i = 0; i < numMutations; i++){
            mutationPoints.add(r.nextInt(settings.getGenLength()));
        }
        Gene p = father1.genome.getHead();
        Gene q = father2.genome.getHead();
        if (left){
            for (int i = 0; i < domGenesLen; i++){
                if (!mutationPoints.contains(i)) {
                    genome.addGene(p.getGene());
                }
                else {
                    genome.addGene(r.nextInt(8));
                }
                p = p.next;
                q = q.next;
            }
            for (int i = 0; i < subGenesLen; i++){
                if (!mutationPoints.contains(i)){
                    genome.addGene(q.getGene());
                }
                else {
                    genome.addGene(r.nextInt(8));
                }
                q = q.next;
            }
        }
        else {
            for (int i = 0; i < subGenesLen; i++){
                if (!mutationPoints.contains(i)){
                    genome.addGene(q.getGene());
                }
                else {
                    genome.addGene(r.nextInt(8));
                }
                p = p.next;
                q = q.next;
            }
            for (int i = 0; i < domGenesLen; i++){
                if (!mutationPoints.contains(i)) {
                    genome.addGene(p.getGene());
                }
                else {
                    genome.addGene(r.nextInt(8));
                }
                p = p.next;
            }
        }
        this.currGene = genome.getHead();
        father1.energy -= settings.getReproductionLostEnergy();
        father2.energy -= settings.getReproductionLostEnergy();
    }
    public boolean subEnergy(int val){
        energy -= val;
        return energy < 0 ? true : false;
    }
    public MoveTuple move(MoveValidator validator){
        MoveTuple oldnew = new MoveTuple();
        oldnew.oldPosition = position;
        if (validator.moveValidator(position.add(orientation.dirToVector()))) {
            position = position.add(orientation.dirToVector());
        }
        orientation = orientation.rotation(currGene.getGene());
        currGene = getNextGene();
        oldnew.newPosition = position;
        return oldnew;
    }
    public int getEnergy() {
        return energy;
    }

    public Settings getSettings() {
        return settings;
    }

    private Gene getNextGene(){
        if (settings.getAnimalMoving() == 0) {
            return currGene.next;
        }
        else {
            Random generator = new Random();
            int roll = generator.nextInt(101) + 1;
            if (roll <= 20){
                int geneInd = generator.nextInt(genome.getLen());
                Gene g = currGene;
                for (int i = 0; i < geneInd; i++){
                    g = g.next;
                }
                return g;
            }
            else {
                return currGene.next;
            }
        }
    }
    public void setPosition(Vector2d pos){
        position = pos;
    }
    @Override
    public String toString(){
        return orientation.toString();
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public Directions getOrientation() {
        return orientation;
    }

    @Override
    public int getImageIdx() {
        return 0;
    }

    @Override
    public void setObserver(ElementChangeObserver observer) {

    }

    public Genome getGenome() {
        return genome;
    }

    public void addEnergy(){
        energy = energy + settings.getEatingGrassEnergy() <= settings.getAnimalFullEnergy() ? energy + settings.getEatingGrassEnergy() : settings.getAnimalFullEnergy();
    }
    @Override
    public int getActiveGenome() {
        return currGene.getGene();
    }

    @Override
    public int compareTo(Animal o) {
        return Integer.compare(o.getEnergy(),this.getEnergy());
    }
}
