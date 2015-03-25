package com.viviquity.jenkins.yumparameter.aws.yum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.google.common.collect.Maps;
import com.viviquity.jenkins.yumparameter.aws.PackageMetadataProvider;
import com.viviquity.jenkins.yumparameter.aws.yum.model.Metadata;
import com.viviquity.jenkins.yumparameter.aws.yum.model.Package;

public class YumMetadataProvider implements PackageMetadataProvider {

	private YumPrimaryParser parser = new YumPrimaryParser();

	@Override
	public Map<String, String> extractPackageMetadata(InputStream file) {
		try {
	        final Metadata metadata = parser.parse(new GZIPInputStream(file));
	        return buildPackageMap(metadata);			
		} catch (IOException e){
			throw new RuntimeException("Couldn't retrieve Yum package metadata", e);
		}

	}
	
    private Map<String, String> buildPackageMap(Metadata metadata) {
        final Map<String, String> map = Maps.newTreeMap();
        for (Package pack : metadata.getPackages()) {
            map.put(pack.getRpmString(), pack.getNormalisedString());
        }
        return map;
    }

	@Override
	public String getMetatdataFilePath(String repoPath) {
		return String.format("%s/repodata/primary.xml.gz", repoPath);
	}

}
