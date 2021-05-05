package com.ysm.microservice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService  implements NotificationService{

	
	 @Autowired
	 private JavaMailSender javaMailSender;
	 
	
	 @Value("${toEmailIds}")
	 private String[] toEmails;
	 
	
	 @Value("${emailSubject}")
	 private String emailSubject;
	 
	 @Override
	 public String send(String message) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmails);
        msg.setSubject(emailSubject+"---"+LocalDateTime.now().toString());
        msg.setText(message);
        javaMailSender.send(msg);
		return "Email Sent to total No:-->"+toEmails.length;
	}

	
	
	
	
}
