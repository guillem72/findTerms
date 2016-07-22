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
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

/**
 * A class in charge to query and comunication in sparkql
 * 
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class DataMapper {
     private Model model;

   
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    
    /**
     * Do a sparql query retrieving the related uris. 
     * The query is constructed in the method <b>surrogate.preQuery</b> 
     * @param queryString Query constructed in the method surrogate.preQuery
     * @param mode And indication for choose the right label
     * @return ArrayList[uris] The list of uris related to the term. 
      */
    public ArrayList<String> getUris(String queryString, String mode){
        ArrayList<String> uris = new ArrayList<>(); 
        Query query = QueryFactory.create(queryString);

        try (// Execute the query and obtain results
                QueryExecution qe = QueryExecutionFactory.create(query, this.model)) {
            ResultSet results = qe.execSelect();
            //System.out.println(results.toString());
// Important - free up resources used running the query
            for (; results.hasNext();) {
                // Access variables: soln.get("x") ;
                RDFNode n;
                QuerySolution soln = results.next();
                //System.out.println(soln.toString());
                if (mode.equals("wide"))
                n = soln.get("parent"); // "x" is a variable in the query
                else n = soln.get("child");
                //System.out.println(n.toString());
               uris.add(n.toString());
               
            }
        }
        return uris;
    }// getUris
    
    /**
     *Construct the string to do a sparql query to get the label of a given uri 
     * @param term_uri  The uri of a term 
     * @return The query to retrive a label.
     */
    protected String queryLabel(String term_uri){
    String label;
    //label=" SELECT ?label " + "WHERE {<"+ term_uri +"> <rdfs:label xml:lang=\"en\">"+
       //     "  ?label} ";
       label=" SELECT ?label " + "WHERE {<"+ term_uri +"> <http://www.w3.org/2000/01/rdf-schema#label>"+
          "  ?label} ";
      
     //System.out.println("LABEL="+label);
    return label;
    }

    /**
     * Get the value of a label. 
     * @param uri The uri of the term.
     * @return The label, a human readable string for the term.
     * 
     */
    public String getLabel(String uri) {
        //System.out.println(uri);
        String result="";
        String queryString=queryLabel(uri);
        Query query = QueryFactory.create(queryString);

        try (// Execute the query and obtain results
                QueryExecution qe = QueryExecutionFactory.create(query, this.model)) {
            ResultSet results = qe.execSelect();
// Output query results       ResultSetFormatter.out(System.out, results, query);
// Important - free up resources used running the query
            for (; results.hasNext();) {
                // Access variables: soln.get("x") ;
                RDFNode n;
                QuerySolution soln = results.next();
                n = soln.get("label");
                String labelLang=n.toString();
                String label0=labelLang.split("@")[0];
               result=label0.trim();
               
            }
        }
        return result;
    }
    
    /**
     * Get all labels in the ontology.
     * @return all labels, the representation of the terms
     */
    public ArrayList<String> getAllLabels(){
        ArrayList<String> labels=new ArrayList<>();
          String querylabel=" SELECT ?label "
                  + "WHERE { ?uri <http://www.w3.org/2000/01/rdf-schema#label>"
                  +
          "  ?label} ";
          Query query = QueryFactory.create(querylabel);

        try (// Execute the query and obtain results
                QueryExecution qe = 
                        QueryExecutionFactory.create(query, this.model)) {
            ResultSet results = qe.execSelect();

            for (; results.hasNext();) {
                // Access variables: soln.get("x") ;
                RDFNode n;
                QuerySolution soln = results.next();
                n = soln.get("label");
                String labelLang=n.toString();
                String label0=labelLang.split("@")[0];
                labels.add(label0.trim());
               
            }
        }
       return labels;
    }
    
}
