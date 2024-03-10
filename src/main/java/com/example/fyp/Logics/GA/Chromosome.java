package com.example.fyp.Logics.GA;

import com.example.fyp.Logics.Simulation.Node.INode;
import com.example.fyp.Logics.Simulation.Node.t2.*;
import com.example.fyp.Logics.Simulation.Node.t3.*;
import com.example.fyp.Logics.Simulation.Node.c5.*;
import com.example.fyp.Logics.Simulation.Node.m5.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome {
    private List<INode> hostlist;
    private double fitness;


    //Right now we are measuring fitness in respect to cost.
    //Future improvement will be made to the fitness by taking in utilization of each host in respect to cost
    public double getFitness(){
        return this.fitness;
    }
    public void setFitness(double f){
        this.fitness = f;
    }

    public double getCost(){
        double cost = 0.0;
        for(INode nodes : hostlist){
            cost += nodes.getCost();
        }
        BigDecimal rounded = BigDecimal.valueOf(cost);
        rounded = rounded.setScale(2, RoundingMode.UP);
        return rounded.doubleValue();
    }
    public Chromosome(){
        this.hostlist = new ArrayList<>();
    }
    public List<INode> getHostList(){
        return this.hostlist;
    }
    public void setHostlist(List<INode> newHostList){
        this.hostlist = newHostList;
    }

    public void initializeGenotype(int size){
        for(int i = 0; i < size; i++){
            INode n = getRandomNode(hostlist.size()+1);
            n.createInstance();
            hostlist.add(n);
        }
    }

    //This method cross over the INodes of 2 parents
    public static List<INode> crossover(List<INode> g1, List<INode> g2) {
        Random random = new Random();
        int xo = random.nextInt(g1.size());
        if (xo == 0) {
            return g2;
        }
        if (xo == g1.size() - 1) {
            return g1;
        }
        if (xo > g2.size()) {
            xo = g2.size() - 1;
        }
        List<INode> g3 = new ArrayList<>(g1.subList(0, xo));
        g3.addAll(g2.subList(xo, g2.size()));
        return g3;
    }
    public static List<INode> pointMutate(List<INode> g1, double rate, double amount){
        for(INode n : g1){
            Random r = new Random();
            if(r.nextDouble() < rate){
                int ind = r.nextInt(g1.size());
                g1.set(ind, getRandomNode(g1.size()+1));
            }
            return g1;
        }
        return g1;
    }
    //This method shrinks the nodes of a chromosome by deleting a node from a random point
    public static List<INode> shrinkMutate(List<INode> g1, double rate){
        if (g1.size() == 1) {
            return g1;
        }
        Random random = new Random();
        if (random.nextDouble() < rate) {
            int ind = random.nextInt(g1.size());
            g1.remove(ind);
        }
        return g1;
    }
    public static List<INode> growMutate(List<INode> g1, double rate){
        Random random = new Random();
        if(random.nextDouble() < rate){
            g1.add(getRandomNode(g1.size()+1));
        }
        return g1;
    }

    public static INode getRandomNode(int vmid){
        Random r = new Random();
        int randomNumber = r.nextInt(30);
        return switch (randomNumber) {
            case 1 -> new t2micro(vmid);
            case 2 -> new t2small(vmid);
            case 3 -> new t2medium(vmid);
            case 4 -> new t2large(vmid);
            case 5 -> new t2xlarge(vmid);
            case 6 -> new t2x2large(vmid);
            case 7 -> new t3nano(vmid);
            case 8 -> new t3micro(vmid);
            case 9 -> new t3small(vmid);
            case 10 -> new t3medium(vmid);
            case 11 -> new t3large(vmid);
            case 12 -> new t3xlarge(vmid);
            case 13 -> new t3x2large(vmid);
            case 14 -> new m5large(vmid);
            case 15 -> new m5xlarge(vmid);
            case 16 -> new m5x2large(vmid);
            case 17 -> new m5x4large(vmid);
            case 18 -> new m5x8large(vmid);
            case 19 -> new m5x12large(vmid);
            case 20 -> new m5x16large(vmid);
            case 21 -> new m5x24large(vmid);
            case 22 -> new c5large(vmid);
            case 23 -> new c5xlarge(vmid);
            case 24 -> new c5x2large(vmid);
            case 25 -> new c5x4large(vmid);
            case 26 -> new c5x8large(vmid);
            case 27 -> new c5x12large(vmid);
            case 28 -> new c5x18large(vmid);
            case 29 -> new c5x24large(vmid);
            default -> new t2nano(vmid);
        };
    }
}
