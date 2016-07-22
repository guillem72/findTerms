/*
 * Copyright (C) 2016 Guillem LLuch Moll guillem72@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.glluch.findterms;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.jena.rdf.model.Model;

/**
 * Aux class for generate several file to tests the classes
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class GenerateTestsResults {

    private Model model;

    public GenerateTestsResults() {
       
    }
    
    //DataMapper
    public GenerateTestsResults(Model model) {
        this.model = model;
    }

    

    //RDFReader
    public void readerResult() throws IOException {
        RDFReader lector = new RDFReader();
        String filename;
        filename = "resources/test/miniReasoner.owl";
        Model model1;
        model1 = lector.reader(filename);
        System.out.println("readerResult():");
        System.out.println(model1.toString());
        FileUtils.writeStringToFile(new File("resources/test/mini_model.txt"), model1.toString(), "utf8");
    }

 

   

    
    public void findTermsFoundResults() throws IOException{
         ArrayList<String> res;
        //Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder=new FindTerms();
        FindTerms.vocabulary=Vocabulary.get();
        String docFile="resources/test/findtermsdoc.txt";
         String doc=FileUtils.readFileToString(new File(docFile),"utf8");
        ArrayList<String> f=finder.found(doc);
        System.out.println(f);
        FileUtils.writeStringToFile(new File("resources/test/findtermsdocResult.txt"),
                f.toString(), "utf8");
    }
    
    //Class Vocabulary
    public void  vocabularyGetResults() throws IOException{
        ArrayList <String> v=Vocabulary.get();
        File target=new File("resources/test/vocabularyGetResults.bin");
        byte[] vs=SerializationUtils.serialize(v);
        FileUtils.writeByteArrayToFile(target, vs);       
    }
    
    public void vocabularyFindAcronymsResults() throws IOException{
        Vocabulary.get();
    HashMap<String, String> v = Vocabulary.findAcronyms();
     File target=new File("resources/test/vocabularyFindAcronymsResults.bin");
        byte[] vs=SerializationUtils.serialize(v);
        FileUtils.writeByteArrayToFile(target, vs);   
    
    }
    
    public void vocabulartListTermsResult() throws IOException{
          Vocabulary.get();
          String res=Vocabulary.listTerms();
          File target=new File("resources/test/vocabularyListTermsResult.txt");
          FileUtils.writeStringToFile(target, res);
          
    }
    
    //AcronymsReader
    public void acronymsReaderResult() throws IOException{
        AcronymsReader ar=new AcronymsReader();
        HashMap<String, String> acron=ar.reader("resources/acrosWikiArroba.txt");
        File target=new File("resources/test/acronymsReaderResult.bin");
        byte[] as=SerializationUtils.serialize(acron);
        FileUtils.writeByteArrayToFile(target, as);
        
    }
    
    //General functions
    public void write(String filename,Collection c) throws IOException{
        File target=new File(filename);
        byte[] vs=SerializationUtils.serialize((Serializable) c);
        FileUtils.writeByteArrayToFile(target, vs);   
    }

}
