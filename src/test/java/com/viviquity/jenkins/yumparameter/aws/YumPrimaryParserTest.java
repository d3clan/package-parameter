package com.viviquity.jenkins.yumparameter.aws;

import com.viviquity.jenkins.yumparameter.aws.model.Metadata;
import com.viviquity.jenkins.yumparameter.aws.model.MetadataTest;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class YumPrimaryParserTest {

    @Test
    public void testPullMetadata() throws Exception {
        final YumPrimaryParser parser = new YumPrimaryParser();
        final Metadata metadata = parser.pullMetadata(MetadataTest.class.getResourceAsStream("/primary.xml"));
        assertNotNull(metadata);
    }
}