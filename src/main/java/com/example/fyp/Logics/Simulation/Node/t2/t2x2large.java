package com.example.fyp.Logics.Simulation.Node.t2;

import com.example.fyp.Logics.Simulation.Node.INode;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;
import java.util.List;

public class t2x2large implements INode {
    private Host host;
    private int id;
    //Characteristics of t2.x2large
    private static final String VM_TYPE = "t2.x2large";
    private static final int VM_MIPS = 1000; // (Millions Instruction per Second) Not disclosed, using default of 1000
    private static final int VM_RAM = 32000; // RAM: x2large instance have 1 GiB ram
    private static final int VM_BANDWIDTH = 100; // Bandwidth: x2large instance have low(100mb) - moderate(1000mb) bandwidth
    private static final int VM_PES = 8; //Number of Processing Elements (cores): x2large instance have 1vCPU

    private static final int VM_SIZE = 10000; //Storage in Mb: x2large instance does not come with storage, default of 10000Mb is used

    private static final double VM_COST = 0.37; //Cost: on demand cost for x2large instance
    public t2x2large(int id){
        this.id = id;
    }

    @Override
    //This host will use a time-shared policy for scheduling
    public void createInstance(){
        //Create a list of processing unit, purpose for scheduling policies.
        //creating a list of size 1 since we only have 1 processing element for this instance
        List<Pe> processingElementList = new ArrayList<>(VM_PES);
        processingElementList.add(new Pe(0, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(1, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(2, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(3, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(4, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(5, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(6, new PeProvisionerSimple(VM_MIPS)));
        processingElementList.add(new Pe(7, new PeProvisionerSimple(VM_MIPS)));
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
