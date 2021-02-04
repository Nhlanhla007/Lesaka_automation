package Logger;

import org.apache.log4j.Logger;


/**
 * <copyright file="Log.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class Log {
    //Initialize Log4j instance
    private static Logger Log = Logger.getLogger(Log.class.getName());

    //We can use it when starting tests
    public static void startLog (String testClassName){
        Log.info("Test is Starting...");
    }
    
    public static Logger getLogData(String className){
    	 return Logger.getLogger(className);
    	 }

    //We can use it when ending tests
    public static void endLog (String testClassName){
        Log.info("Test is Ending...");
    }

    //Info Level Logs
    public static void info (String message) {
        Log.info(message);
    }

    //Warn Level Logs
    public static void warn (String message) {
        Log.warn(message);
    }

    //Error Level Logs
    public static void error (String message) {
        Log.error(message);
    }

    //Fatal Level Logs
    public static void fatal (String message) {
        Log.fatal(message);
    }

    //Debug Level Logs
    public static void debug (String message) {
        Log.debug(message);
    }
}
