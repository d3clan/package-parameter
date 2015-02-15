package com.virginholidays.jenkins.yumparamater;

import com.google.common.collect.Maps;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.SimpleParameterDefinition;
import hudson.model.StringParameterValue;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * yum-paramater
 *
 * @author Declan Newman (467689)
 * @since 14/02/15
 */
public class PersistentYumParameterDefinition extends SimpleParameterDefinition {

    private List<String> choices;
    private final boolean useAwsKeys;
    private final String awsAccessKeyId;
    private final String awsSecretAccessKey;
    private final String bucketName;
    private final String repoPath;

    @DataBoundConstructor
    public PersistentYumParameterDefinition(String name, String description, boolean useAwsKeys, String awsAccessKeyId, String awsSecretAccessKey,
                                            String bucketName, String repoPath) {
        super(name, description);
        this.useAwsKeys = useAwsKeys;
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretAccessKey = awsSecretAccessKey;
        this.bucketName = bucketName;
        this.repoPath = repoPath;
    }


    public static boolean areValidChoices(String choices) {
        return true;
    }

    /**
     * Creates a {@link hudson.model.ParameterValue} from the string representation.
     *
     * @param value
     */
    @Override
    public ParameterValue createValue(String value) {
        return new StringParameterValue(getName(), value, getDescription());
    }

    /**
     * Create a parameter value from a form submission.
     * <p/>
     * <p/>
     * This method is invoked when the user fills in the parameter values in the HTML form
     * and submits it to the server.
     *
     * @param req
     * @param jo
     */
    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        StringParameterValue value = req.bindJSON(StringParameterValue.class, jo);
        value.setDescription(getDescription());
        return value;
    }

    @Exported
    public Map<String, String> getChoices() {
        final Map<String, String> list = Maps.newHashMap();
        list.put("Hello-1", "World1");
        list.put("Hello-2", "World2");
        list.put("Hello-3", "World3");
        return list;
    }

    public boolean isUseAwsKeys() {
        return useAwsKeys;
    }

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    public String getAwsSecretAccessKey() {
        return awsSecretAccessKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getRepoPath() {
        return repoPath;
    }

    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        private boolean useAwsKeys;
        private String awsAccessKeyId;
        private String awsSecretAccessKey;

        @Override
        public String getDisplayName() {
            return "AWS S3 Yum repo choice parameter";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            final JSONObject keys = formData.getJSONObject("useAwsKeys");
            if (useAwsKeys && keys != null && keys.size() > 0) {
                useAwsKeys = true;
                awsAccessKeyId = keys.getString("awsAccessKeyId");
                awsSecretAccessKey = keys.getString("awsSecretAccessKey");
            }
            save();
            return super.configure(req, formData);
        }

        @Override
        public String getHelpFile() {
            return "/help/parameter/choice.html";
        }

        /**
         * Checks if parameterised build choices are valid.
         */
        public FormValidation doCheckChoices(@QueryParameter String value) {
            if (PersistentYumParameterDefinition.areValidChoices(value)) {
                return FormValidation.ok();
            } else {
                return FormValidation.error("Invalid value");
            }
        }

        public boolean isUseAwsKeys() {
            return useAwsKeys;
        }

        public String getAwsAccessKeyId() {
            return awsAccessKeyId;
        }

        public String getAwsSecretAccessKey() {
            return awsSecretAccessKey;
        }
    }

}
