package com.api.service;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

@Service
public class SMSFuncionarioService {
	
	public void enviar(String msg) {
		
		
		try {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("cadastro");
		
		MessageProducer producer = session.createProducer(fila);
		
		Message message = session.createTextMessage(msg);
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
		}catch(NamingException e) {
			
		}catch(JMSException e) {
			
		}
	}

}
