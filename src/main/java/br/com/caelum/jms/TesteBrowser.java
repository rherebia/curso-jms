package br.com.caelum.jms;

import java.util.Enumeration;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteBrowser {

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");

		InitialContext context = new InitialContext(properties);
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiro");
		
		QueueBrowser browser = session.createBrowser((Queue) fila);
		
		Enumeration enumeration = browser.getEnumeration();
		
		while (enumeration.hasMoreElements()) {
			Message message = (Message) enumeration.nextElement();
			
			System.out.println(message);
		}
		
		session.close();
		connection.close();
		context.close();
	}

}
