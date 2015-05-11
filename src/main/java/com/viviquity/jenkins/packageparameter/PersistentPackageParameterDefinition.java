package com.viviquity.jenkins.packageparameter;

import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.SimpleParameterDefinition;
import hudson.model.StringParameterValue;
import hudson.util.FormValidation;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

import com.viviquity.jenkins.packageparameter.aws.AwsClientReader;

/**
 * yum-parameter
 *
 * Definition for a parameter that looks up the deployed packages for a given repository. This is specifically for AWS S3 hosted
 * repos at the moment, but would be nice to extend this to accommodate other location types. It should be very
 * straightforward to do this.
 *
 * @author Declan Newman (467689)
 * @since 14/02/15
 */
public class PersistentPackageParameterDefinition extends SimpleParameterDefinition {

    private final boolean useAwsKeys;
    private final String awsAccessKeyId;
    private final String awsSecretAccessKey;
    private final String bucketName;
    private final String repoPath;
    private final String repositoryType;
    
    /**
     * Default constructor
     * @param name the name of the parameter. This will passed to the build as a variable
     * @param description what is the description that the user has supplied for the parameter
     * @param useAwsKeys the boolean flag, that tells us if we need to supply authentication when calling S3.
     * @param awsAccessKeyId the access key, if required.
     * @param awsSecretAccessKey the secret access key, if required.
     * @param bucketName the name of the S3 bucket where the repo is hosted.
     * @param repoPath the path to target the S3 repo.
     */
    @DataBoundConstructor
    public PersistentPackageParameterDefinition(String name, String description, boolean useAwsKeys, String awsAccessKeyId, String awsSecretAccessKey,
                                            String bucketName, String repoPath, String repositoryType) {
        super(name, description);
        this.useAwsKeys = useAwsKeys;
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretAccessKey = awsSecretAccessKey;
        this.bucketName = bucketName;
        this.repoPath = repoPath;
        this.repositoryType = repositoryType; 
    }


    /**
     * Allows us to validate the choices. Currently always returns true.
     * @param choices to be validated
     * @return true if the choices are valid (always true)
     */
    public static boolean areValidChoices(String choices) {
        return true;
    }

    /**
     * Creates a {@link hudson.model.ParameterValue} from the string representation.
     *
     * @param value the value of the parameter to be used
     */
    @Override
    public ParameterValue createValue(String value) {
        return new StringParameterValue(getName(), value, getDescription());
    }

    /**
     * Create a parameter value from a form submission.
     * This method is invoked when the user fills in the parameter values in the HTML form
     * and submits it to the server.
     *
     * @param req the {@link org.kohsuke.stapler.StaplerRequest} when the user saves values for the parameter
     * @param jo the {@link net.sf.json.JSONObject} containing the values for the parameter
     */
    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        StringParameterValue value = req.bindJSON(StringParameterValue.class, jo);
        value.setDescription(getDescription());
        return value;
    }

    /**
     * Returns the choices for the drop down. This is will attempt to locate the primary.xml repo data, and populate the
     * drop-down.
     * @return the {@link java.util.Map} of options to be displayed to the user when building with parameters.
     * @throws JAXBException if unable to unmarshal the data
     * @throws IOException if we're unable to open the stream for whatever reason. This could be that the AWS S3 request
     * failed for other reasons too.
     */
    @Exported
    public Map<String, String> getChoices() throws JAXBException, IOException {
        final AwsClientReader.Builder builder = AwsClientReader.Builder.newInstance(repoPath, repositoryType);
        if (useAwsKeys && StringUtils.isNotBlank(awsAccessKeyId) && StringUtils.isNotBlank(awsSecretAccessKey)) {
            builder.withAwsAccessKeys(awsAccessKeyId, awsSecretAccessKey);
        }
        final AwsClientReader reader = builder.build();
        return reader.getPackageMap(bucketName, repoPath);
    }

    /**
     * Parameter for retrieving the value in the config form
     * @return true if the checkbox has been selected.
     */
    public boolean isUseAwsKeys() {
        return useAwsKeys;
    }

    /**
     * Parameter for retrieving the value in the config form
     * @return the AWS access key that has been stored in the config.
     */
    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    /**
     * Parameter for retrieving the value in the config form
     * @return the AWS secret access key that has been stored in the config.
     */
    public String getAwsSecretAccessKey() {
        return awsSecretAccessKey;
    }

    /**
     * Parameter for retrieving the value in the config form
     * @return the AWS secret bucket name that has been stored in the config.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Parameter for retrieving the value in the config form
     * @return the repo to be used when looking for the primary.xml file
     */
    public String getRepoPath() {
        return repoPath;
    }

    /**
     * The descriptor for outlining the behavior of the config panel.
     */
    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        private boolean useAwsKeys;
        private String awsAccessKeyId;
        private String awsSecretAccessKey;

        /**
         * The name to display in the config panel
         * @return the display name
         */
        @Override
        public String getDisplayName() {
            return "AWS S3 Yum repo choice parameter";
        }


        
        /**
         * The location for the help file
         * @return the location for the help file.
         */
        @Override
        public String getHelpFile() {
            return "/help/parameter/choice.html";
        }

        /**
         * Checks if parametrised build choices are valid.
         * @param value the values to be checked
         * @return the {@link hudson.util.FormValidation} object containing the information about the validation
         */
        public FormValidation doCheckChoices(@QueryParameter String value) {
            if (PersistentPackageParameterDefinition.areValidChoices(value)) {
                return FormValidation.ok();
            } else {
                return FormValidation.error("Invalid value");
            }
        }

        /**
         * Should the parameter use AWS keys when looking up the data
         * @return true if the checkbox has been selected
         */
        public boolean isUseAwsKeys() {
            return useAwsKeys;
        }

        /**
         * The values for the AWS access key
         * @return the AWS access key, if set.
         */
        public String getAwsAccessKeyId() {
            return awsAccessKeyId;
        }

        /**
         * The values for the AWS secret access key
         * @return the AWS secret access key, if set.
         */
        public String getAwsSecretAccessKey() {
            return awsSecretAccessKey;
        }
    }

}
