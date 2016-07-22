PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX ieee:<http://glluch.com/ieee_taxonomy#>


SELECT ?parent  WHERE { ieee:linkedin ieee:wide ?parent }

SELECT ?l  WHERE { ieee:linkedin ieee:level ?l }

SELECT ?label WHERE {ieee:linkedin rdfs:label ?label}

SELECT ?parent ?l WHERE 
{ ieee:linkedin ieee:wide ?parent .
?parent ieee:level ?l }


SELECT ?parent ?l WHERE 
{ ieee:linkedin ieee:wide ?parent .
?parent ieee:level ?l .
FILTER (?l >=1) .
}

SELECT ?parent WHERE 
{ ieee:linkedin ieee:wide ?parent .
?parent ieee:level ?l .
FILTER (?l >=1) .
}

SELECT ?parent WHERE 
{
ieee:linkedin ieee:level ?lw .
ieee:linkedin ieee:wide ?parent .
?parent ieee:level ?l .
FILTER (?lw-?l >=2) .
}

         query="SELECT ?parent WHERE \n" +
"{\n" +base_uri + term_id+
" ieee:level ?lw .\n" +base_uri + term_id+
" ieee:wide ?parent .\n" +
"?parent ieee:level ?l .\n" +
"FILTER (?lw-?l >="+radio+") .\n" +
"}";





SELECT ?child WHERE 
{
ieee:economics ieee:level ?lw .
ieee:economics ieee:narrowed ?child .
?child ieee:level ?l .
FILTER (?l-?lw >=2) .
}

"SELECT ?child WHERE 
{
"+base_uri + term_id+" ieee:level ?lw .
"+base_uri + term_id+" ieee:narrowed ?child .
?child ieee:level ?l .
FILTER (?l-?lw >=2) .
}"
