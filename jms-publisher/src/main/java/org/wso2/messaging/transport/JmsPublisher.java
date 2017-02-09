/**
 * POC on how BPMN could be integrated with a JMS provider for reliable messaging
 * <b>Note : </b> this code is not recommended to be used for production, this is for demo use only
 */

package org.wso2.messaging.transport;

import org.wso2.messaging.message.Message;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.NamingException;

/**
 * Publishes a JMS message using a given connection
 */
public class JmsPublisher {

    /**
     * The connection factory would be defined when initializing the publisher
     */
    private JmsConnection connection;


    public JmsPublisher(JmsConnection connection) {
        this.connection = connection;
    }

    /**
     * <p>
     * Sends a JMS message to a queue
     * <p/>
     * <p>
     * <b>Note:<b/> Per each request a new connection will be established with the broker, this will not be the
     * most optimum way of sending the message. In future the connection caching mechanism will be introduced
     * <p/>
     *
     * @param queueName name of the destination queue
     * @param message   message content
     * @throws NamingException
     * @throws JMSException
     */
    public void sendMessages(String queueName, Message message) throws NamingException,
            JMSException {

        QueueSender queueSender = null;
        QueueConnection queueConnection = null;
        QueueSession queueSession = null;

        try {
            // Lookup connection factory
            QueueConnectionFactory connFactory = connection.getConnectionFactory();
            queueConnection = connFactory.createQueueConnection();
            queueConnection.start();
            queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            Queue jmsQueue = queueSession.createQueue(queueName);
            // create the message to send/ in this case this will only be used to send text messages
            TextMessage textMessage = queueSession.createTextMessage(message.getMessageContent());
            queueSender = queueSession.createSender(jmsQueue);
            queueSender.send(textMessage);
        } finally {
            if (null != queueSender) {
                queueSender.close();
            }

            if (null != queueSession) {
                queueSession.close();
            }

            if (null != queueConnection) {
                queueConnection.close();
            }
        }

    }

}
