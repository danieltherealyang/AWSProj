package com.amazonaws.samples;

import java.util.Arrays;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class MoviesCreateTable {

    public static void main(String[] args) throws Exception {
    	ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
    	try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot Load Credentials",
                    e);
        }
    	
    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
    			.withCredentials(credentialsProvider)
    			.withRegion("us-east-2")
    			.build();

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "loltyler1dotcom-Movies";

        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
                                                                          // key
                    new KeySchemaElement("title", KeyType.RANGE)), // Sort key
                Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                    new AttributeDefinition("title", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }

    }
}
