package hanze.nl.mockdatabaselogger;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.thoughtworks.xstream.XStream;

public class ArrivaLogger {
	static Connection connection;
	static Session session;
	static MessageConsumer consumer;

	public static void main (String[] args){
		
    try {
    	createConnections();
    	int aantalBerichten=0;
    	int aantalETAs=0;

    	while (true) {
    		int etaSize = processMessageAndGetETAsize();
    		if(etaSize >= 0){
    			aantalBerichten++;
    			aantalETAs += etaSize;
    		}else{
    			break;
    		}
    	}

    	closeConnections();
    	System.out.println(aantalBerichten + " berichten met " + aantalETAs + " ETAs verwerkt.");
    	} catch (Exception e) {
    		System.out.println("Caught: " + e);
    		e.printStackTrace();
    	}
	}

	private static int processMessageAndGetETAsize() throws JMSException{
		Message message = consumer.receive(2000);
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			XStream xstream = new XStream();
			xstream.alias("Bericht", Bericht.class);
			xstream.alias("ETA", ETA.class);
			Bericht bericht=(Bericht)xstream.fromXML(text);
			return bericht.ETAs.size();
		} else {
			System.out.println("Received: " + message);
			return -1;
		}
	}

	private static void createConnections() throws JMSException{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("ARRIVALOGGER");
		consumer = session.createConsumer(destination);
	}

	private static void closeConnections() throws JMSException{
		consumer.close();
		consumer = null;

		session.close();
		session = null;

		connection.close();
		connection = null;
	}
}
