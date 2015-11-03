package com.viviquity.jenkins.packageparameter.aws.yum;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.viviquity.jenkins.packageparameter.aws.PackageMetadataProvider;
import com.viviquity.jenkins.packageparameter.aws.yum.model.Metadata;
import com.viviquity.jenkins.packageparameter.aws.yum.model.Package;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class YumMetadataProvider implements PackageMetadataProvider {

    private YumPrimaryParser parser = new YumPrimaryParser();

    @Override
    public Map<String, String> extractPackageMetadata(InputStream file) {
        try {
            final Metadata metadata = parser.parse(new GZIPInputStream(file));
            return buildPackageMap(metadata);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't retrieve Yum package metadata", e);
        }

    }

    private Map<String, String> buildPackageMap(Metadata metadata) {
        final Map<String, String> map = Maps.newLinkedHashMap();
        final Set<Package> packages = Sets.newTreeSet(metadata.getPackages()).descendingSet();
        for (Package pack : packages) {
            map.put(pack.getRpmString(), pack.getNormalisedString());
        }
        return map;
    }

    @Override
    public String getMetatdataFilePath(String repoPath) {
        return String.format("%s/repodata/primary.xml.gz", repoPath);
    }

}
