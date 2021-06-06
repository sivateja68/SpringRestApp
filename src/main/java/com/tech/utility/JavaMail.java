package com.tech.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class JavaMail {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Async
	public void sendEmail(String to,String subject, String message) {
		 MimeMessage mess = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(mess);
		 try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		 javaMailSender.send(mess);		
	}
	
	@Async
	public void sendEmailForgot(String to,String subject,String link) {
		 MimeMessage mess = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(mess);
		 try {
			helper.setTo(to);
			helper.setSubject(subject);
			String message = "<p>Hello,</p>"
		            + "<p>You have requested to reset your password.</p>"
		            + "<p>Click the link below to change your password:</p>"
		            + "<p><a href=\"" + link + "\">Change my password</a></p>"
		            + "<br>"
		            + "<p>Ignore this email if you do remember your password, "
		            + "or you have not made the request.</p>";
			helper.setText(message,true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		 javaMailSender.send(mess);		
	}
	
}
