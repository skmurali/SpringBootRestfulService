package com.store.inventory;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray ;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;


public class StoreMongoDB {

	
	  public static void deleteMongodbItem( int itemID) 
	  {
		    
			try 
			{
			    DBCollection items =  getMongoDBConnection("items") ; 
				items.remove(new BasicDBObject().append("id", itemID)) ;
			    
			    BasicDBObject query = new BasicDBObject();
				query.put("id:", new BasicDBObject("$eq", itemID));
			    System.out.println("JSON Object has been stored  Successfully" + query);
				
				
			} 
			finally 
			{
			   //items.close();
			}
		    
		    
	  }
	  public void storedInMongoDB(Map documentMap ) {
		  
		    DBCollection itemsCollection =  getMongoDBConnection("items") ; 

		    itemsCollection.insert(new BasicDBObject(documentMap));

		    System.out.println("JSON Object has been stored  Successfully");
			DBCursor cursor = null	;
					
			try 
			{
				cursor = itemsCollection.find() ;
				
			   while(cursor.hasNext()) {

			       System.out.println(cursor.next());
			       
			   }
			} 
			finally 
			{
			   cursor.close();
		       System.out.println("JSON Object has been displayed Successfully ");
			}
	  }
	  
	  public static Object getItemDetails(int itemID) 
	  {	  
			
		    DBCollection itemsCollection =  getMongoDBConnection("items") ; 
			DBObject predicate = new BasicDBObject("id", itemID)  ;
			System.out.println( " quer2   "  +  predicate);
			
			DBCursor myCursor =  itemsCollection.find(predicate) ;
			System.out.println(" cursor " + myCursor.count()); 
			String output ="";
			while (myCursor.hasNext()) 
			{
				DBObject dbobj = myCursor.next();
				output = JSON.serialize(dbobj) ;
				
			    System.out.println("retrived row " +output);
			}
			
		    System.out.println("output " +  output);
		    System.out.println("girlObj count " +  itemsCollection.getCount());

	    	return output ;
		  
	  }
	  public static void updateItem(item user) {
		  
		    DBCollection itemsCollection =  getMongoDBConnection("items") ; 

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("name", user.getName());
			newDocument.append("description", user.getDescription());
			newDocument.append("qty", user.getQty());
			newDocument.append("price", user.getPrice());
			
			BasicDBObject setQuery = new BasicDBObject();
			setQuery.append("$set", newDocument);
			
			BasicDBObject searchQuery = new BasicDBObject().append("id", user.getId());
			itemsCollection.update(searchQuery, setQuery);

			
	  }
	  
	  public static void insertDocument(item user) {
		  
		  DBCollection girlsCollection =  getMongoDBConnection("girls") ; 
		  BasicDBObject document = new BasicDBObject();
		  
		  document.put("id", user.getId());
		  document.put("name", user.getName());
		  document.put("description", user.getDescription());

		  document.put("qty", user.getQty());
		  document.put("price", user.getPrice());
		  
	      WriteResult wrt = girlsCollection.insert(document);
		  
	  }
	  
	  
	  public static List retrieveItemDetails() throws Exception
	  {
	       System.out.println(" MongoDB  retrieveItemDetails   ") ;
		  
		    DBCollection coll =  getMongoDBConnection("items") ; 
	  		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			DBCursor cursor = null	;
			List  itemsList =  new ArrayList() ;
			String json = "";
			JSONArray productArray = null;
			
			DBObject dbj = null;  
			try 
			{
				cursor = coll.find() ;
			    while(cursor.hasNext()) 
			    {
					   item dressItem = new item() ;
                       dbj = cursor.next() ;
				       System.out.println(Integer.parseInt(dbj.get("id")+""));
				       dressItem.setId(Integer.parseInt(dbj.get("id")+"")) ;	       
				       dressItem.setName(dbj.get("name")+"") ;	       
				       dressItem.setDescription(dbj.get("description")+"") ;	       
				       dressItem.setQty(Double.parseDouble(dbj.get("qty")+"")) ;	       
				       dressItem.setPrice(Double.parseDouble(dbj.get("price")+"")) ;	 
				       itemsList.add(dressItem) ;
			    }

			       System.out.println(itemsList.size()) ;

			
			} 
			finally 
			{

				cursor.close();
		        System.out.println("JSON Object has been displayed Successfully ");

			}

		  
			 return  itemsList ;
	  }
	  
	  private static DBCollection getMongoDBConnection(String collectionName) 
	  {

		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );		
		DB db = mongoClient.getDB( "itemsdb" );		 
		DBCollection girlCollection =db.getCollection(collectionName);
		
		return girlCollection ;
	  }

	  public static void main(String[] args) throws Exception 
	  {
		
	     //  deleteMongodbItem("id", 3) ;
		 // updateItem(3) ;
		 //  getItemDetails(3) ;
		  //   insertDocument() ;
	         retrieveItemDetails() ;
		  
	  }

}
