/*
 * Copyright (C) 2016 Guillem LLuch Moll <guillem72@gmail.com>
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
import com.glluch.utils.Out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Guillem LLuch Moll
 */
public class TermsCount extends HashMap<String, Integer> implements java.io.Serializable {

   public String pretyPrint(){
       String res="";
       Iterator iter=this.iterator();
       while (iter.hasNext()){
           String term = (String) iter.next();
           res+=term+" -> "+this.get(term)+System.lineSeparator();
       }
       return res;
   }
    
    public ArrayList <String> keys(){
        ArrayList<String> res=new ArrayList<>();
        Set keyset=this.keySet();
        for (Object key0:keyset){
            res.add((String)key0);
        }
        
        return res;
        
   }
    
    
    public Iterator iterator(){
    ArrayList <String> keys=this.keys();
    return keys.iterator();
    }
    
    
      
    
    
    protected transient boolean verbose = true;

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    protected transient boolean debug=true;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }




 /**
     * Sent a message to the console depens on the parametre verbose. If it is
     * true (on), the text is shown.
     *
     * @param text The text to be shown
     */

  protected void show(String text) {
        if (this.verbose) {
            Out.p(text);
        }
    }
    

  /**
     * Sent a message to the console depens on the parametre debug. If it is
     * true (on), the text is shown.
     *
     * @param text The text to be shown
     */
    protected void debug(String text){
        if (this.debug){
        Out.p(text);
        }
    }

 

}
