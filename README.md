# findTerms
Program in Java that find terms in a text in case insensitive mode.

## Usage

### Find terms in a text

This is the way to search the terms in a text. The list of term to search for has to be in an 
ArraList &lt;String&gt;. 

The default terms are defined in a file which name is
the static attribute filename of the class Vocabulary. This file must be in a owl 2.0 format 
and (maybe) have *wide* and *narrowed* relation with other terms in the ontology.
The file use by default is a subset of the [IEEE taxonomy v101](http://www.ieee.org/documents/taxonomy_v101.pdf) , 
retrieve in 2016. Thease terms in this subset are computers related and I have build it with
 [IEEEtaxonomy2rdf](https://github.com/guillem72/IEEEtaxonomy2rdf)

        ArrayList<String> res;
        Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder=new FindTerms();
        FindTerms.vocabulary=Vocabulary.get();
        String doc="In this text I want to find some words";
        res=finder.found(doc);

To find all terms and their related terms and weight them, use
        
        String doc="Advanced Security Practitioner (CASP) ---> The CASP certification proves "
                  + "competency in enterprise security; risk management; research and analysis; "
                  + "and integration of computing, communications, and business disciplines. - "
                  + "conceptualize, design, and engineer secure solutions across complex enterprise "
                  + "environments- apply critical thinking and judgment across a broad spectrum of "
                  + "security disciplines- propose and implement solutions that map to enterprise "
                  + "drivers- enterprise security- risk management";
          termsAndRelated ta=new termsAndRelated();
          ta.setTerm_boost(2.0) //optional, set the factor for the terms. The result value will be ocurrences*term_boost
          ta.setRelated_boost(1.1) //optional, set the factor for related terms.
          HashMap<String,Double> ieee=ta.find(doc);//ieee contains the terms and their value.
         

### Find related terms

The Vocabulary has to be called previosly because the model has to be set.
        
        ArrayList related;
        Vocabulary.get();
        Surrogate surro=new Surrogate(Vocabulary.jenaModel);
        surro.setTerm("path planning");
        related=surro.getSurrogates();
        System.out.println(related.toString());


### Find acronyms in a text

          FindTerms finder=new FindTerms();
          HashMap<String, String> acron;
          AcronymsReader ar=new AcronymsReader();
          acron=ar.reader("resources/acrosWikiArroba.txt");
          Set keys=acron.keySet();
          FindTerms.vocabulary=new ArrayList<>();
          FindTerms.vocabulary.addAll(keys);
          String doc="In this text I want to find some acronyms. For instance AMQP, APIPA and UDP";
          ArrayList<String> res=finder.found(doc);
          for (Object term:res){
                //code here
               //System.out.println((String)term+" @ "+acron.get((String)term));
          }


## Notes on some classes

### AcronymsReader

The input file must have the next syntax: [acronym] @ [expansion], a fragment could be
        
        BRM @ Business Reference Model
        BRMS @ Business Rule Management System
        BRR @ Business Readiness Rating
        BSA @ Business Software Alliance       

If there are more than one expansion, it doesn't return any one (so the acronym will be missing), 
but it could be found as multipleAcronimos with their multiples expansions.

## Tests
Some tests are provided. The test for RDFReader doesn't work but the class seems to work 
apropiatedly, so maybe a more convinient serialization is needed.