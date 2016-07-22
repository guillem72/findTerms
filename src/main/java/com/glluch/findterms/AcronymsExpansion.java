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

import java.util.HashMap;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * Given a text substitutes o adds any ancronym found by their expansion.
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class AcronymsExpansion {
    private String filename="resources/acrosWikiAndMore.txt";

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String add(String doc) {
        String res0 = doc;
        HashMap<String, String> acron;
        AcronymsReader ar = new AcronymsReader();
        acron = ar.reader(filename);
        //System.out.println(acron);
        Set keys = acron.keySet();
        for (Object term : keys) {//iter on all acronims and substitute if it is present
            String new_term=acron.get((String) term);
            if (!StringUtils.isEmpty(new_term)) 
            res0=Utils.termAcronymAdd(res0,(String)term,(String)term
                    +" ( "+new_term+" ) ");
           

        }
        return res0;
    }
}
