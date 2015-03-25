package com.viviquity.jenkins.yumparameter.aws.apt;

import java.io.InputStream;
import java.util.Map;

import com.viviquity.jenkins.yumparameter.aws.PackageMetadataProvider;


public class AptMetadataProvider implements PackageMetadataProvider {

	private AptPackagesParser parser = new AptPackagesParser();
	private PackagesFormatter formatter = new PackagesFormatter();
	
	@Override
	public Map<String, String> extractPackageMetadata(InputStream file) {
		return formatter.format(parser.parse(file));
	}

	@Override
	public String getMetatdataFilePath(String repoPath) {
		return repoPath + "/dists/trusty/main/binary-amd64/Packages";
	}

}
