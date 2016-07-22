/*
 * Copyright (C) 2016 Guillem LLuch Moll guillem72@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
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
package com.glluch.findterms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class FindTermsTest {
    
    public FindTermsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of found method, of class FindTerms.
     */
    @Test
    public void testFound() throws IOException {
         ArrayList<String> res;
        //Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder=new FindTerms();
        FindTerms.vocabulary=Vocabulary.get();
        String docFile="resources/test/findtermsdoc.txt";
        String doc=FileUtils.readFileToString(new File(docFile),"utf8");
        ArrayList<String> f=finder.found(doc);
        String expResult=f.toString();
        String result=FileUtils.readFileToString(
                new File("resources/test/findtermsdocResult.txt"),"utf8");
        assertEquals(expResult, result);
       
    }

    
    
}
