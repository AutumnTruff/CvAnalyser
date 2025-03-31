package org.example;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;

public class NLPProcessor {
    class CVAnalysisResult {
        String name;
        String email;
        List<String> skills;
        List<String> organizations;
        List<String> education;
        List<Integer> yearsOfExperiance;

        public CVAnalysisResult(String name, String email){
            this.name = name;
            this.email = email;
        }
    }
    public static StanfordCoreNLP pipeline;
    static{
        //initializing the nlp pipeline
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        properties.setProperty("pos.model", "models/english-left3words-distsim.tagger");
        properties.setProperty("ner.model", "models/ner/english.all.3class.caseless.distsim.crf.ser.gz");
        //properties.setProperty("annotators", "tokenize, ssplit");
        pipeline = new StanfordCoreNLP(properties);
    }


    //processing text from the cv
    public static void processCV(String cvText){
        //creating an instance for the cv to be processed under
        CoreDocument document = new CoreDocument(cvText);

        //annotating the cv
        pipeline.annotate(document);

        //extracting key information from the cv
        for (CoreLabel generalToken: document.tokens()){
            String word = generalToken.word();
            String namedEntityRecTag = generalToken.ner();
            String lemma = generalToken.lemma();
            String pos = generalToken.get(CoreAnnotations.PartOfSpeechAnnotation.class);

            if(!namedEntityRecTag.equals("O")){
                System.out.println("Entity: " + word + "NER Tag: "+namedEntityRecTag);
            }

            //find the candidates name in the cv
            String candidatesName = "";
            for (CoreEntityMention namedEntities : document.entityMentions()){
                if(namedEntities.entityType().equals("PERSON")){
                    candidatesName = namedEntities.text();
                    //I am assuming that the first name in the cv is the candidates name
                }
            }
            //finding the email of the candidate
            String candidateEmail ="";
            for (CoreLabel TokenForEmail : document.tokens()) {
                String email = TokenForEmail.word();
                if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                    candidateEmail = email;
                }
            }
            //decided to have each search loop have its own token for readability


            //now to store the main nlp output to be used in the ranking system
            return CVAnalysisResult;



        }
    }
    // TODO: restructure code so that the name and email for the candidates application come from the nlp

    public static Candidate pullCandidateInfo(String cvText) {

        String[] lines = cvText.split("\\n");
        String name = "Unnamed";
        String email = "unknown@example.com";
        double rating = 1.0;
        // Default rating as placeholder

        Candidate candidate = new Candidate(name, 0, email, "default", "employee", rating);
        //return CVAnalysisResult;
        return ;

        //these are placeholder values as I've not set up nlp processing yet
    }
}


    //this method has

