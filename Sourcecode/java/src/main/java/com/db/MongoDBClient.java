package com.db;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.mol.polymergen.GenerateStructure;
import com.mol.util.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * ClassName: MongoDBClient *
 * 
 * @author Sudha Eswaran
 *
 */
@SuppressWarnings("deprecation")
public class MongoDBClient {

	static Logger logger = Logger.getLogger(MongoDBClient.class);
	MongoCollection<Document> coll = null;

	MongoCollection<Document> getCollection() {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(Constants.mongo_URI));
		MongoDatabase database = mongoClient.getDatabase(Constants.mongo_dbname);
		MongoCollection<Document> coll = database.getCollection(Constants.mongo_collname);
		return coll;
	}

	public String insertData(String jsonData) {
		MongoCollection<Document> coll = getCollection();
		Document document = Document.parse(jsonData);
		coll.insertOne(document);
		logger.info("Inserted");
		return "success";
	}
	public long getDocumentCount()
	{
		MongoCollection<Document> coll = getCollection();
		return coll.countDocuments();
	}
	

	public static void main(String s[]) {

		MongoDBClient mongoDB = new MongoDBClient();
		System.out.println(mongoDB.getDocumentCount());		

	}

}
