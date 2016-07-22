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

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;

/**
 * A class to work with vocabularies.
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Vocabulary {

    /*
     *@var filename The name of the file with the terms. It has to be in owl2.0 format.
     */
    public static String filename = "resources/ieee_onto2_reasoning.rdf";
    /**
     * @var labels The list of terms.
     */
    private static ArrayList<String> labels = new ArrayList<>();
    public static String wikiAcros = "resources/acrosWikiAndMore.txt";

    /**
     * @var acronimos. The list of acronyms
     */
    private static HashMap<String, String> acronimos;

    public static Model jenaModel;

    public static Model getJenaModel() {
        if (!(jenaModel != null)) {
            RDFReader lector = new RDFReader();
            jenaModel = lector.reader(filename);
        }
        return jenaModel;
    }

    public static void setJenaModel(Model jenaModel) {
        Vocabulary.jenaModel = jenaModel;
    }

    /**
     * Build the list of terms
     *
     * @return the list of terms in filename
     */
    public static ArrayList<String> get() {
        if (!labels.isEmpty()) {
            return labels;
        }
        RDFReader lector = new RDFReader();

        jenaModel = lector.reader(filename);
        DataMapper dm = new DataMapper();
        dm.setModel(jenaModel);
        labels = dm.getAllLabels();
        return labels;
    }

    public static void reset() {
        labels = new ArrayList<>();
    }

    /**
     * Return the list of possible acronyms in the terms.
     *
     * @return the list of possible acronyms. It builds them simple by join the
     * first letter of each word in upper case.
     */
    public static HashMap<String, String> findAcronyms() {
        HashMap<String, String> acron = new HashMap<>();

        String short_term = "";
        for (String label : labels) {
            short_term = buildAcronim(label);
            //System.out.println(label+" "+short_term+".");
            if (StringUtils.isNotEmpty(short_term)) {
                acron.put(short_term, label);
            }
        }
        return acron;
    }

    /**
     * Given a term return their possible Acronym. For example, for
     * <em>Informacion Systems</em> it returns <em>IS</em>. If the term is made
     * by a single word only, it returns an empty string.
     *
     * @param term0 the term to be evaluated.
     * @return the guessed acronym or an empty string if the term is a single
     * word.
     */
    public static String buildAcronim(String term0) {
        String term = term0.trim().toLowerCase();
        String res = "";
        String[] parts = term.split(" ");
        if (parts.length <= 1) {
            return res;
        }
        int i = 0;
        while (i < parts.length) {
            //System.out.print(i+" "+);
            res += parts[i].toUpperCase().charAt(0);
            i++;
        }
        return res;
    }

    /**
     * Makes a string prepared to be show in a console. It is very similar to
     * toString().
     *
     * @return and string prepared to be send to a console.
     */
    public static String listTerms() {
        int cs = 0;
        String res = "";
        for (String label : labels) {
            res += label + ",";
            cs += label.length() + 1;
            if (cs > 80) {
                res += System.lineSeparator();
                cs = 0;
            }
        }
        return res;
    }
}
