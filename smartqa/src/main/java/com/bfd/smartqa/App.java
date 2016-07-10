package com.bfd.smartqa;

import org.apache.log4j.Logger;

import com.bfd.smartqa.ws.Server;

/**
 * Hello world!
 *
 */
public class App 
{
	public static Logger logger = Logger.getLogger(App.class);  
	
    public static void main( String[] args ) throws Exception
    {
        GlobalHolder.init(); 
        GlobalHolder.getLogger().info("SmartQA server started");

        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 10001;
        }
        new Server(port).run();
    }
}
