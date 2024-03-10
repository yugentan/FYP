package com.example.fyp.Logics.GA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Chromosome> chromosomeList;
    private int chromosomeSize;
    private int populationSize;
    private List<Double> fitnessmap;
    public Population(int popSize, int chromosomeSize){
        this.populationSize = popSize;
        this.chromosomeSize = chromosomeSize;
        this.chromosomeList = new ArrayList<>();
    }

    public void generatePopulation(){
        for(int i = 0; i < populationSize; i++){
            //initialize chromosomes
            Chromosome c = new Chromosome();
            c.initializeGenotype(chromosomeSize);
            chromosomeList.add(c);
        }
    }

    // Using cumalative fitness
    public void evalFitnessMap(){
        List<Double> fitnessMap = new ArrayList<>();
        double fitness = 0.0;
        for(Chromosome c : chromosomeList){
            fitness = fitness + c.getFitness();
            fitnessMap.add(fitness);
        }
        this.fitnessmap = fitnessMap;
    }
    public List<Double> getFitnessmap(){
        return this.fitnessmap;
    }
    public List<Chromosome> getChromosomeList(){
        return this.chromosomeList;
    }
    public double getMeanFitness(){
        double total = 0.0;
        for(Chromosome c : chromosomeList){
            total += c.getFitness();
        }
        return total/chromosomeList.size();
    }
    public Chromosome getElite(){
        Chromosome elite = getChromosomeList().get(0);
        for(Chromosome c : getChromosomeList()){
            if(c.getFitness() > elite.getFitness() ){
                elite = c;
            }
        }
        return elite;
    }
    public Integer selectParent(){
        Random r = new Random();
        double randomNumber = r.nextDouble(0, 1);
        randomNumber = randomNumber * fitnessmap.get(fitnessmap.size()-1);
        for(int i = 0; i < fitnessmap.size(); i++){
            if (randomNumber <= fitnessmap.get(i)){
                return i;
            }
        }
        return 0;
    }

}
