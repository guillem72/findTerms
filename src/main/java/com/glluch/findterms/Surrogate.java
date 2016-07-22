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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.glluch.findterms;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.rdf.model.Model;

/**
 * Given a term return all related terms in the taxonomy. The taxonomy must have
 * relations "wide" and "narrowed". For a term, this class can retrieve one or
 * both of them.
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Surrogate {

    protected String term;
    protected Model model;
    protected String base_uri = "http://glluch.com/ieee_taxonomy#";
    protected String prefix="<http://glluch.com/ieee_taxonomy#";
    protected DataMapper dataMapper;
    protected int radio=1;

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        if (radio>1)
        this.radio = radio;
    }

    
    
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        
        this.term = term.toLowerCase().trim();
    }

    public String getBase_uri() {
        return base_uri;
    }

    public void setBase_uri(String base_uri) {
        this.base_uri = base_uri;
    }

    /**
     * Create an instance base of a term and a org.apache.jena.rdf.model.
     *
     * @param term The term for which related terms are needed.
     * @param model The org.apache.jena.rdf.model.
     */
    public Surrogate(String term, Model model) {
        setTerm(term);
        this.model = model;
        dataMapper = new DataMapper();
        dataMapper.setModel(this.model);
    }

    public Surrogate(Model model) {
        this.term = "";
        this.model = model;
        dataMapper = new DataMapper();
        dataMapper.setModel(this.model);
    }
    
    public Surrogate(Model model, int radio) {
        this.term = "";
        this.model = model;
        setRadio(radio);
        dataMapper = new DataMapper();
        dataMapper.setModel(this.model);
    }

    /**
     * Retrieve the terms related to the surrogate.term in the model
     *
     * @param mode In this implementation could be "wide" or "narrowed".
     * @return An array of Terms with the relation "mode", from the label of the
     * RDF/XML.
     */
       public ArrayList<String> getSurrogates(String mode) {
        ArrayList<String> surros = new ArrayList<>();
        if (this.term.isEmpty()) {
            return surros;
        }
        String term_id = this.term.replace(" ", "_");
        ArrayList<String> uris;
        String queryString = preQuery(mode, term_id);
        if (queryString.isEmpty()) {
            return surros;
        }//TODO error
        uris = dataMapper.getUris(queryString, mode);

        for (String uri : uris) {

            //System.out.println(uri);
            String label = dataMapper.getLabel(uri);
            if (!label.isEmpty()) {
                surros.add(dataMapper.getLabel(uri));
            }
            else {if (!uri.equals("http://glluch.com/ieee_taxonomy#ieee"))
                System.out.println("ERROR no label for "+uri);}
        }
        return surros;
    }

    /**
     * Retrieve the terms related to the surrogate.term in the model.
     *
     * @return An array of terms related to this.term in all modes
     */
    
    public ArrayList<String> getSurrogates() {
        // System.out.println(this.term);
        ArrayList<String> surros;
        surros = getSurrogates("wide");
        surros.addAll(this.getSurrogates("narrowed"));
        //System.out.println(surros);
        return surros;

    }

    /**
     * Prepare the statement for a sparql command.
     *
     * @param mode Could be "wide" or "narrowed"
     * @param term_id It is the term in uppercase and underscore instead of
     * spaces
     * @return The sentence to be done in a string
     */
    protected String preQuery(String mode, String term_id) {
        String query = "";
        query+="PREFIX ieee:<http://glluch.com/ieee_taxonomy#>"+System.lineSeparator();
        if (mode.equals("wide")) {
            //query = " SELECT ?parent " + "WHERE {<" + base_uri + term_id + ">"
              //      + "  <" + base_uri + mode + ">  ?parent} ";
              
         query+="SELECT ?parent WHERE \n" +
"{\n<" +base_uri + term_id+">"+
" ieee:level ?lw .\n<" +base_uri + term_id+">"+
" ieee:wide ?parent .\n" +
"?parent ieee:level ?l .\n" +
"FILTER (?lw-?l >="+radio+") .\n" +
"}";

        }
        if (mode.equals("narrowed")) {
           // query = " SELECT ?child " + "WHERE {<" + base_uri + term_id + ">"
             //       + "  <" + base_uri + mode + ">  ?child} ";
             query+="SELECT ?child WHERE"+ 
"{<"+base_uri + term_id+"> ieee:level ?lw ."+
"<"+base_uri + term_id+"> ieee:narrowed ?child ."+
"?child ieee:level ?l ."+
"FILTER (?l-?lw >=2) ."+
"}";
        }
        //System.out.println(query);
        return query;
    }

    /**
     * Given a terms[], get the list of related terms.
     *
     * @param terms a list of term. The function return the related terms for
     * each one.
     * @return an array term -&gt; term[]
     */
    public HashMap<String, ArrayList<String>> surrogatesForeach(ArrayList<String> terms) {
        HashMap<String, ArrayList<String>> all = new HashMap<>();
        for (String term0 : terms) {
            setTerm(term0);
            ArrayList<String> related;
            related = this.getSurrogates();
            all.put(term, related);
        }
        return all;

    }
}