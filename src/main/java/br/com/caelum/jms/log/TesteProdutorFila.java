package br.com.caelum.jms.log;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.LOG", "fila.log");

		InitialContext context = new InitialContext(properties);
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("LOG");
		
		MessageProducer messageProducer = session.createProducer(fila);

		TextMessage textMessage = session.createTextMessage("Log");
		
		messageProducer.send(textMessage);
		
		session.close();
		connection.close();
		context.close();
	}

}
