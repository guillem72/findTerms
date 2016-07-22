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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * This class reads a file with a list of acronyms and transform it in a hashmap. 
 * The file has to be in the format [Acronym] @ [Expandion] like, for instance, 
 * <em>"bps @ bits per second"</em>
 *  If an acronym has two or more possible expansion it will not be included and put in 
 *  <strong>multipleAcronimos</strong>
 * 
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class AcronymsReader implements iReader {

    /**
     * @var multipleAcronimos This hashmap have the acronyms with more than one 
     * expansion. The key is the acronym itself and the value is a list the all the possible 
     * expansions found.
     */
    private static HashMap<String, ArrayList<String>> multipleAcronimos = new HashMap<>();

        
    private static final Logger LOG = Logger.getLogger(AcronymsReader.class.getName());

    /**
     * get multipleAcronimos
     * @return multipleAcronimos, the hashmap containing the acronyms with more 
     * than one expansion(with key and values).
     */
    public HashMap<String, ArrayList<String>> getMultipleAcronimos() {

        return multipleAcronimos;
    }

    /**
     * get multipleAcronimos keys
     * @return the collection of acronyms with more than one value, without the values.
     */
    public Set keyMA() {
        return multipleAcronimos.keySet();
    }

    /**
     * get multipleAcronimos length
     * @return the number of acronyms with more than one value.
     */
    public Integer numMA() {
        return multipleAcronimos.size();
    }

    /**
     * Show in console the list of acronyms with multiple expansion
     */
    public void showMultipleAcronimos() {
        Set keys = multipleAcronimos.keySet();
        for (Object key0 : keys) {
            String key = (String) key0;
            String mess = key + ":";
            ArrayList<String> al = (ArrayList<String>) multipleAcronimos.get(key0);
            for (String val : al) {
                mess += ", " + val;
            }
            System.out.println(mess);
        }
    }

    /**
     * Process a file with acronyms and return them in a more computable format.
     * 
     * @param inputFileName The file filled with the acronyms and their expansion. 
     * "@" is used for split the term and their meaning.
     * @return a hashMap with &lt; Acronym,Expansion &gt;
     */
    @Override
    public HashMap<String, String> reader(String inputFileName) {
        HashMap<String, String> acron = new HashMap<>();
        File facros = new File(inputFileName);
        ArrayList<String> values;
        try {
            String lacros = FileUtils.readFileToString(facros, "utf-8");
            String[] lines = lacros.split("\n");
            for (String l : lines) {
                String[] temp = parseLine(l);
                if (!StringUtils.isEmpty(temp[0])) {
                    String acronym=temp[0];
                    if (acron.containsKey(acronym)) {//the acronym is in the list
                        //System.out.println("Multiple trobat "+temp[0]+": "+acron.get(temp[0])+" + "+temp[1]);
                        values = new ArrayList<>();
                        values.add(acron.get(acronym));
                        values.add(temp[1]);

                        acron.remove(acronym);
                        values.add(acron.get(temp[0]));

                        multipleAcronimos.put(acronym, values);
                    } else//  if (acron.containsKey(temp[0]))
                    if (multipleAcronimos.containsKey(acronym)) { //the acronym is multiple
                        values = multipleAcronimos.get(acronym);
                        values.add(temp[1]);
                        multipleAcronimos.replace(acronym, values);
                    } else {// if(multipleAcronimos.containsKey(temp[0]))
                        acron.put(acronym, temp[1]); 
                       // acron.put(temp[0], temp[1]); 
                    }//else if(multipleAcronimos.containsKey(temp[0]))//end else if (acron.containsKey(temp[0]))

                }//if (!StringUtils.isEmpty(temp[0]))
            }

        } catch (IOException ex) {
            Logger.getLogger(AcronymsReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return acron;
    }

    /**
     * Parse one line in the form [Acronym] @ [Expansion]
     * @param line0 the line 
     * @return An array made of two terms. The first is the acronym and the second is 
     * its expansion.
     */
    public String[] parseLine(String line0) {
        String[] res = new String[2];
        res[0] = "";
        res[1] = "";
        String line = line0.trim();
        if (StringUtils.containsIgnoreCase(line, "@")) {
            String[] parts = line.split("@");
            if (parts.length > 1) {
                res[0] = parts[0].trim();
                res[1] = parts[1].trim();
            }
        }

        return res;
    }

}
