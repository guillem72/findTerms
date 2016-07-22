/*
 * Copyright (C) 2016 Guillem LLuch Moll <guillem72@gmail.com>
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillem LLuch Moll <guillem72@gmail.com>
 */
public class DataMapperTest {
    
    private Model model;
    
    public DataMapperTest() {
        String filename = "resources/test/miniReasoner.owl";
        this.model = ModelFactory.createDefaultModel();
        RDFReader lector = new RDFReader();
        this.model = lector.reader(filename);
        System.out.println("\nDataMapper class Test");
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
     * Test of getUris method, of class DataMapper.
     * @throws java.io.IOException
     */
    @Test
    public void testGetUris() throws IOException {
       //TODO
        
    }

    /**
     * Test of queryLabel method, of class DataMapper.
     */
    @Test
    public void testQueryLabel() {
        System.out.println("queryLabel Test");
        String term_uri = "http://glluch.com/ieee_taxonomy#Artificial_intelligence";
        DataMapper instance = new DataMapper();
        instance.setModel(this.model);
        String expResult = "SELECT ?label WHERE {<http://glluch.com/ieee_taxonomy#Artificial_intelligence> <http://www.w3.org/2000/01/rdf-schema#label>  ?label}";
        String result = instance.queryLabel(term_uri).trim();
        //System.out.println("Res="+result);
        //System.out.println("Esp="+expResult);
        int diff=result.compareTo(expResult);
        //System.out.println("diff="+diff);
        assertEquals(diff, 0);
        
       
    }

    /**
     * Test of getLabel method, of class DataMapper.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel Test");
        String uri = "http://glluch.com/ieee_taxonomy#Artificial_intelligence";
        DataMapper instance = new DataMapper();
        instance.setModel(this.model);
        String expResult = "Artificial intelligence";
        String result = instance.getLabel(uri);
        assertEquals(expResult, result);
       
    }
    
}
