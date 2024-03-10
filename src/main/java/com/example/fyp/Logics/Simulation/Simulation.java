package com.example.fyp.Logics.Simulation;

import com.example.fyp.Logics.Common.SingletonSession;
import com.example.fyp.Logics.GA.Chromosome;
import com.example.fyp.Logics.GA.Population;
import com.example.fyp.Logics.Simulation.Cluster.Cluster;
import com.example.fyp.Logics.Simulation.Node.INode;
import com.example.fyp.Logics.Simulation.Pods.Pod;
import com.example.fyp.Logics.Simulation.Task.SimpleTask;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Simulation {
    public void evaluatePopulation(Population p) {
        for(Chromosome c : p.getChromosomeList()){
         try {
             Log.disable();
             Cluster simulationCluster = new Cluster("Cluster", c.getHostList());
             int num_user = 1; // number of cloud users
             Calendar calendar = Calendar.getInstance(); // Calendar whose fields have been initialized with the current date and time.
             boolean trace_flag = false; // trace events
             CloudSim.init(num_user, calendar, trace_flag);
             Datacenter cl = simulationCluster.getCluster();

             DatacenterBroker broker = createBroker();
             int brokerId = broker.getId();
             List<Vm> podlist = new ArrayList<>();

             for(Pod pod : SingletonSession.getSession().getServices()){
                 pod.setBrokerid(brokerId);
                 podlist.add(pod.getVm());
             }
             broker.submitVmList(podlist);
             List<Cloudlet> taskList = new ArrayList<Cloudlet>();
             //say we got 100 task to be completed by this assigned pods in our host
             for(int i = 0; i < 10; i++){
                 SimpleTask httpRequest = new SimpleTask(i);
                 httpRequest.initializeTask();
                 httpRequest.setUserId(brokerId);
                 Random r = new Random();
                 httpRequest.setAllocationId(r.nextInt(podlist.size()));
                 taskList.add(httpRequest.getTask());
             }

             broker.submitCloudletList(taskList);

             CloudSim.startSimulation();

             CloudSim.stopSimulation();

             //List<Cloudlet> newList = broker.getCloudletReceivedList();
             //printCloudletList(newList);
             //Evaluating fitness by: time taken for task execution/cost of operation.
             double totalTime = 0;
             for(Cloudlet clt : broker.getCloudletReceivedList()){
                 totalTime += clt.getActualCPUTime();
             }

             double avgTime = totalTime/broker.getCloudletReceivedList().size();

             double afitness = (avgTime/10)/c.getCost();

             c.setFitness(afitness);
         }catch (Exception e) {
             e.printStackTrace();
             Log.printLine("Unwanted errors happen");
         }
        }

    }



    private static DatacenterBroker createBroker() {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return broker;
    }
    private static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        System.out.println();
        System.out.println("========== OUTPUT ==========");
        System.out.println("Cloudlet ID" + indent + "STATUS" + indent
                + "Data center ID" + indent + "VM ID" + indent + "Time" + indent
                + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            System.out.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                System.out.print("SUCCESS");

                System.out.println(indent + indent + cloudlet.getResourceId()
                        + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + indent + dft.format(cloudlet.getExecStartTime())
                        + indent + indent
                        + dft.format(cloudlet.getFinishTime()));
            }
        }
    }
}
