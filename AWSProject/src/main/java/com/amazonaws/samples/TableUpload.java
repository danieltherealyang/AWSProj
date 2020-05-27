package com.amazonaws.samples;

import java.sql.SQLException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
public class TableUpload {
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

		Table table = dynamoDB.getTable("TableUpload");
		
		// Build the item
		Item item = new Item()
		    .withPrimaryKey("Id", 1)
		    .withString("name", "Tyler1")
		    .withString("BestProduct", "Tyler1 Bloodrush")
		    .withBoolean("isBuff", true);
	
		// Write the item to the table
		PutItemOutcome outcome = table.putItem(item);
	}
}