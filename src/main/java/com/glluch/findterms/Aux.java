/*
 * Copyright (C) 2016 Guillem LLuch Moll <guillem72@gmail.com>
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
 * along with this program.  If not, see http://www.gnu.org/licenses.
 */
package com.glluch.findterms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.apache.commons.io.FileUtils;

/**
 * This is fit-all class to put some auxiliar code that is use only one time.
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Aux {

    

    /**
     * Given a colletion of acronyms write a list of links for their verificacion.
     * 
     * @param ma a set with all the acronyms to be checked manualy in web
     * @throws IOException error in file.
     */
    public void writeFileAcronymsChecker(Set <String> ma) throws IOException{
        String baseurl="http://www.acronymfinder.com/Information-Technology/";
        String res="";
        File target=new File("resources/acronymsChecker.txt");
        ArrayList<String>links=new ArrayList<>();
        for(String term:ma){
            res+=baseurl+term+".html\n";
            links.add(baseurl+term+".html");
        }
        FileUtils.writeLines(target, links);
    }
    
    public static ArrayList<String> joinHashMapValues(HashMap <String,ArrayList<String>> cads){
        ArrayList<String> res=new ArrayList<>();
        Set keys=cads.keySet();
        for (Object key0:keys){
            String key=(String) key0;
            res.addAll(cads.get(key));
            
        }
        return res;
    }
}
