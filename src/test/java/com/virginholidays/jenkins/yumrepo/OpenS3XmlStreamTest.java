package com.virginholidays.jenkins.yumrepo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * yum-paramater
 *
 * @author Declan Newman (467689)
 * @since 13/02/15
 */
public class OpenS3XmlStreamTest {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ///repos/publish-api/repodata/primary.xml.gz
        //vhols-common-test-yumrepo
        AWSCredentials creds = new BasicAWSCredentials("AKIAIT7KDKX5F7XEXIDQ", "KtXkkMVYA2MoOB6n1n8Ml/GYgN9HHmyKGFVi+uyH");
        AmazonS3Client client = new AmazonS3Client(creds);
        final S3Object object = client.getObject("vhols-common-test-yumrepo", "repos/publish-api/repodata/primary.xml.gz");
        System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final Document document = builder.parse(new GZIPInputStream(object.getObjectContent()));
        final NodeList data = document.getElementsByTagName("package");
        for (int i = 0; i < data.getLength(); i++) {
            final Element rpmPackage = (Element) data.item(i);
            final Element name = (Element) rpmPackage.getElementsByTagName("name").item(0);
            final Element version = (Element) rpmPackage.getElementsByTagName("version").item(0);
            System.out.println("Name: " + name.getTextContent());
            System.out.println("Version: " + version.getAttribute("ver"));
            System.out.println("Ref: " + version.getAttribute("rel"));
            System.out.println("Epoch: " + version.getAttribute("epoch"));
        }
    }
}
