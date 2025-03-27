package org.example;

public class NLPProcessCV implements Runnable{

    private final JobApplication application;

    public NLPProcessCV(JobApplication application){
        this.application = application;
    }
    @Override
    public void run(){
        System.out.println("Cv processing started for the uploaded cv");
        NLPProcessor.processCV(application.getCvText());

    }
}
