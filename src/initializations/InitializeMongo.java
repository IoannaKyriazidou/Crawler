/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package initializations;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author user
 */
public final class InitializeMongo {

    /* constructor */
    public InitializeMongo() {
        
    }

    public DBCollection initMongoDB(ConfigurationBuilder cb) throws MongoException, UnknownHostException {

        System.out.println("Connecting to Mongo DB..");
        Mongo mongo;
        mongo = new Mongo("127.0.0.1");
        //onoma basis
        DB db = mongo.getDB("mongo");

        DBCollection items = db.getCollection("coll");
        System.out.println("MongoDB is OK");
        
        return items;
    }
    
    public Twitter returnTwitterConstructor(ConfigurationBuilder cb) throws MongoException, UnknownHostException {
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        
        return twitter;
    }
    
}
