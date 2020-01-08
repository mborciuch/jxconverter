# json-xml-converter-maven

This app was developed  as one of Hyperskill Projects.

It implements Composit Desing Patterin, in area of prepared structure.
The area of parsing text to  structure is similiar between JSON-XML and XML-JSON, but with some differences. Parsing needs refactoring, to be undependent from input structure.

Nodes of structure contains Printer interface implementation. This printer knows, how to print node.

There are two types of printers: for XML and JSON target format. For each format has own printers hierarchy. But it could be improved with Bridge Design Pattern. 


Actually, it supports converting most cases of XML structure to JSON: nested list, parameters.
All cases are presented in Unit Tests.

For now, work is progressed in developing JSON-XML converter: the nested list are not dealt yet.

