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
package com.glluch.findterms;

import com.glluch.utils.StringsThings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import org.apache.commons.lang3.StringUtils;

/**
 * Find terms in a text.
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class FindTerms {

    /**
     * The list of terms to be search for.
     */
    public static ArrayList<String> vocabulary;

    /**
     * Return all the terms from the vocabulary found in doc0. Note that return
     * the larger term found, so for "Management System" it returns one term,
     * not "Management" and "System".
     *
     * @param doc0 The doc where the terms will be searched
     * @return A list made by the found terms.
     */
    public ArrayList<String> found(String doc0) {
        //System.out.println("FindTerms.found()");
        String doc = doc0.toLowerCase();
        TermsCount hits = new TermsCount();
        for (String candi0 : vocabulary) {
            String candi = candi0.toLowerCase();
            if (Utils.stringContains(doc, candi)) {

                String[] parts = doc.split(candi);

                hits.put(candi, parts.length - 1);

            }
        }
        //System.out.println("hits=" + hits.toString());
        return removeInsideTerms(hits);
    }

    /**
     * Return all the terms from the vocabulary found in doc0. Note that return
     * the larger term found, so for "Management System" it returns one term,
     * not "Management" and "System".
     *
     * @param doc0 The doc where the terms will be searched
     * @return A map made by the found terms and their occurences.
     */
    public TermsCount foundAndCount(String doc0) {
        //System.out.println("FindTerms.found()");
        String doc = doc0.toLowerCase();
        TermsCount hits = new TermsCount();
        for (String candi0 : vocabulary) {
            String candi = candi0.toLowerCase();
            int matches = StringUtils.countMatches(doc, candi);
            if (matches > 0) {

                hits.put(candi, matches);

            }
        }
        //System.out.println("hits=" + hits.toString());
        removeInsideTerms(hits);
        return hits;
    }

    /**
     * Internal function which removes a term that is part of another term.
     * Return the occurences of the term, too.
     *
     * @param f An array term-&gt; hits of the term
     * @return a map term --&gt; num_occurences without the non proper terms
     * deleted.
     */
    protected HashMap<String, Integer> removeInsideTermsAndCount(TermsCount f) {
        
        Stack pila = new Stack();

        Set keys = f.keySet();//all term found, term form part of other term, too
        pila.addAll(keys);
        Collections.sort(pila,new StringsThings());
        //Out.pS(pila);
        /*
         Pick the first element and compares it with every one in the rest of the elements
         */
        while (!pila.empty()) {
            String t1 = (String) pila.pop();
            Stack rest = (Stack) pila.clone();
            while (!rest.empty()) {

                String t2 = (String) rest.pop();
                if (f.containsKey(t1) && f.containsKey(t2)) {
                    //if one term t2 is in the other t1 and apper the same number of times,  
                    //it means than t2 is not a proper term, only is part of a larger term so
                    //it has to be removed (from f). 
                    //The aim of the else statement is to mantain a correct number of 
                    //occurences for the included (in a larger term) terms, for example
                    // "circuits and analog circuits" the word circuits appers two times but
                    // one time is part of another term and only one time is a proper term.
                    if (t1.contains(t2) && f.get(t1) >= f.get(t2)) {//TODO throw error if f.get(t1)>f.get(t2)
                        //.remove(t2);
                        //Out.p("t1.contains(t2) && f.get(t1) >= f.get(t2)");
                        //Out.p("t1 is "+t1+","+f.get(t1)+" times");
                        //Out.p("t2 is "+t2+","+f.get(t2)+" times");
                        f.remove(t2);
                    } else {//if (t1.contains(t2) && f.get(t1)>=f.get(t2))
                        if (t1.contains(t2) && f.get(t1) < f.get(t2)) {
                        //Out.p("t1.contains(t2) && f.get(t1) < f.get(t2)");
                        //Out.p("t1 is "+t1+","+f.get(t1)+" times");
                        //Out.p("t2 is "+t2+","+f.get(t2)+" times");
                            
                            f.replace(t2, f.get(t2) - f.get(t1));
                        }
                    }//else
                    if (t2.contains(t1) && f.get(t2) >= f.get(t1)) {//TODO throw error if f.get(t2)>f.get(t1)
                        //Out.p("t2.contains(t1) && f.get(t2) >= f.get(t1)");
                        //Out.p("t1 is "+t1+","+f.get(t1)+" times");
                        //Out.p("t2 is "+t2+","+f.get(t2)+" times");
                        f.remove(t1);
                    } else {//if (t2.contains(t1) && f.get(t2)>=f.get(t1))
                        if (t2.contains(t1) && f.get(t2) < f.get(t1)) {
                        //Out.p("t2.contains(t1) && f.get(t2) >= f.get(t1)");
                        //Out.p("t1 is "+t1+","+f.get(t1)+" times");
                        //Out.p("t2 is "+t2+","+f.get(t2)+" times");
                            f.replace(t1, f.get(t1) - f.get(t2));
                        }
                    }//else
                }// if (f.containsKey(t1) && f.containsKey(t2))
            }//while (!rest.empty())
        }

        return f;
    }

    /**
     * Internal function which removes a term that is part of another term.
     *
     * @param f An array term-&gt; hits of the term
     * @return A list without the non proper terms deleted.
     */
    protected ArrayList<String> removeInsideTerms(TermsCount f) {
        ArrayList<String> terms = new ArrayList<>();//the good
        terms.addAll(removeInsideTermsAndCount(f).keySet());

        return terms;
    }
}
