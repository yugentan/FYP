package com.example.fyp.Logics.Simulation.Node.t3;


import com.example.fyp.Logics.Simulation.Node.INode;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;
import java.util.List;

public class t3medium implements INode {
    private Host host;
    private int id;
    //Characteristics of t3.medium
    private static final String VM_TYPE = "t3.medium";
    private static final int VM_MIPS = 1000; // (Millions Instruction per Second) Not disclosed, using default of 1000
    private static final int VM_RAM = 4000; // RAM: medium instance have 4 GiB ram
    private static final int VM_BANDWIDTH = 100; // Bandwidth: medium instance have low(100mb) - moderate(1000mb) bandwidth
    private static final int VM_PES = 2; //Number of Processing Elements (cores): medium instance have 1vCPU

    private static final int VM_SIZE = 10000; //Storage in Mb: medium instance does not come with storage, default of 10000Mb is used
    private static final double VM_COST = 0.04; //Cost: on demand cost for medium instance
    public t3medium(int id){
        this.id = id;
    }

    @Override
    //This host will use a time-shared policy for scheduling
    public void createInstance(){
        //Create a list of processing unit, purpose for scheduling policies.
        List<Pe> processingElementList = new ArrayList<>(VM_PES);
        processingElementList.add(new Pe(0, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(1, new PeProvisionerSimple(VM_MIPS)));

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
