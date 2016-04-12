package methods;
/**
 * @Ioanna Kyriazidou
 */
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.json.DataObjectFactory;
import com.mongodb.util.JSON;
import static java.lang.Integer.parseInt;
import java.util.*;
import java.util.logging.*;
import twitter4j.TwitterException;

public final class getTweets {
    VerifiedAccount va = new VerifiedAccount();
    /* constructor */
    public getTweets() {
    }
    private List<Status> makeQuery(Twitter twitter) throws TwitterException {
        /* Query */
        Query query = new Query("#wjhjjh");
        query.setCount(100);
        QueryResult result;
        result = twitter.search(query);
        System.out.println("Getting Tweets...");
        List<Status> tweets = result.getTweets();
        return tweets;
    }
    private DBCursor mongoQueries(DBCollection items, String str) {
                BasicDBObject field = new BasicDBObject(str, true).append("_id", false);
                BasicDBObject neQuery = new BasicDBObject();
                // Regular Expression for mentions
                neQuery.put("text", new BasicDBObject("$regex", "@"));
                DBCursor cursor = items.find(neQuery, field);
                return cursor;
    }
    private void extractEntities(DBObject mention, String input, List<List<Integer>> idListBig ) {
        /* Casting DBObject to String */
        Object entities = mention.get(input);
        //System.out.println("\n2. " +entities);
        String str = entities.toString();
        //System.out.println("\n3. " +str);
        List<String> myList = Arrays.asList(str.split(","));
        //System.out.println("\n4. "+myList);
        List<Integer> idList = new ArrayList<Integer>();
        for (int i = 0; i < myList.size(); i++) {
            int id = Integer.parseInt(myList.get(i).replaceAll("[^0-9]", ""));
            //System.out.println("\n5. "+id);
            idList.add(id);
            //System.out.println("\n6. "+myINTList);
        }
            idListBig.add(idList);
            System.out.println("\n7. List of mentions " + idList);
    }
    private String exactUser(DBObject mention, String input) {
        Object user = mention.get(input);
        //System.out.println("\n2. " +user);
        String str = user.toString();
        //System.out.println("\n3. " +something);
        int length = str.length();
    String result = "";
    for (int i = 0; i < length; i++) {
        Character character = str.charAt(i);
        if (Character.isDigit(character)) {
            result += character;
        }
    }
    return result;
    }
    private void callVerification(List<List<Integer>> idListBig, Twitter twitter) {
        for (int i = 0; i < idListBig.size(); i++) {
            va.checkVerifiedAccount(twitter, idListBig.get(i));
        }
    }
    /* rawjson to mongodb, quering to mongodb, */
    public void getTweetsByQuery(DBCollection items, Twitter twitter) {
        List<List<Integer>> idListBig = new ArrayList<List<Integer>>();
        try {
            /* Query */
            List<Status> tweets = makeQuery(twitter);
            /* Loop for multiple json files into MongoDB*/
            for (Status status : tweets) {
                /* get json file*/
                String rawJSON = DataObjectFactory.getRawJSON(status);
                /* Parse json */
                BasicDBObject dbObject = (BasicDBObject) JSON.parse(rawJSON);
                items.insert(dbObject);
                DBCursor cursor = mongoQueries(items, "entities.user_mentions.id");
                DBCursor cursor2 = mongoQueries(items, "user.id_str");
                int count = 0;
                /* Module of mentions */
                while (cursor.hasNext()) {
                    count++;
                    DBObject mention = cursor.next();
                    extractEntities(mention, "entities", idListBig);
                }
                /* Module of each user */
                while (cursor2.hasNext()) {
                    count++;
                    DBObject mention = cursor2.next();
                    System.out.println("\nEftase ston user!");
                    String id = exactUser(mention, "user");
                    System.out.println("\nID USER: "+id);
                    long user = Long.parseLong(id);
                    System.out.println("\nID USER LONG: "+user);
                    getTweetByUser(items, twitter, user);
                }
               
            }                  
        } catch (Exception e) {
            System.out.println("MongoDB Connection Error : " + e.getMessage());
        }
        System.out.println("Number of records:" + items.count());
        callVerification(idListBig, twitter);
        //System.exit(0);
    }
    /* rawjson to mongodb, quering to mongodb, */
    public void getTweetByUser(DBCollection items, Twitter twitter, long user_id) {
        try {
            List<Status> statusList = twitter.getUserTimeline(user_id);
            for (Status statuses : statusList) {
                System.out.println("\n20 Latest tweets of user with id:" + user_id + "are: "+ statuses.toString());
            }
            //List<Status> statusList = twitter.getUserTimeline(cursor.toString());     
            // for (Status statuses : statusList) {
            //  System.out.println("latest tweets:" + statuses.toString());
            //  }    
        } catch (TwitterException ex) {
            Logger.getLogger(getTweets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
