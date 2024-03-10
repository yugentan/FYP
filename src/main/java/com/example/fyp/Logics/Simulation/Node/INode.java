package com.example.fyp.Logics.Simulation.Node;


import org.cloudbus.cloudsim.Host;

public interface INode {
    public double getCost();
    public String getVmType();
    public Host getHost();
    public void createInstance();
}
