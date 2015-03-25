package com.viviquity.jenkins.yumparameter.aws;

import com.viviquity.jenkins.yumparameter.aws.model.MetadataTest;
import com.viviquity.jenkins.yumparameter.aws.yum.YumPrimaryParser;
import com.viviquity.jenkins.yumparameter.aws.yum.model.Metadata;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class YumPrimaryParserTest {

    @Test
    public void testPullMetadata() throws Exception {
        final YumPrimaryParser parser = new YumPrimaryParser();
        final Metadata metadata = parser.parse(MetadataTest.class.getResourceAsStream("/primary.xml"));
        assertNotNull(metadata);
    }
}