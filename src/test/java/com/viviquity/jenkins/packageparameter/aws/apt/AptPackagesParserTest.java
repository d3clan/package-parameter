package com.viviquity.jenkins.packageparameter.aws.apt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.viviquity.jenkins.packageparameter.aws.apt.model.Package;

public class AptPackagesParserTest {

	@Test
	public void testParse() {
		InputStream io = AptPackagesParserTest.class.getResourceAsStream("/Packages");
		List<Package> packages = new AptPackagesParser().parse(io);
		assertNotNull(packages);
		assertEquals(2, packages.size());
	}

}
