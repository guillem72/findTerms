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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.glluch.findterms;

import java.util.ArrayList;
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
public class UtilsTest {
    
    public UtilsTest() {
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
     * Test of firstUpper method, of class Utils.
     */
    @Test
    public void testFirstUpper() {
        System.out.println("Utils firstUpper");
        String text = "firstLower";
        Utils instance = new Utils();
        String expResult = "FirstLower";
        String result = instance.firstUpper(text);
        assertEquals(expResult, result);
       
        
    }

    /**
     * Test of firstUpperForeach method, of class Utils.
     */
    @Test
    public void testFirstUpperForeach() {
        System.out.println("Utils firstUpperForeach");
        ArrayList<String> texts = new ArrayList<String>();
        ArrayList<String> expResult = new ArrayList<String>();
        texts.add("minus"); expResult.add("Minus");
        texts.add("Uperr"); expResult.add("Uperr");
        Utils instance = new Utils();
        
        ArrayList<String> result = instance.firstUpperForeach(texts);
        assertEquals(expResult, result);
       
       
    }

    /**
     * Test of firstLower method, of class Utils.
     */
    @Test
    public void testFirstLower() {
        System.out.println("Utils firstLower");
        String text = "FirstLower";
        Utils instance = new Utils();
        String expResult = "firstLower";
        String result = instance.firstLower(text);
        assertEquals(expResult, result);
        
      
    }

    /**
     * Test of replaceCaseInsensitive method, of class Utils.
     */
    @Test
    public void testReplaceCaseInsensitive() {
        System.out.println("replaceCaseInsensitive");
        String doc0 = "The landscape is beautiful";
        String old = "landscape";
        String newterm = "sea";
        Utils instance = new Utils();
        String expResult = "The sea is beautiful";
        String result = instance.replaceCaseInsensitive(doc0, old, newterm);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of containsCaseInsensitive method, of class Utils.
     */
    @Test
    public void testContainsCaseInsensitive() {
        System.out.println("containsCaseInsensitive");
        String doc = "The landscape is beautiful";
        String term = "iss";
        boolean expResult = false;
        boolean result = Utils.containsCaseInsensitive(doc, term);
        assertEquals(expResult, result);
        
       
    }

    /**
     * Test of stringContains method, of class Utils.
     */
    @Test
    public void testStringContains() {
        System.out.println("stringContains");
        String text = "I will come and meet you at the woods 123woods and all the woods";
        String term = "woods";
        boolean expResult = true;
        boolean result = Utils.stringContains(text, term);
        assertEquals(expResult, result);
        
       
    }
    
     /**
     * Test of stringContains method, of class Utils.
     */
    @Test
    public void testStringContains2() {
        System.out.println("stringContains2");
        String text = "I will come and meet you at the woods 123woods and all the woods";
        String term = "wood";
        boolean expResult = false;
        boolean result = Utils.stringContains(text, term);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of termReplace method, of class Utils.
     */
    @Test
    public void testTermAcronymAdd() {
        System.out.println("termReplace");
        String doc = "This phare is a test, and this is the testing word: atestar";
        String old_term = "test";
        String new_term = "great test";
        String expResult = "This phare is a great test, and this is the testing word: atestar";
        String result = Utils.termAcronymAdd(doc, old_term, new_term);
        assertEquals(expResult, result);
        
    }
    
}
