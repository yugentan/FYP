package com.example.fyp.Logics.AwsManager;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class ResourceHandler {
    public static String getEc2(String accessKey, String secretKey){
        Ec2Client ec2Client = Ec2Client.builder()
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(()-> AwsBasicCredentials.create(accessKey,secretKey))
                .build();
        try{
            DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
            DescribeInstancesResponse response = ec2Client.describeInstances(request);

            for (Reservation reservation : response.reservations()) {
                for (Instance instance : reservation.instances()) {
                    System.out.println("Instance ID: " + instance.instanceId());
                    System.out.println("Instance Type: " + instance.instanceType());
                }
            }
            return "";
        }catch (Exception e){
            return "";
        }
    }

    public static String createEc2(String accessKey, String secretKey, String serviceName) {
        Ec2Client ec2Client = Ec2Client.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
                .build();

        try {
            // Set up the request to run an EC2 instance
            RunInstancesRequest runRequest = RunInstancesRequest.builder()
                    .imageId("ami-001440bcc4ddffcf1")
                    .instanceType(InstanceType.T2_MICRO)
                    .maxCount(1)
                    .minCount(1)
                    .tagSpecifications(TagSpecification.builder()
                            .resourceType("instance")
                            .tags(Tag.builder()
                                    .key("Name")
                                    .value(serviceName)
                                    .build())
                            .build())
                    .build();

            // Run the EC2 instance
            RunInstancesResponse response = ec2Client.runInstances(runRequest);

            // Get the instance ID of the created instance
            String instanceId = response.instances().get(0).instanceId();

            System.out.println("Instance created with ID: " + instanceId);

            return instanceId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
