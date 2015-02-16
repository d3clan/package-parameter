package com.viviquity.jenkins.yumparameter.aws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * yum-parameter
 *
 * Class to represent the version element of the primary XML database
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Version {

    private String epoch;
    private String ver;
    private String rel;

    @XmlAttribute
    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    @XmlAttribute
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @XmlAttribute
    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
