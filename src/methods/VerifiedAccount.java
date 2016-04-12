package methods;

/**
 *
 * @Ioanna Kyriazidou
 */
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VerifiedAccount {

    /* constructor */
    public VerifiedAccount() {

    }

    /* Check for verified accounts by id*/
    public void checkVerifiedAccount(Twitter twitter, List<Integer> idList) {
        try {
            for (int i=0;i < idList.size();i++) {
                int tweetID = idList.get(i);
                //System.out.println("\nCurrent ID: " + tweetID);
            
            List<Status> statusList = twitter.getUserTimeline(tweetID);
            for (Status statuses : statusList) {
                if (statuses.getUser().isVerified() == true) {
                    int verified_account_id = tweetID;
                    //System.out.println("\nThe account " + statuses.getUser().getScreenName() + " with id " + verified_account_id + " is verified.");
                } 
                else {
                   // System.out.println("\nThe account " + statuses.getUser().getScreenName() + " with id " + tweetID + " is unverified.");
                }
                // System.out.println("verified"+statuses.getUser().isVerified());
                // System.out.println("user name" + statuses.getUser().getScreenName());
                //  System.out.println("latest tweets:" + statuses.toString());
            }
            }
        } catch (TwitterException ex) {
            Logger.getLogger(VerifiedAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
