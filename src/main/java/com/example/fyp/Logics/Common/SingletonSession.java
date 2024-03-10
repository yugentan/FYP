package com.example.fyp.Logics.Common;

import com.example.fyp.Logics.Simulation.Node.INode;
import com.example.fyp.Logics.Simulation.Pods.Pod;
import software.amazon.awssdk.regions.Region;

import java.util.ArrayList;
import java.util.List;

/** This is a singleton class that tracks the session of the user.
*   Singleton class will persist throughout application lifecycle
*   Easy reference for accesskey and region usecase.
**/
public class SingletonSession {
    private SingletonSession(){}
    private String accessKey;
    private String secretKey;
    private String provider;

    private Region region;

    private List<Pod> services;
    private List<INode> elite;
    static SingletonSession session = new SingletonSession();

    public static SingletonSession getSession(){
        return session;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    public Region getRegion() {
        return region;
    }
    public List<Pod> getServices(){
        if(services == null){
            services = new ArrayList<>();
        }
        return services;
    }

    public List<INode> getElite() {
        return elite;
    }

    public void setElite(List<INode> elite) {
        this.elite = elite;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
    @Override
    public String toString() {
        return "SingletonSession{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }

    public static void destroy(){
        session = new SingletonSession();
    }
}
