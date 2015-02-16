package com.viviquity.jenkins.yumparamater.aws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * yum-paramater
 *
 * Class to represent the metadata element of primary XML database
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(name = "metadata", namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Metadata {

    private Integer packageCount;
    private List<Package> packages;

    @XmlAttribute(name = "packages")
    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }

    @XmlElement(name = "package")
    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
