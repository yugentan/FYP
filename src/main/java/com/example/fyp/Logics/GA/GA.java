package com.example.fyp.Logics.GA;

import com.example.fyp.Logics.Common.SingletonSession;
import com.example.fyp.Logics.Simulation.Node.INode;
import com.example.fyp.Logics.Simulation.Simulation;
import com.example.fyp.Main;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GA {
    public static void run(int popSize, int generation, VBox out){
        String fPath = Main.class.getResource("").getPath() + "generation.csv";
        FileWriter w = null;
        try{
            w = new FileWriter(fPath);
            w.write("Generation, Mean\n");
            Population p = new Population(popSize, SingletonSession.getSession().getServices().size());
            p.generatePopulation();
            for(int i = 0; i < generation; i++){
                //Evaluate the population by running it in the cloud simulation
                Simulation s = new Simulation();
                s.evaluatePopulation(p);
                p.evalFitnessMap();
                System.out.println("Generation: " + i + ", Mean fitness: " + p.getMeanFitness());
                w.write(i + "," + p.getMeanFitness() + "\n");
                Label generationLabel = new Label("Generation: " + i + ", Mean fitness: " + p.getMeanFitness());
                out.getChildren().add(generationLabel);

                //Saving elite chromosome
                Chromosome elite = p.getElite();

                //getting parents for new chromosome
                int parent1_idx = p.selectParent();
                int parent2_idx = p.selectParent();
                //creation of new chromosome by crossover
                List<INode> newChromosome = Chromosome.crossover(p.getChromosomeList().get(parent1_idx).getHostList(), p.getChromosomeList().get(parent2_idx).getHostList());

                //mutate the new genome
                //Point mutate
                newChromosome = Chromosome.pointMutate(newChromosome, 0.25, 0.25);
                //Grow mutate
                newChromosome = Chromosome.growMutate(newChromosome, 0.25);
                //shrink mutate
                newChromosome = Chromosome.shrinkMutate(newChromosome, 0.25);


                //setting the new chromosome into the population
                p.getChromosomeList().get(1).setHostlist(newChromosome);

                //setting idx zero as the elite
                p.getChromosomeList().set(0, elite);

                //For slowing down simulation purpose
                try {
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //Save the final elite to session
            SingletonSession.getSession().setElite(p.getElite().getHostList());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if(w!=null){
                try {
                    w.close(); // Close the FileWriter
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }

        }

    }
}
