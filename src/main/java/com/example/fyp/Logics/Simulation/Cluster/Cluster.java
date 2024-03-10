package com.example.fyp.Logics.Simulation.Cluster;

import com.example.fyp.Logics.Simulation.Node.INode;
import org.cloudbus.cloudsim.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cluster {
    // This cluster can have x amount of host.
    // Of minimum, a deployed app should have 1 host acting as Master node, 1 host acting as worker node.
    private List<INode> hostList;
    private String name;
    private Datacenter cluster;
    public Cluster(String name, List<INode> instances){
        this.name = name;
        this.hostList = instances;
    }

    private DatacenterCharacteristics getClusterCharacteristic(){
        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen"; // vm manager
        double time_zone = 8.0; //time_zone of this cluster is located in

        List<Host> hosts = new ArrayList<Host>();

        double totalCost = 0.0; //cost of all host in this cluster
        double costPerMem = 0.05; //Default: since instance type don't have a cost per memory use
        double costPerStorage = 0.001; //Default: to be change when ebs is implemented
        double costPerBw = 0.0; //Default: since instance type don't have a cost per bandwidth use

        for(INode host : hostList){
            totalCost += host.getCost();
            hosts.add(host.getHost());
        }

        return new DatacenterCharacteristics(
                arch,
                os,
                vmm,
                hosts,
                time_zone,
                totalCost,
                costPerMem,
                costPerStorage,
                costPerBw
        );
    }

    // This method creates a datacenter that represent our cluster
    public void initializeCluster(){
        List<Host> hosts = new ArrayList<>();
        LinkedList<Storage> storageList = new LinkedList<Storage>();
        for(INode host:hostList){
            host.createInstance();
            hosts.add(host.getHost());
        }

        try{
            cluster = new Datacenter(this.name, this.getClusterCharacteristic(), new VmAllocationPolicySimple(hosts), storageList,0);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // return a representation of our cluster
    public Datacenter getCluster(){
        if(this.cluster == null){
            initializeCluster();
        }
        return this.cluster;
    }

    public void addHost(INode instance){
        this.hostList.add(instance);
    }

}
