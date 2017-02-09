/**
 * POC on how BPMN could be integrated with a JMS provider for reliable messaging
 * <b>Note : </b> this code is not recommended to be used for production, this is for demo use only
 */
package org.wso2.messaging.message;

/**
 * Would represent the message which will be sent across to the broker
 */
public class RegistrationMessage implements Message {
    /**
     * RegistrationMessage content
     */
    private String message;

    /**
     * The message format the registration should hold
     */
    private static final String REGISTRATION_PAYLOAD_TEMPLATE =
            "{\n" +
                    "  \"registration\": {\n" +
                    "    \"name\": \"$1\",\n" +
                    "    \"number\": \"$2\"\n" +
                    "  }\n" +
                    "}";

    /**
     * For the registration message it's essential to have the user name and the number
     *
     * @param name               the name of the user
     * @param registrationNumber the registration number
     */
    public RegistrationMessage(String name, String registrationNumber) {
        //Will Construct the payload
        this.message = REGISTRATION_PAYLOAD_TEMPLATE.replace("$1", name).replace("$2", registrationNumber);
    }

    /**
     * @return the constructed message
     */
    public String getMessageContent() {
        return this.message;
    }
}
