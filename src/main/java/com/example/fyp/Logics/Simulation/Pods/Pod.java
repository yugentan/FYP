package com.example.fyp.Logics.Simulation.Pods;

import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

public class Pod {
    Vm vm;

    private String vmm = "Xen"; // VM manager name

    private String serviceName;
    private int id;
    private int brokerid;
    private int mips = 1000;
    private long size = 200; //size in mb
    private int ram = 512; //required ram for this pod
    private long bw = 100; //required bw of this pod
    private int pesNumber = 1; //number of cpu

    //This is where each deployment of ms is map to a pod.
    public Pod(int id, int brokerid, String serviceName, int mips, int pesNumber){
        this.id = id;
        this.brokerid = brokerid;
        this.serviceName = serviceName;
        this.mips = mips;
        this.pesNumber = pesNumber;
    }
    public void initializeVM(){
        vm = new Vm(this.id, this.brokerid, this.mips, this.pesNumber, this.ram
                ,this.bw, this.size, this.vmm, new CloudletSchedulerTimeShared());
    }
    public Vm getVm(){
        if(vm == null) {
            initializeVM();
        }
        return this.vm;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public void setBrokerid(int id){
        this.brokerid = id;
        this.vm = new Vm(this.id, this.brokerid, this.mips, this.pesNumber, this.ram
                ,this.bw, this.size, this.vmm, new CloudletSchedulerTimeShared());
    }
    @Override
    public String toString() {
        return "Pod{" +
                "vm=" + vm +
                ", vmm='" + vmm + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", id=" + id +
                ", brokerid=" + brokerid +
                ", mips=" + mips +
                ", size=" + size +
                ", ram=" + ram +
                ", bw=" + bw +
                ", pesNumber=" + pesNumber +
                '}';
    }
}
