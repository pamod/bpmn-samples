/**
 * POC on how BPMN could be integrated with a JMS provider for reliable messaging
 * <b>Note : </b> this code is not recommended to be used for production, this is for demo use only
 */

package org.wso2.messaging.message;

/**
 * Defines a JMS message
 */
public interface Message {

    /**
     * The message payload should be given
     *
     * @return message content
     */
    String getMessageContent();
}
