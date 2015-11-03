package com.viviquity.jenkins.packageparameter.aws.yum.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * yum-parameter
 * Class to represent the XML in the primary XML database
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
@XmlRootElement(name = "package", namespace = "http://linux.duke.edu/metadata/common")
@XmlAccessorType(XmlAccessType.NONE)
public class Package implements Comparable<Package> {

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
        return String.format("%s-%s-%s", name, version.getVer(), version.getRel());
    }

    @XmlTransient
    public String getNormalisedString() {
        final DateFormat sdf = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.UK);
        StringBuilder sb = new StringBuilder(version.getVer() + "-");
        final String rel = version.getRel();
        if (rel.indexOf('_') > 0) {
            sb.append(rel.replaceFirst("_", " - branch: "));
        }
        sb.append(" (built: " + sdf.format(new Date(time.getBuild() * 1000)) + ")");
        return sb.toString();
    }

    @Override
    public int compareTo(Package o) {
        if (o == null || o.getTime() == null || o.getTime().getBuild() == null) {
            return 1;
        } else if (time.getBuild() == null) {
            return -1;
        } else {
            return time.getBuild().compareTo(o.getTime().getBuild());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Package)) {
            return false;
        }
        Package aPackage = (Package) o;
        return Objects.equals(name, aPackage.name) &&
                Objects.equals(arch, aPackage.arch) &&
                Objects.equals(location, aPackage.location) &&
                Objects.equals(version, aPackage.version) &&
                Objects.equals(time, aPackage.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arch, location, version, time);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("arch", arch)
                .append("location", location)
                .append("version", version)
                .append("time", time)
                .toString();
    }

}
