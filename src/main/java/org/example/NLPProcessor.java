package org.example;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class NLPProcessor {
    public static StanfordCoreNLP pipeline;
    static{
        //initializing the nlp pipeline
        Properties properties = new Properties();
        //properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        properties.setProperty("annotators", "tokenize, ssplit");
        pipeline = new StanfordCoreNLP(properties);
    }


    //processing text from the cv
    public static void processCV(String cvText){
        //creating an instance for the cv to be processed under
        CoreDocument document = new CoreDocument(cvText);

        //annotating the cv
        pipeline.annotate(document);

        //extracting key information from the cv
        for (CoreLabel token: document.tokens()){
            String word = token.word();
            String namedEntityRecTag = token.ner();
            String lemma = token.lemma();
            if(!namedEntityRecTag.equals("O")){
                System.out.println("Entity: " + word + "NER Tag: "+namedEntityRecTag);
            }
            //Printing out base form of each word
            System.out.println("Lemmantized word: "+ lemma);
        }
    }

    public static Candidate pullCandidateInfo(String cvText) {

        String[] lines = cvText.split("\\n");
        String name = "Unnamed";
        String email = "unknown@example.com";
        double rating = 1.0;
        // Default rating as placeholder

        Candidate candidate = new Candidate(name, 0, email, "default", "employee", rating);
        return candidate;

        //these are placeholder values as I've not set up nlp processing yet
    }
}


    //this method has

