package com.virginholidays.jenkins.yumparamater.aws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * yum-paramater
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Time {

    private Long file;
    private Long build;

    @XmlAttribute
    public Long getFile() {
        return file;
    }

    public void setFile(Long file) {
        this.file = file;
    }

    @XmlAttribute
    public Long getBuild() {
        return build;
    }

    public void setBuild(Long build) {
        this.build = build;
    }


}
