package com.example.fyp.Logics.AwsManager;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;


public class Authentication {

    /**
     * This method test the input accessKey and secretKey of login screen
     * using the aws sdk.
     * @param accessKey access key for aws
     * @param secretKey secret key for aws
     * @return true or false base off the provided keys
     */
    public static boolean auth(String accessKey, String secretKey){
        S3Client c = S3Client
                .builder()
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(()-> AwsBasicCredentials.create(accessKey,secretKey))
                .build();
        try{
            ListBucketsResponse response = c.listBuckets();
            //No exception thrown means ok
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
