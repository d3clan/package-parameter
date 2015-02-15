package com.virginholidays.jenkins.yumparamater.aws;

import com.virginholidays.jenkins.yumparamater.aws.model.Metadata;
import com.virginholidays.jenkins.yumparamater.aws.model.MetadataTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class YumPrimaryParserTest {

    @Test
    public void testPullMetadata() throws Exception {
        final YumPrimaryParser parser = new YumPrimaryParser();
        final Metadata metadata = parser.pullMetadata(MetadataTest.class.getResourceAsStream("/primary.xml"));
        assertNotNull(metadata);
    }
}