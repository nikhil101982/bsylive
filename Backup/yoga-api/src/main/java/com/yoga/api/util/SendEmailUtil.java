package com.yoga.api.util;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yoga.api.model.StatusMessageResponse;

@Service
public class SendEmailUtil {

	@Autowired
	public JavaMailSender javaMailSender;

	UtilMethods utilMethods = new UtilMethods();

	public StatusMessageResponse sendEmail(String emailTo , String emailFrom , String subject , String text , String successMessage, String errorMessage) throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();

		try {
			message.setTo(emailTo);
			message.setFrom(emailFrom);
			message.setText(text);
			message.setSubject(subject);
			javaMailSender.send(message);
			
			return utilMethods.successResponse(successMessage);
		} catch (MailException e) {

			return  utilMethods.errorResponse(errorMessage);

		}
	}

}
