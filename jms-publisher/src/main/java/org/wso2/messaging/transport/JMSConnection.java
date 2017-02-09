package org.wso2.messaging.transport;


import javax.jms.QueueConnectionFactory;
import javax.naming.NamingException;

/**
 * Details to establish a connection with the wso2 message broker
 */
public interface JMSConnection {

    /**
     * JMS queue connection
     *
     * @return Connection factory
     * @throws NamingException
     */
    QueueConnectionFactory getConnectionFactory() throws NamingException;

}

