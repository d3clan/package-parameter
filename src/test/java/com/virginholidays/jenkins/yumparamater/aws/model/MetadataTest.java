package com.virginholidays.jenkins.yumparamater.aws.model;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import static org.junit.Assert.*;

public class MetadataTest {

    @Test
    public void testPackageParsing() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Metadata.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final JAXBElement<Metadata> jaxbElement =
                unmarshaller.unmarshal(new StreamSource(MetadataTest.class.getResourceAsStream("/primary.xml")), Metadata.class);
        assertNotNull(jaxbElement);
        final Metadata metadata = jaxbElement.getValue();
        assertNotNull(metadata);
        assertEquals(Integer.valueOf(11), metadata.getPackageCount());
        assertEquals(11, metadata.getPackages().size());
        for (Package pack : metadata.getPackages()) {
            checkPackage(pack);
        }

    }

    private void checkPackage(Package pack) {
        assertNotNull(pack.getName());
        final Time time = pack.getTime();
        assertNotNull(time);
        assertNotNull(time.getBuild());
        assertNotNull(time.getFile());
        final Version version = pack.getVersion();
        assertNotNull(version);
        assertNotNull(version.getEpoch());
        assertNotNull(version.getRel());
        assertNotNull(version.getVer());
        final Location location = pack.getLocation();
        assertNotNull(location);
        assertNotNull(location.getHref());
    }

}