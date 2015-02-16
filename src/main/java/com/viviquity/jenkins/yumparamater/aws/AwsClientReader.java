package com.virginholidays.jenkins.yumparamater.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.collect.Maps;
import com.virginholidays.jenkins.yumparamater.aws.model.Metadata;
import com.virginholidays.jenkins.yumparamater.aws.model.Package;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

/**
 * yum-paramater
 *
 * @author Declan Newman (467689)
 * @since 15/02/15
 */
public class AwsClientReader {
    private final AmazonS3Client awsClient;
    private final String repoPath;
    private final Optional<String> awsAccessKeyId;
    private final YumPrimaryParser parser;
    private final Optional<String> awsSecretAccessKey;

    private AwsClientReader(Builder builder) throws JAXBException {
        this.repoPath = builder.repoPath;
        this.awsAccessKeyId = builder.awsAccessKeyId;
        this.awsSecretAccessKey = builder.awsSecretAccessKey;
        this.parser = new YumPrimaryParser();
        this.awsClient = buildClient();
    }


    public Map<String, String> getPackageMap(final String bucketName, final String packageName) throws IOException {
        final S3Object s3Object = awsClient.getObject(bucketName, String.format("%s/repodata/primary.xml.gz", repoPath));
        final Metadata metadata = parser.pullMetadata(new GZIPInputStream(s3Object.getObjectContent()));
        return buildPackageMap(metadata);
    }

    private AmazonS3Client buildClient() {
        if (awsAccessKeyId.isPresent() && awsSecretAccessKey.isPresent()) {
            return new AmazonS3Client(new BasicAWSCredentials(awsAccessKeyId.get(), awsSecretAccessKey.get()));
        } else {
            return new AmazonS3Client(new InstanceProfileCredentialsProvider());
        }
    }

    private Map<String, String> buildPackageMap(Metadata metadata) {
        final Map<String, String> map = Maps.newTreeMap();
        for (Package pack : metadata.getPackages()) {
            map.put(pack.getRpmString(), pack.getNormalisedString());
        }
        return map;
    }

    public static class Builder {
        private final String repoPath;
        private Optional<String> awsAccessKeyId;
        private Optional<String> awsSecretAccessKey;

        private Builder(String repoPath) {
            this.repoPath = repoPath;
        }

        public static Builder newInstance(String repoPath) {
            return new Builder(repoPath);
        }

        public Builder withAwsAccessKeys(String awsAccessKeyId, String awsSecretAccessKey) {
            this.awsAccessKeyId = Optional.of(awsAccessKeyId);
            this.awsSecretAccessKey = Optional.of(awsSecretAccessKey);
            return this;
        }

        public AwsClientReader build() throws JAXBException {
            return new AwsClientReader(this);
        }
    }

}
