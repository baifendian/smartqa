package com.bfd.smartqa.etl;

import com.bfd.smartqa.etl.scheduler.AlgoScoreRunner;
import com.bfd.smartqa.etl.scheduler.AlgoTagRunner;
import com.bfd.smartqa.etl.watchman.Runner;


/**
 * Main Process
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	GlobalHolder.init();
    	Runner watchman = new Runner();
    	AlgoScoreRunner scoreRunner = new AlgoScoreRunner();
    	AlgoTagRunner tagRunner = new AlgoTagRunner();
    	
    	watchman.start();
    	scoreRunner.start();
    	tagRunner.start();
    }
}
