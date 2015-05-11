package com.viviquity.jenkins.packageparameter.aws.apt;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.viviquity.jenkins.packageparameter.aws.apt.model.Package;

public class PackagesFormatter {
	private VersionComparator versionComparator = new VersionComparator();
	
	public Map<String, String> format(List<Package> packages){
		Map<String, String> result = new TreeMap<String, String>(versionComparator);
		for(Package pack : packages)
			result.put(String.format("%s-%s", pack.name, pack.version), String.format("%s=%s", pack.name, pack.version));
		return result;
	}
	
	private class VersionComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2) * -1;
		}
	}
}
