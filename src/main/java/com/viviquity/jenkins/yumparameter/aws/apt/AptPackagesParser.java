package com.viviquity.jenkins.yumparameter.aws.apt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.viviquity.jenkins.yumparameter.aws.apt.model.Package;

public class AptPackagesParser {

	public List<Package> parse(InputStream inputFile){
		try {
			return yamlToPackages(fileToYaml(inputFile));
		} catch (IOException e) {
			   IOUtils.closeQuietly(inputFile);
			   throw new RuntimeException("Couldn't read Package file.", e);
		}
	}

	private String fileToYaml(InputStream inputFile) throws IOException {
		List<String> lines  = IOUtils.readLines(inputFile );
		return StringUtils.join(yamlCompatibleFormat(lines), "\n");
	}

	private List<String> yamlCompatibleFormat(List<String> lines) {
		List<String> yamlLines = new ArrayList<String>();
		for(String l : lines) yamlLines.add( StringUtils.isBlank(l) ? "---" : l);
		return yamlLines;
	}

	private List<Package> yamlToPackages(String yaml) throws YamlException {
		List<Package> packages = new ArrayList<Package>();
		YamlReader reader = new YamlReader(yaml);
		while (true) {
			Object result = reader.read();
			if(result == null || result instanceof String)  break; 
			
			packages.add( new Package.Builder((Map<String, String>) result).build());
		}
		return packages;
	}
}
