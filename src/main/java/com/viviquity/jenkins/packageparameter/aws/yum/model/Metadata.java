package com.viviquity.jenkins.packageparameter.aws.yum.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * yum-parameter
 *
 * Class to represent the metadata element of primary XML database
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(name = "metadata", namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "packages" })
public class Metadata {

    @XmlAttribute(name = "packages")
    private Integer packageCount;
    @XmlElement(name = "package", required = true)
    private List<Package> packages;

    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
