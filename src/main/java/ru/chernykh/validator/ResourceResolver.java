package ru.chernykh.validator;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import java.io.InputStream;


public class ResourceResolver implements LSResourceResolver {

    private String prefix;

    public LSInput resolveResource(String type, String namespaceURI,
                                   String publicId, String systemId, String baseURI) {

        systemId = prefix + "/" + systemId;
        InputStream resourceAsStream = this.getClass().getResourceAsStream(systemId);
        if (resourceAsStream == null) {
            throw new IllegalStateException("Cannot find xsd " + systemId + " in classpath");
        }
        return new MyCustomLSInput(publicId, systemId, resourceAsStream);
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
