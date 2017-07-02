import org.asteriskjava.fastagi.DefaultAgiServer;

/**
 * Created by andrea on 30/06/17.
 */
public class Main {
    public static void main(String[] args) throws Exception
    {
        final DefaultAgiServer server;
        server = new DefaultAgiServer();
        server.startup();
    }
}
