package com.example.fyp.Logics.Simulation.Node.m5;

import com.example.fyp.Logics.Simulation.Node.INode;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;
import java.util.List;

public class m5xlarge implements INode {
    private Host host;
    private int id;
    //Characteristics of m5.xlarge
    private static final String VM_TYPE = "m5.xlarge";
    private static final int VM_MIPS = 1000; // (Millions Instruction per Second) Not disclosed, using default of 1000
    private static final int VM_RAM = 16000; // RAM: xlarge instance have 1 GiB ram
    private static final int VM_BANDWIDTH = 100; // Bandwidth: xlarge instance have low(100mb) - moderate(1000mb) bandwidth
    private static final int VM_PES = 4; //Number of Processing Elements (cores): xlarge instance have 2 CPU

    private static final int VM_SIZE = 10000; //Storage in Mb: xlarge instance does not come with storage, default of 10000Mb is used

    private static final double VM_COST = 0.19; //Cost: on demand cost for xlarge instance
    public m5xlarge(int id){
        this.id = id;
    }

    @Override
    //This host will use a time-shared policy for scheduling
    public void createInstance(){
        //Create a list of processing unit, purpose for scheduling policies.
        //creating a list of size 1 since we only have 1 processing element for this instance
        List<Pe> processingElementList = new ArrayList<>(VM_PES);
        for(int i = 0; i < VM_PES; i++){
            processingElementList.add(new Pe(i, new PeProvisionerSimple(VM_MIPS)));
        }
        this.host = new Host(this.id,
                new RamProvisionerSimple(VM_RAM),
                new BwProvisionerSimple(VM_BANDWIDTH),
                VM_SIZE, processingElementList,
                new VmSchedulerTimeShared(processingElementList));
    }
    @Override
    public double getCost(){
        return VM_COST;
    }
    @Override
    public String getVmType(){
        return VM_TYPE;
    }
    @Override
    public Host getHost() {
        return this.host;
    }
}
