package com.viviquity.jenkins.yumparameter.aws.apt.model;

import java.util.Map;

public class Package {

	public String name;
	public String version;
	public String installedSise;
	public String priority;
	public String section;
	public String maintainer;
	public String architecture;
	public String description;
	public String md5sum;
	public String sha1;
	public String sha256;
	public String source;
	public String size;
	public String filename;
	
	public static class Builder {
		private Package pack = new Package();
		
		public Builder(Map<String, String> map) {
			pack.name = map.get("Package");
			pack.version = map.get("Version");
			pack.installedSise = map.get("Installed-Size");
			pack.priority = map.get("Priority");
			pack.section = map.get("Section");
			pack.maintainer = map.get("Maintainer");
			pack.architecture = map.get("Architecture");
			pack.description = map.get("Description");
			pack.md5sum = map.get("MD5sum");
			pack.sha1 = map.get("SHA1");
			pack.sha256 = map.get("SHA256");
			pack.source = map.get("Source");
			pack.size = map.get("Size");
			pack.filename = map.get("Filename");
		}
		
		
		public Package build(){
			return pack;
		}
	}
}
