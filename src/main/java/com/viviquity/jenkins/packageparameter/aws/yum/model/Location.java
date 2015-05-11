package com.viviquity.jenkins.packageparameter.aws.yum.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * yum-parameter
 *
 * Class to represent the location element of primary XML database
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(name = "location", namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Location {

    @XmlAttribute
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
