package com.store.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.SpringApplication;

//import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Controller
public class CRUDController {

    private static final String template = "Hello, %s!";
    
    private static final Logger logger = LogManager.getLogger(CRUDController.class);
    
    private final AtomicLong counter = new AtomicLong();


    @DeleteMapping("/item/{id}")
    public  ResponseEntity<Object>  deleteStudent (@PathVariable int id) {

    	logger.info("Delete User : {}", id);

        System.out.println(" user id "+ id +" has been deleted" );

        StoreMongoDB.deleteMongodbItem(id) ;
        
    	return  new ResponseEntity<Object>(id, HttpStatus.OK);
    }
    
    @GetMapping("/items")
    public ResponseEntity<Object> retrieveAllStudent() throws Exception {
    	
    	logger.info("Get User : {}" );
        System.out.println(" user id" );

        logger.debug("Hello world - debug log");
        logger.info("Hello world - info log");
        logger.warn("Hello world - warn log");
        logger.error("Hello world - error log");

        List<item>  itemList = new ArrayList<item>()  ;
        itemList =    StoreMongoDB.retrieveItemDetails();

        return  new ResponseEntity<Object>(itemList, HttpStatus.OK);

    }
    
    @GetMapping("/item/{id}")
    public ResponseEntity<Object> retrieveStudent(@PathVariable int id) {
    	
    	logger.info("Get User : {}", id);
        System.out.println(" user id" + id);
        Object items =    StoreMongoDB.getItemDetails(id);

        
        return  new ResponseEntity<Object>(items, HttpStatus.OK);
    }
    @PostMapping("/item")
    public ResponseEntity<Object> createStudent(@RequestBody item user) {

    	logger.info("Creating User : {}", user.getName()   );

        System.out.println(" user id" + user);
    	
        StoreMongoDB.insertDocument(user) ;

        return  new ResponseEntity<Object>(user, HttpStatus.OK);

    }
    
    @PutMapping("/item")
    public ResponseEntity<Object> updateStudent(@RequestBody item user) {
    	logger.info("user has been updated  : {}", user.getDescription()   );

        String 	studentId = "" ;
        System.out.println(" user id has been updated" + user.getId());

        logger.debug("mongo db update - debug log");
        logger.info("mongo db update - info log");
        logger.warn("mongo db update - warn log");
        logger.error("mongo db update - error log");

        StoreMongoDB.updateItem(user) ;
        
    	return  new ResponseEntity<Object>(user.getId(), HttpStatus.OK);
    }
    public static void main(String[] args) {
        SpringApplication.run(CRUDController.class, args);
    }

    
}