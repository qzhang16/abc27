# demo JAXB example from JDK 8.
-  IntelliJ has to set bytecode level to 8 to run JDK 8.
-  xjc.sh could be run to generate java objects for an xml schema file.
   -   Pay attention to the package name for generated java object.  ./xjc.sh -d <output dir> -p <package name> <xsd file>
   -   According to 'JAXBContext jc = JAXBContext.newInstance( "primer.po" );', I would rather one xsd one package.
