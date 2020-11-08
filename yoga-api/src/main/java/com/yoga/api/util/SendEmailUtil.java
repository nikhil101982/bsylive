package com.yoga.api.util;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.yoga.api.model.StatusMessageResponse;

public class SendEmailUtil {


	@Autowired
	public JavaMailSender javaMailSender;

	UtilMethods utilMethods = new UtilMethods();

	public StatusMessageResponse sendEmail(String subject, String fromEmailId,
			String toEmailId, String text, String successResponseMessage, String failureResponseMessage)
			throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();
		try {
			message.setTo(toEmailId);
			message.setFrom(fromEmailId);
			message.setText(text);
			message.setSubject(subject);
			javaMailSender.send(message);
			return utilMethods.successResponse(successResponseMessage);
		} catch (MailException e) {

			return utilMethods.errorResponse(failureResponseMessage);

		}
	}

}
