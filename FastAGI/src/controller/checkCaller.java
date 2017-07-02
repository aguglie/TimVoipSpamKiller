package controller;

import org.asteriskjava.fastagi.*;
import utils.Stats;
import utils.TellowScore;

/**
 * Created by andrea on 30/06/17.
 */
public class checkCaller extends BaseAgiScript{
    public void service(AgiRequest request, AgiChannel channel)
            throws AgiException
    {
        //If it's an internal number (starts with *), text stats
        if (request.getCallerIdNumber().substring(0,1).equals("*")){
            answer();
            sayAlpha("Bloccati ");
            sayNumber(Stats.getRejectedCallsCounter()+"");
            hangup();
            return;
        }

        //If it's an external number, check if it's spam
        if (TellowScore.getScore(request.getCallerIdNumber()) > 6){
            Stats.increaseRejectedCallsCounter();
            answer();
            hangup();
            return;
        }
    }
}
