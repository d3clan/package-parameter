package com.viviquity.jenkins.yumparamater.aws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * yum-paramater
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Package {

    private String name;
    private String arch;
    private Location location;
    private Version version;
    private Time time;

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @XmlElement
    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    @XmlElement
    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @XmlElement
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @XmlTransient
    public String getRpmString() {
        return String.format("%s:%s-%s-%s.%s", version.getEpoch(), name, version.getVer(), version.getRel(), arch).toLowerCase();
    }

    @XmlTransient
    public String getNormalisedString() {
        final DateFormat sdf = SimpleDateFormat.getInstance();
        StringBuilder sb = new StringBuilder(version.getVer() + "-");
        final String rel = version.getRel();
        if (rel.indexOf('_') > 0) {
            sb.append(rel.replaceFirst("_", " branch: "));
            if (sb.indexOf("_") > 0) {
                sb.replace(sb.indexOf("_"), sb.indexOf("_") + 1, "/");
            }
        }
        sb.append(" (built: " + sdf.format(new Date(time.getBuild() * 1000)) + ")");
        return sb.toString();
    }
}
