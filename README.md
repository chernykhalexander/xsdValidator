# xsdValidator
Java validator XML according XSD from classpath.

It is not difficult to validate XML by XSD. It is very convenient to store XSDs in classpath and validate XML in a such way. 

Problems starts when XSD contains imports of other schemas. Schema parser doesn't know how to resolve the location of imports.
There is no out of box solution to this problem. 
The aim of this code is to solve it.
