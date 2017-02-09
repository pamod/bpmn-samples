/**
 * POC on how BPMN could be integrated with a JMS provider for reliable messaging
 * <b>Note : </b> this code is not recommended to be used for production, this is for demo use only
 */

package org.wso2.messaging.andes;

import org.wso2.messaging.transport.JavaConnection;

import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Creates JMS connection with the broker
 */
public class AndesJmsConnection implements JavaConnection {

    /**
     * Qpid specific initial context factory
     */
    private static final String QPID_ICF = "org.wso2.andes.jndi.PropertiesFileInitialContextFactory";

    /**
     * Connection factory prefix name
     */
    private static final String CF_NAME_PREFIX = "connectionfactory.";

    /**
     * Qpid connection factory name
     */
    private static final String CF_NAME = "qpidConnectionfactory";

    /**
     * Message broker user name
     */
    private static final String USER_NAME = "admin";

    /**
     * Message broker password
     */
    private static final String PASSWORD = "admin";

    /**
     * Client identifier name
     */
    private static String CARBON_CLIENT_ID = "carbon";

    /**
     * Carbon virtual host name
     */
    private static String CARBON_VIRTUAL_HOST_NAME = "carbon";

    /**
     * Host name of the message broker
     */
    private static String CARBON_DEFAULT_HOSTNAME = "localhost";

    /**
     * Port of exec of the message broker
     */
    private static String CARBON_DEFAULT_PORT = "5672";

    /**
     * {@inheritDoc}
     */
    @Override
    public QueueConnectionFactory getConnectionFactory() throws NamingException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, QPID_ICF);
        properties.put(CF_NAME_PREFIX + CF_NAME, getTCPConnectionURL(USER_NAME, PASSWORD));
        InitialContext ctx = new InitialContext(properties);
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup(CF_NAME);
        return connectionFactory;
    }

    /**
     * Generate andes specific AMQP url
     *
     * @param username the authenticated used name
     * @param password the authenticated password
     * @return connection url
     */
    private String getTCPConnectionURL(String username, String password) {
        return new StringBuffer()
                .append("amqp://").append(username).append(":").append(password)
                .append("@").append(CARBON_CLIENT_ID)
                .append("/").append(CARBON_VIRTUAL_HOST_NAME)
                .append("?brokerlist='tcp://").append(CARBON_DEFAULT_HOSTNAME).append(":").append
                        (CARBON_DEFAULT_PORT).append("'")
                .toString();
    }
}
