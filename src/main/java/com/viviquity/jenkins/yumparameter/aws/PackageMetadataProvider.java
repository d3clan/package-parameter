package com.viviquity.jenkins.yumparameter.aws;

import java.io.InputStream;
import java.util.Map;

public interface PackageMetadataProvider {

	public Map<String,String> extractPackageMetadata(InputStream file);

	public String getMetatdataFilePath(String repoPath);
}
