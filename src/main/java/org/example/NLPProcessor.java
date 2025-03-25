package org.example;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;

public class NLPProcessor {
    public static StanfordCoreNLP pipeline;
    static{
        //initializing the nlp pipeline
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
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
    }

    //this method has

