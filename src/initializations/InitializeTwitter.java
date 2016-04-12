package initializations;
/**
 *
 * @Ioanna Kyriazidou
 */
import twitter4j.conf.ConfigurationBuilder;

public final class InitializeTwitter {
    
    private static final String ACCESS_TOKEN = "2999643856-jZxniwexoSq6af0NX3aHnKLITvQufIkA9ArLGfY";
    private static final String ACCESS_TOKEN_SECRET = "jEcVM5SM22s1tlj4Ve3Tn5GhG7af4Yu4sp0lZkEOVuDyk";
    private static final String CONSUMER_KEY = "akqzjginDHVhUV5wFZkpRbihz";
    private static final String CONSUMER_SECRET = "cxRESqZy0tlPoLaSrvbFgXEeMMpuWODtuJ5KsX8w5SJx3xCkYt";

    /* constructor */
    public InitializeTwitter() {
        
    }
    
    public ConfigurationBuilder returnTwitterBuilder() {
        
        // create new db to store the tweets
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthAccessToken(ACCESS_TOKEN);
        cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        cb.setOAuthConsumerKey(CONSUMER_KEY);
        cb.setOAuthConsumerSecret(CONSUMER_SECRET);
        
        return cb;
    }    
    
}