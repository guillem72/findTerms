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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * Some general methods for the package. Maybe some of them can be substituted
 * by org.apache.commons.StringUtils.
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Utils {

    /**
     * Given a piece of text return it with the first letter in upper case.
     *
     * @param text The text to be processed.
     * @return The same text but the first char in upper case.
     */
    public String firstUpper(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        String ntext = text.substring(0, 1).toUpperCase() + text.substring(1);
        return ntext;
    }

    /**
     * Applies the firstUpper method to every text in the list
     *
     * @param texts list of texts to be "uppercased".
     * @return an arrayList in which each element has the first char in upper
     * case.
     */
    public ArrayList<String> firstUpperForeach(ArrayList<String> texts) {
        ArrayList<String> ntexts = new ArrayList<>();
        for (String text : texts) {
            ntexts.add(firstUpper(text));
        }
        return ntexts;
    }

    /**
     * Given a piece of text return it with the first letter in lower case.
     *
     * @param text The text to be processed.
     * @return The same text but the first char in lower case.
     */
    public String firstLower(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        return text.substring(0, 1).toLowerCase() + text.substring(1);
    }

    /**
     * Replaces <em>old</em> term in <em>doc0</em> with <em>newterm</em>. This
     * method is case insensitive, as the name suggests.
     *
     * @param doc0 The text where the term has to be replaced.
     * @param old The piece of text to replaced.
     * @param newterm The term which will replace <em>old</em>
     * @return The string with the occurences of <em>old</em> replaced by
     * <em>newterm</em>
     */
    public String replaceCaseInsensitive(String doc0, String old, String newterm) {
        String doc = "";
        if (doc0.contains(old)) {
            doc = doc0.replace(old, newterm);
        } else {
            if (old.equals(firstLower(old))) { //starts lowerCase
                old = firstUpper(old);
            } else {
                old = firstLower(old);
            }
            if (doc0.contains(old)) {
                doc = doc0.replace(old, newterm);
            }
        }

        return doc;
    }

    /**
     * Search a term in string. It is case insensitive.
     *
     * @param doc The text where the term will be search.
     * @param term The term to be search in <em>doc</em>
     * @return true if the <em>term</em> was found in <em>doc</em>, false
     * otherwise.
     */
    public static boolean containsCaseInsensitive(String doc, String term) {
        String docl = doc.toLowerCase();
        String terml = term.toLowerCase();
        return Utils.stringContains(docl, terml);
        //return docl.contains(terml);
    }

    /**
     *
     * Search a term in string.
     *
     * @param text The text where the term will be search.
     * @param term The term to be search in <em>text</em>
     * @return true if the <em>term</em> was found in <em>text</em>, false
     * otherwise.
     */
    public static boolean stringContains(String text, String term) {
        //System.out.println("Utils.stringContains:");
        
        String patternString = "\\b(" + term + ")\\b";
        boolean found = false;
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            found = true;
        }
        //System.out.println(term +" found?"+ found);
        return found;

    }

    //replaceAll(String regex, String replacement)
    public static String termAcronymAdd(String doc, String old_term, String new_term) {
        String res;
            res=doc.replaceAll("\\b"+old_term+"\\b", new_term);
        return res;

    }

}//end of class
