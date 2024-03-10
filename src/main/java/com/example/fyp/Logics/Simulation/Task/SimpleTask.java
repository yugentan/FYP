package com.example.fyp.Logics.Simulation.Task;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
//A representation of a request
public class SimpleTask {
    private Cloudlet c;
    private int id;

    public SimpleTask(int id){
        this.id = id;
    }
    public void initializeTask(){
        int length = 400000; //(MI millions instructions) a length of 10000MI running ion a processor of 2000 mips takes 5 second
        int pesNumber = 1; // number of processing unit required to run this task
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        c = new Cloudlet(this.id, length, pesNumber, fileSize, outputSize, utilizationModel,utilizationModel,utilizationModel);
    }
    public void setUserId(int id){
        c.setUserId(id);
    }
    public void setAllocationId(int id){
        c.setVmId(id);
    }

    public Cloudlet getTask(){
        if (c == null){
            this.initializeTask();
        }
        return c;
    }

}
