package com.viviquity.jenkins.yumparamater.aws;

import com.viviquity.jenkins.yumparamater.aws.model.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * yum-paramater
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
public class YumPrimaryParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(YumPrimaryParser.class);
    private final Unmarshaller unmarshaller;

    public YumPrimaryParser() throws JAXBException {
        this.unmarshaller = JAXBContext.newInstance(Metadata.class).createUnmarshaller();
    }

    public Metadata pullMetadata(InputStream inputStream) throws IOException {
        try {
            final JAXBElement<Metadata> jaxbElement = unmarshaller.unmarshal(new StreamSource(inputStream), Metadata.class);
            return jaxbElement.getValue();
        } catch (JAXBException e) {
            LOGGER.error("Cannot communicate with the Yum primary repository xml", e);
            throw new IOException(e);
        }

    }
}
