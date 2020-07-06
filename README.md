# General
This is both way converter of Json and Xml formats. Initially, it was project on Hyperskill platform. Then it evolves into standalone application.
Major purpose of this project was to build non-trivial application from scratch, by analysing requirements, preparing overall architecture, and finally coding application with use of Test Driven Development and Design Patterns.

# Technologies
Server
On server side, project uses Java with Spring Boot. ConverterService is written in plain Java, and uses Jackson and jUnit.
Weblayer is built with Spring Boot.
On client side, there is single website, built with React library.
As building tool, Maven is used.

# Philosophy
Converting from one format to another is based on two main steps:
1. Parsing input and building own own-tree structure, which is composite of lists and leafs;
2. Printing tree in required format.

Ad.1 
Parsing from Xml to Json is based on Regexp. Input string is divided, and then the whole tree is built.
Parsing from Json to Xml is used with help of Jackson library, which prepares Map of input lines. On the base of this map, the composite is prepared.
When specific node is created, suitable printer is injected, which knows, how to print the node. All nodes and printers are created using specific Factory.

Own tree-structure is implemented with use of Composite Design Pattern. One can distinguish:
- AbstractNode, which is parent for Node and NodeList,
- Node, which is leaf in tree,
- NodeList, which contains list of AbstractNodes.
Nodes and NodeList can have attributes. NodeList, wich elements have the same name, is called EqualNodeList.

Ad.2 
Printing operation returns String in required format (Json or Xml).
During printing, whole composite is iterated, and each printer is called. 
There are two printers hierarchy: for Json and Xml nodes. There are printers for nodes with value, empty nodes, nodeslist, etc.

# Features to be implemented
- Add detailied info on website;
- Handled 500 meesage on website
- Support for nested tags with the same name (both in json and xml)





