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

import com.glluch.utils.JMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class termsAndRelated {
      private static double term_boost=2.0;
      private static double related_boost=1.0;
      protected static int radio=1;

    public static int getRadio() {
        return radio;
    }

    public static void setRadio(int radio) {
        termsAndRelated.radio = radio;
    }
      
    public static double getTerm_boost() {
        return term_boost;
    }

    public static void setTerm_boost(double term_boost) {
        termsAndRelated.term_boost = term_boost;
    }

    public static double getRelated_boost() {
        return related_boost;
    }

    public static void setRelated_boost(double related_boost) {
        termsAndRelated.related_boost = related_boost;
    }
      
    /**
     * 
     * @param docs
     * @return
     */
    public HashMap <String, HashMap <String,Double>> findForEach(HashMap <String, String> docs){
        HashMap <String, HashMap <String,Double>> res=new HashMap <>();
        Set keys=docs.keySet();
        for(Object key0:keys){
            String key=(String ) key0;
            String doc=docs.get(key);
            HashMap <String,Double> terms=find(doc);
            res.put(key,terms);
        }
        
        return res;
    }  
    
    /**
     * Given a doc return all the terms and their related terms from the rdf file. 
     * @param doc The text where the terms will be sought
     * @return A list of terms and their weigth.
     */
    public HashMap<String,Double> find(String doc){
        //  HashMap<String,Double> res1=new HashMap<String,Double>();
           HashMap<String,Integer> ptermsAndCount;
    FindTerms finder=new FindTerms();
    FindTerms.vocabulary=Vocabulary.get();
    
       ptermsAndCount=finder.foundAndCount(doc);//proper terms
          ArrayList<String> pterms = JMap.keys2array(ptermsAndCount);
       //get related terms
        Surrogate surro=new Surrogate(Vocabulary.jenaModel, radio);
       HashMap<String, ArrayList<String>> rtermsRaw=surro.surrogatesForeach(pterms);
       HashMap <String,Double>ptermsAndCountDouble= JMap.si2sd(ptermsAndCount);
       HashMap <String,Double> rterms=preIntersect(ptermsAndCountDouble,rtermsRaw);
       
       
       //mix the results: proper an related terms
      HashMap<String,Double>  res1=com.glluch.utils.JMap.intersectAndSum(ptermsAndCountDouble,rterms);
        return res1;
          
      }
      
      private static HashMap <String,Double> preIntersect(HashMap<String,Double> weights, HashMap<String, ArrayList<String>> rterms){
            HashMap <String,Double> res=new HashMap <>();
            Set keys=rterms.keySet();
            for (Object key0:keys){
                String key=(String) key0;
                double value0=weights.get(key);
                double value=value0*related_boost;
                if (res.isEmpty()) res=forEach(rterms.get(key),value);
                else{
                    res=com.glluch.utils.JMap.intersectAndSum(res,forEach(rterms.get(key),value));
                }
                
            }
            return res;
        }
      
      private static HashMap <String,Double> forEach(ArrayList <String> rterms,double val){
        HashMap <String,Double> res=new HashMap <>();
        for (String t:rterms){
            res.put(t,val);
        }
        return res;
        }
}
