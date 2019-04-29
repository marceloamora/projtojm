package com.api.service;

import java.io.IOException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.dtos.EmpresaDto;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class SMSConsumerFuncionarioService {
    @Autowired
	EmpresaService empresaService;
	public void run() throws Exception {
		

		 try {
			
			 
			InitialContext context = new InitialContext();
				ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
				
				Connection connection = factory.createConnection(); 
				connection.start();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				
				Destination fila = (Destination) context.lookup("cadastro");
				MessageConsumer consumer = session.createConsumer(fila );
				
				consumer.setMessageListener(new MessageListener() {

					@Override
					public void onMessage(Message message) {

						TextMessage textMessage = (TextMessage)message;
						
						try {
							 System.out.println(textMessage.getText());
							 ObjectMapper mapper = new ObjectMapper();
							 EmpresaDto  empresaDto = mapper.readValue(textMessage.getText(), EmpresaDto.class);
							 empresaService.salvar(empresaDto);
						} catch (JMSException | IOException e) {
							e.printStackTrace();
						}
					}
					
				});
				
						
				new Scanner(System.in).nextLine();
				
				session.close();
				connection.close();
				context.close();
			 
		 }catch(Exception e) {
			 throw e;
		 }
	}

}
