import initializations.*;
import com.mongodb.*;
import java.net.UnknownHostException;
import methods.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @Ioanna Kyriazidou
 */
public class Main {

    public static void main(String[] args) throws TwitterException, MongoException, UnknownHostException {
        
        // 1st class - initialize Twitter objects
        InitializeTwitter taskObj = new InitializeTwitter();
        ConfigurationBuilder cb = taskObj.returnTwitterBuilder();
        
        // 2nd class - initialize Mongo Database
        InitializeMongo database = new InitializeMongo();
        DBCollection items = database.initMongoDB(cb);
        Twitter twitter = database.returnTwitterConstructor(cb);
        
        // 3rd class - 1st method - get tweets by query
        getTweets tweetsQuery = new getTweets();
        tweetsQuery.getTweetsByQuery(items, twitter);
        
        // 3rd class - 2nd method - get tweets by user
        //tweetsQuery.getTweetByUser(items, twitter, 4503597479886593L);
    }
}
