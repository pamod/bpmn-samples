/**
 * POC on how BPMN could be integrated with a JMS provider for reliable messaging
 * <b>Note : </b> this code is not recommended to be used for production, this is for demo use only
 */
package org.wso2.messaging;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.wso2.messaging.andes.AndesJmsConnection;
import org.wso2.messaging.message.Message;
import org.wso2.messaging.message.RegistrationMessage;
import org.wso2.messaging.transport.JMSConnection;
import org.wso2.messaging.transport.JmsPublisher;

/**
 * Will send a reliable message to a specified queue via JMS
 */
public class ReliableMessagingActiviti implements JavaDelegate {

    /**
     * Will publish a given JMS message
     */
    JmsPublisher publisher = new JmsPublisher();

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        JMSConnection connection = new AndesJmsConnection();

        //Will extract the variables
        String frmUserName = (String) delegateExecution.getVariable("frmUserName");
        String frmRegistrationNumber = (String) delegateExecution.getVariable("frmRegistrationNumber");
        String frmDepartment = (String) delegateExecution.getVariable("frmDepartment");

        //Will construct the message content
        Message message = new RegistrationMessage(frmUserName, frmRegistrationNumber);

        publisher.sendMessages(connection, frmDepartment, message);
    }
}
