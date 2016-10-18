# Fitxers i fragments

- acrosWikiAndMore.txt llistat d'acrònims de wikipèdia enriquit amb els trobats a IEEE computers
- IEEE_computersArreglatRAW.txt  IEEE només el que semblen importants sense haver substituït els acrònims.
- IEEE_computer_acronyms_done.txt  Resultat d'aplicar el fragment:
    
        String docName="resources/IEEE_computersArreglatRAW.txt";
        String targetName="resources/IEEE_computers_acronyms_done.txt";
        File docFile=new File(docName);
        File targetFile=new File (targetName);
        String doc=FileUtils.readFileToString(docFile);
        AcronymsExpansion ae=new AcronymsExpansion();
        ae.setFilename("resources/acrosWikiAndMore.txt");
        String res=ae.add(doc);
        System.out.println(res);
        FileUtils.write(targetFile, res);

- IEEE_computer_acronyms_done_manual.txt   Fitxer anterior però editat manualment. 
S'han  tret les expansions inadequades, els parèntesis explicatius i s'ha creat una nova línia
per a cada acrònim.
