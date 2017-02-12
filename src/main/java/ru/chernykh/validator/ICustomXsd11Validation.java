package ru.chernykh.validator;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;

public interface ICustomXsd11Validation {
    void validateXml(String prefixForSchemaRootInClasspath, Source xmlFile, Source xsdFile) throws SAXException;
}
