package com.glluch.findterms;
/*
 * Copyright (C) 2016 Guillem LLuch Moll guillem72@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
import com.glluch.utils.Out;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class FindTermsMain {

    /**
     * Not implemented yet.
     * @param args the command line arguments
     * args0 the taxonomy
     * args1 the positions
     * args2 the doc
     * @throws java.io.FileNotFoundException When reads a file
     *
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filename="resources/ecfAcronymsExpanded.txt";
        //gtest();
        
        //related();
        //findAndCount();
        //findAll();
        findAndCountFromFile(filename);
         
    }
    
      public static void findAll(){
          String doc="Advanced Security Practitioner (CASP) ---> The CASP certification proves "
                  + "competency in enterprise security; risk management; research and analysis; "
                  + "and integration of computing, communications, and business disciplines. - "
                  + "conceptualize, design, and engineer secure solutions across complex enterprise "
                  + "environments- apply critical thinking and judgment across a broad spectrum of "
                  + "security disciplines- propose and implement solutions that map to enterprise "
                  + "drivers- enterprise security- risk management";
          termsAndRelated ta=new termsAndRelated();
          HashMap<String,Double> ieee=ta.find(doc);
          
          Out.stringdoublemap(ieee);
      }

    public static void related(){
       // HashMap <String,ArrayList<String>> related;
       ArrayList related;
        Vocabulary.get();
    Surrogate surro=new Surrogate(Vocabulary.jenaModel);
    surro.setTerm("path planning");
    related=surro.getSurrogates();
    System.out.println(related.toString());
    }
    
    
    public static void gtest() throws IOException{
        GenerateTestsResults gtr=new  GenerateTestsResults ();
        //gtr.vocabularyGetResults();
        //gtr.vocabulartListTermsResult();
        //gtr.vocabularyFindAcronymsResults();
        gtr.findTermsFoundResults();
    }
    
    public static void find(){
        ArrayList<String> res;
        //Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder=new FindTerms();
        FindTerms.vocabulary=Vocabulary.get();
        //System.out.println(Vocabulary.listTerms());
        String doc="Linux Evolution and Popular Operating Systems and cognitive radio";
        res=finder.found(doc);
        System.out.println(res);
    }
    
       public static void findAndCount(){
        HashMap<String, Integer> res;
        //Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder=new FindTerms();
        FindTerms.vocabulary=Vocabulary.get();
        //System.out.println(Vocabulary.listTerms());
        String doc="Analog circuits and Analog circuits and Analog "
                + "integrated circuits and Analog circuits and Analog processing circuits,"
                + "maybe circuits and, of course analog ";
        res=finder.foundAndCount(doc);
        System.out.println(res);
    }

    private static void findAndCountFromFile(String filename) throws IOException {
        HashMap<String, Integer> res;
        String targetFile="resources/ieeeTermsInECF.csv";
        //Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder=new FindTerms();
        FindTerms.vocabulary=Vocabulary.get();
        
        String docText = FileUtils.readFileToString(new File(filename));
        res=finder.foundAndCount(docText);
        String resCsv=Out.csv(res);
        FileUtils.writeStringToFile(new File(targetFile), resCsv);
        
        
    }
    
    
    
    
}
