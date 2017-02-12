package ru.chernykh.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class CustomXsd11Validation implements ICustomXsd11Validation {
    private static Logger logger = LoggerFactory.getLogger(CustomXsd11Validation.class);

    /**
     * Validate XML file by XSD.
     * XSD should be in classpath resources
     *  @param prefixForSchemaRootInClasspath - prefix where XSD 1.1 schemas are stored
     * @param xmlFile                        source to check. Please close this by yourself.
     * @param xsdFile                        source with schema. Please close this by yourself.
     */
    public void validateXml(String prefixForSchemaRootInClasspath, Source xmlFile, Source xsdFile) throws SAXException {
        // 1. Lookup a factory for the W3C XML Schema language
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
        try {
            factory.setFeature("http://apache.org/xml/features/validation/cta-full-xpath-checking", true);
        } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
            throw new IllegalStateException("Error configuring parser "+e.getMessage(),e);
        }

        ResourceResolver resolver = new ResourceResolver();
        resolver.setPrefix(prefixForSchemaRootInClasspath);//set prefix if your schema is not in the root of classpath

        factory.setResourceResolver(resolver);

        // 2. Compile the schema.
        Schema schema = null;
        try {
            schema = factory.newSchema(xsdFile);
        } catch (SAXException e) {
            throw new IllegalStateException("Error parsing schema file:"+e.getMessage(),e);
        }

        // 3. Get a validator from the schema.
        Validator validator = schema.newValidator();

        // 4. Check the document
        boolean result = false;
        try {
            validator.validate(xmlFile);
        } catch (IOException e) {
            logger.error("IO error during validation", e);
        }
    }
}
