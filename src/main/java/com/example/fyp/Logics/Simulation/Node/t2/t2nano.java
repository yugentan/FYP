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

/*
 * This class define the modelling of a t2.nano instance of aws ec2
 * Due to some information that are not disclose, a default value will be use which remain consistent across all instance
 */
public class t2nano implements INode {
    private Host host;
    private int id;
    //Characteristics of t2.nano
    private static final String VM_TYPE = "t2.nano";
    private static final int VM_MIPS = 1000; // (Millions Instruction per Second) Not disclosed, using default of 1000
    private static final int VM_RAM = 537; // RAM: nano instance have 0.5 GiB ram ~~ est. 537Mb
    private static final int VM_BANDWIDTH = 100; // Bandwidth: nano instance have low(100mb) - moderate(1000mb) bandwidth
    private static final int VM_PES = 1; //Number of Processing Elements (cores): nano instance have 1vCPU
    private static final int VM_SIZE = 10000; //Storage in Mb: nano instance does not come with storage, default of 10000Mb is used
    private static final double VM_COST = 0.01; //Cost: on demand cost for nano instance
    //Constructor
    public t2nano(int id){
        this.id = id;
    }

    @Override
    //This host will use a time-shared policy for scheduling
    public void createInstance(){
        //Create a list of processing unit, purpose for scheduling policies.
        //creating a list of size 1 since we only have 1 processing element for this instance
        List<Pe> processingElementList = new ArrayList<>(VM_PES);
        processingElementList.add(new Pe(0, new PeProvisionerSimple(VM_MIPS)));

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
