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
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
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
public class VocabularyTest {
    
    public VocabularyTest() {
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
     * Test of get method, of class Vocabulary.
     */
    @Test
    public void testGet() throws IOException {
        System.out.println("get");
        File file=new File("resources/test/vocabularyGetResults.bin");
        byte[] v=FileUtils.readFileToByteArray(file);
        ArrayList<String> expResult = SerializationUtils.deserialize(v);
        ArrayList<String> result = Vocabulary.get();
        assertEquals(expResult, result);
 
    }

    /**
     * Test of findAcronyms method, of class Vocabulary.
     */
    @Test
    public void testFindAcronyms() throws IOException {
        System.out.println("findAcronyms");
         Vocabulary.get();
        File file=new File("resources/test/vocabularyFindAcronymsResults.bin");
        byte[] v=FileUtils.readFileToByteArray(file);
        HashMap<String, String> expResult = SerializationUtils.deserialize(v);
        HashMap<String, String> result = Vocabulary.findAcronyms();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of buildAcronim method, of class Vocabulary.
     */
    @Test
    public void testBuildAcronim() {
        System.out.println("buildAcronim");
        String term0 = "Computer Vission or Similar";
        String expResult = "CVOS";
        Vocabulary.get();
        String result = Vocabulary.buildAcronim(term0);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of listTerms method, of class Vocabulary.
     */
    @Test
    public void testListTerms() throws IOException {
        System.out.println("listTerms");
        File res=new File("resources/test/vocabularyListTermsResult.txt");
        Vocabulary.get();
        String expResult = FileUtils.readFileToString(res);
        String result = Vocabulary.listTerms();
        assertEquals(expResult, result);
       
    }
    
}
