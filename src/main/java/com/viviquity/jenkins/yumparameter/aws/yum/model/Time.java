package com.viviquity.jenkins.yumparameter.aws.yum.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * yum-parameter
 *
 * Class to represent the time element of the primary XML database
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Time {

    @XmlAttribute
    private Long file;
    @XmlAttribute
    private Long build;


    public Long getFile() {
        return file;
    }

    public void setFile(Long file) {
        this.file = file;
    }

    public Long getBuild() {
        return build;
    }

    public void setBuild(Long build) {
        this.build = build;
    }


}
