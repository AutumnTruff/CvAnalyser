package org.example;

public class NLPThreadCreator {

    public static void createThread(JobApplication application){

        NLPProcessCV ProcessingThread = new NLPProcessCV(application);
        Thread thread = new Thread(ProcessingThread);
        //                              ^=object of the new instance (one per cv being processed)
        thread.setDaemon(false);
        thread.start();

        /*
        Creating a new thread instance to tackle the Cv processing away from the user to make sure
        the user isn't impacted by the performance of the nlp.

        cv processing must not be interrupted by the program closing so the threads daemon is set to false.
        */

    }

}
